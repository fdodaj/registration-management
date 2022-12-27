package al.ikubinfo.registrationmanagement.service.impl;

import al.ikubinfo.registrationmanagement.converter.UserConverter;
import al.ikubinfo.registrationmanagement.dto.CourseDto;
import al.ikubinfo.registrationmanagement.dto.PasswordDto;
import al.ikubinfo.registrationmanagement.dto.UserDto;
import al.ikubinfo.registrationmanagement.dto.ValidatedUserDto;
import al.ikubinfo.registrationmanagement.entity.UserEntity;
import al.ikubinfo.registrationmanagement.repository.RoleRepository;
import al.ikubinfo.registrationmanagement.repository.UserEntityManagerRepository;
import al.ikubinfo.registrationmanagement.repository.UserRepository;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import al.ikubinfo.registrationmanagement.repository.criteria.UserCriteria;
import al.ikubinfo.registrationmanagement.repository.specification.UserSpecification;
import al.ikubinfo.registrationmanagement.security.Utils;
import al.ikubinfo.registrationmanagement.service.CustomDataTable;
import al.ikubinfo.registrationmanagement.service.UserService;
import be.quodlibet.boxable.BaseTable;
import com.opencsv.CSVWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.opencsv.ICSVParser.DEFAULT_ESCAPE_CHARACTER;
import static com.opencsv.ICSVParser.DEFAULT_SEPARATOR;
import static com.opencsv.ICSVWriter.DEFAULT_LINE_END;
import static com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserSpecification specification;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserEntityManagerRepository userEMRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Page<UserDto> filterUsers(UserCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(),
                Sort.Direction.valueOf(criteria.getSortDirection()), criteria.getOrderBy());
        return userRepository
                .findAll(specification.filter(criteria), pageable)
                .map(userConverter::toDto);
    }


    @Override
    public UserDto getUserById(Long id) {
        return userConverter.toDto(getStudentEntity(id));

    }

    @Override
    public UserDto saveUser(ValidatedUserDto dto) {
        UserEntity userEntity = userConverter.toValidatedUserEntity(dto);
        userEntity.setRole(roleRepository.findByName("STUDENT"));
        return userConverter.toDto(userRepository.save(userEntity));
    }

    @Override
    public UserDto updateUser(ValidatedUserDto student) {
        UserEntity currentEntity = getStudentEntity(student.getId());
        UserEntity userEntity = userConverter.toUpdateStudentEntity(student, currentEntity);
        return userConverter.toDto(userRepository.save(userEntity));
    }


    @Override
    public void deleteUserById(Long id) {
        UserEntity student = getStudentEntity(id);
        student.setDeleted(true);
        userRepository.save(student);
    }


    public UserEntity getStudentEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public List<UserDto> getUsersEM() {
        return userEMRepository.getAllRegisteredUsers()
                .stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUnassignedUsers() {
        return userEMRepository.getAllUnassignedUsers()
                .stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
    }

    public UserDto changePassword(PasswordDto passwordDto) {
        UserEntity user = userRepository
                .findByEmail(Utils.getCurrentEmail().orElseThrow(null))
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!authenticate(user, passwordDto.getCurrentPassword())) {
            throw new AccessDeniedException("Access denied. Cannot change password");
        }
        user.setPassword(bCryptPasswordEncoder.encode(passwordDto.getNewPassword()));
        return userConverter.toDto(userRepository.save(user));
    }

    private boolean authenticate(UserEntity user, String password) {
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

    @Override
    public byte[] createExcel(UserCriteria criteria) {

        Workbook workbook = new XSSFWorkbook();
        String[] headers = getHeaders();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet( "course sheet");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 13);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
            cell.setCellStyle(headerCellStyle);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        int rowNum = 1;

        for (UserDto dto : filterUsers(criteria).getContent()) {
            Row row = sheet.createRow(rowNum++);
            String[] fields = populate(dto);
            for (int i = 0; i < fields.length; i++)
                row.createCell(i).setCellValue(fields[i]);
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream byteArrayOutputStream;

        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);

            byteArrayOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException("Cant create EXCEL");
        }

        return byteArrayOutputStream.toByteArray();

    }

    @Override
    public byte[] createCsv(UserCriteria criteria) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        List<String[]> entries = new ArrayList<>();
        entries.add(getHeaders());

        filterUsers(criteria).getContent().forEach(e -> entries.add(populate(e)));


        OutputStreamWriter outputStreamWriter =
                new OutputStreamWriter(Objects.requireNonNull(stream), StandardCharsets.UTF_8);
        try (CSVWriter writer = new CSVWriter(outputStreamWriter,
                DEFAULT_SEPARATOR,
                NO_QUOTE_CHARACTER,
                DEFAULT_ESCAPE_CHARACTER,
                DEFAULT_LINE_END)) {

            writer.writeAll(entries);
            outputStreamWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.toByteArray();
    }

    @Override
    public byte[] createPdf(UserCriteria criteria) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        List<String[]> entries = new ArrayList<>();
        entries.add(getHeaders());
        filterUsers(criteria).getContent().forEach(e -> entries.add(populate(e)));

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        float columnWidth = (1 / (10 * 2.54f) * 72 * 50);
        float width;
        if (getHeaders().length < 7) width = columnWidth * 7;
        else width = columnWidth * getHeaders().length + columnWidth;
        page.setMediaBox(new PDRectangle(width, PDRectangle.A4.getWidth()));
        doc.addPage(page);
        //Initialize table
        float margin = 10;
        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
        float yStart = yStartNewPage;
        float bottomMargin = 20;

        try {
            BaseTable dataTable = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth,
                    margin, doc, page, true, true);
            CustomDataTable customDataTable = new CustomDataTable(dataTable, page);

            customDataTable.addDataToTable(entries, true);
            dataTable.draw();
            doc.save(output);
            doc.close();
        } catch (IOException e) {
            e.getStackTrace();
            throw new RuntimeException("Can't create PDF!");
        }

        return output.toByteArray();
    }

    public String[] getHeaders() {
        return new String[]{
                "Emer", "Mbiemer", "Email", "Numer telefoni", "Referimi"
        };
    }


    public String[] populate(UserDto dto) {
        return new String[]{
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                dto.getReachForm().toString(),
        };
    }
}
