package al.ikubinfo.registrationmanagement.service.impl;

import al.ikubinfo.registrationmanagement.converter.CourseConverter;
import al.ikubinfo.registrationmanagement.converter.CourseUserConverter;
import al.ikubinfo.registrationmanagement.dto.*;
import al.ikubinfo.registrationmanagement.entity.CourseEntity;
import al.ikubinfo.registrationmanagement.entity.CourseUserEntity;
import al.ikubinfo.registrationmanagement.entity.CourseUserId;
import al.ikubinfo.registrationmanagement.repository.CourseRepository;
import al.ikubinfo.registrationmanagement.repository.CourseUserRepository;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseCriteria;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseUserCriteria;
import al.ikubinfo.registrationmanagement.repository.specification.CourseSpecification;
import al.ikubinfo.registrationmanagement.repository.specification.CourseUserSpecification;
import al.ikubinfo.registrationmanagement.service.CourseService;
import al.ikubinfo.registrationmanagement.service.CustomDataTable;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseSpecification courseSpecification;


    @Autowired
    CourseUserSpecification courseUserSpecification;

    @Autowired
    private CourseConverter converter;


    @Autowired
    private CourseUserConverter courseUserConverter;

    @Autowired
    private CourseRepository courseRepository;


    @Autowired
    private CourseUserRepository courseUserRepository;


    @Override
    public Page<CourseDto> filterCourses(CourseCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(),
                Sort.Direction.valueOf(criteria.getSortDirection()), criteria.getOrderBy());

        Specification<CourseEntity> spec = courseSpecification.filter(criteria);
        return courseRepository.findAll(spec, pageable).map(converter::toDto);
    }


    @Override
    public Page<CourseUserListDto> getCourseUserList(CourseUserCriteria criteria){
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(),
                Sort.Direction.valueOf(criteria.getSortDirection()), criteria.getOrderBy());

        Specification<CourseUserEntity> spec = courseUserSpecification.filter(criteria);
        return courseUserRepository.findAll(spec, pageable).map(courseUserConverter::toCourseUserList);
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto) {
        CourseEntity currentEntity = getCourseEntity(courseDto.getId());
        CourseEntity entity = converter.toUpdateCourseEntity(courseDto, currentEntity);
        return converter.toDto(courseRepository.save(entity));
    }

    @Override
    public CourseUserDto editCourseUser(CourseUserDto courseUserDto) {
        CourseUserEntity currentCourseUserEntity = courseUserConverter.toEntity(getCourseUserEntity(courseUserDto.getCourseId(), courseUserDto.getUserId()));
        CourseUserEntity entity = courseUserConverter.toUpdateCourseUserEntity(courseUserDto, currentCourseUserEntity);
        entity.setCreatedDate(getCourseUserEntity(courseUserDto.getCourseId(), courseUserDto.getUserId()).getCreatedDate());

        return courseUserConverter.toDto(courseUserRepository.save(entity));
    }

    @Override
    public List<CourseUserListDto> getCourseUserListByCourseId(Long courseId) {
        return courseUserRepository.getCourseUserEntitiesByCourseId(courseId)
                .stream().map(courseUserConverter::toCourseUserList)
                .collect(Collectors.toList());
    }



    @Override
    public CourseDto getCourseById(Long id) {

        return courseRepository.findById(id)
                .map(converter::toDto)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public List<CourseDto> getAllUnfilteredCourses() {
        return converter.toCourseDtoList(courseRepository.findAll());
    }


    @Override
    public CourseDto saveCourse(ValidatedCourseDto courseDto) {
        CourseEntity entity = converter.toEntity(courseDto);
        return converter.toDto(courseRepository.save(entity));
    }

    @Override
    public void deleteCourseById(Long id) {
        courseUserRepository.getCourseUserEntitiesByCourseCourseName(getCourseById(id).getCourseName()).forEach(e -> e.setDeleted(true));


        CourseEntity course = courseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Course does not exist"));
        course.setStatus(CourseStatus.FINISHED);
        course.setDeleted(true);
        courseRepository.save(course);
    }



    @Override
    public CourseUserDto assignUserToCourse(CourseUserDto dto) {


        CourseUserEntity courseUserEntity = courseUserRepository
            .findById(new CourseUserId(dto.getUserId(), dto.getCourseId()))
            .orElse(null);

        if (courseUserEntity != null) {
            courseUserEntity.setDeleted(false);
            courseUserRepository.save(courseUserEntity);
            return courseUserConverter.toDto(courseUserEntity);
        } else {


            dto.setCreatedDate(LocalDate.now());
            dto.setCreatedDate(LocalDate.now());
            courseUserRepository.save(courseUserConverter.toEntity(dto));
            return courseUserConverter.toDto(courseUserConverter.toEntity(dto));
        }
    }

    @Override
    public void removeUserFromCourse(Long userId, Long courseId) {
        CourseUserEntity entity = courseUserRepository.findByIdCourseIdAndIdUserId(courseId, userId);
        entity.setDeleted(true);
        courseUserRepository.save(entity);
    }

    @Override
    public CourseUserDto updateCourseUser(CourseUserDto dto) {
        CourseUserEntity currentEntity = courseUserConverter.toEntity(getCourseUserEntity(dto.getUserId(), dto.getCourseId()));
        CourseUserEntity entity = courseUserConverter.toUpdateCourseUserEntity(dto, currentEntity);
        return courseUserConverter.toDto(courseUserRepository.save(entity));
    }

    @Override
    public List<SimplifiedCourseUserDto> getAllStudentsByCourseId(Long courseId) {

        return courseUserRepository.getByIdCourseId(courseId)
                .stream()
                .filter(c -> !c.isDeleted())
                .map(courseUserConverter::toSimplifiedDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseUserListDto> getCourseUserList() {
        return courseUserRepository.findAll()
                .stream()
                .map(courseUserConverter::toCourseUserList)
                .collect(Collectors.toList());
    }




    private CourseEntity getCourseEntity(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public CourseUserDto getCourseUserEntity(Long courseId, Long userId) {
        return courseUserConverter.toDto(courseUserRepository.findById(new CourseUserId(userId, courseId))
                .orElseThrow(() -> new RuntimeException("Course user relation was not found")));
    }

    @Override
    public byte[] createCsv(CourseCriteria criteria) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        List<String[]> entries = new ArrayList<>();
        entries.add(getHeaders());

        filterCourses(criteria).getContent().forEach(e -> entries.add(populate(e)));


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
    public byte[] createExcel(CourseCriteria criteria) {

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

        for (CourseDto dto : filterCourses(criteria).getContent()) {
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
            throw new RuntimeException("Non è possibile creare documento Excel");
        }

        return byteArrayOutputStream.toByteArray();

    }

    @Override
    public byte[] createPdf(CourseCriteria criteria) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        List<String[]> entries = new ArrayList<>();
        entries.add(getHeaders());
        filterCourses(criteria).getContent().forEach(e -> entries.add(populate(e)));

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        float columnWidth = (1 / (10 * 2.54f) * 72 * 50);
        float width;
        if(getHeaders().length < 7) width = columnWidth*7;
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
            throw new RuntimeException("Cant create PDF!f");
        }

        return output.toByteArray();
    }



    public String[] getHeaders() {
        return new String[]{
                "Emri i kursit", "Cmimi", "statusi", "fillimi i regjistrimit", "mbarimi i regjistrimit"
        };
    }

    public String[] populate(CourseDto dto) {
        if(dto.getRegistrationStartDate() == null){
             dto.setRegistrationStartDate(LocalDate.now());
        }
        if(dto.getRegistrationEndDate() == null){
            dto.setRegistrationEndDate(LocalDate.now());
        }
//        if(dto.getPrice() == null){
//            dto.setPrice(0000.0);
//        }
        return new String[]{
                dto.getCourseName(),
                dto.getPrice().toString(),
                dto.getStatus().name(),
                dto.getRegistrationStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                dto.getRegistrationEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        };
    }










    @Override
    public byte[] createCourseUserCvs(CourseUserCriteria criteria) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        List<String[]> entries = new ArrayList<>();
        entries.add(getCourseUserHeaders());

        getCourseUserList(criteria).getContent().forEach(e -> entries.add(populateCourseUser(e)));


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
    public byte[] createCourseUserPdf(CourseUserCriteria criteria) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        List<String[]> entries = new ArrayList<>();
        entries.add(getCourseUserHeaders());
        getCourseUserList(criteria).getContent().forEach(e -> entries.add(populateCourseUser(e)));

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        float columnWidth = (1 / (10 * 2.54f) * 72 * 50);
        float width;
        if(getCourseUserHeaders().length < 7) width = columnWidth*7;
        else width = columnWidth * getCourseUserHeaders().length + columnWidth;
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
            throw new RuntimeException("cant create pdf!");
        }

        return output.toByteArray();
    }

    @Override
    public byte[] createCourseUserExcel(CourseUserCriteria criteria) {

        Workbook workbook = new XSSFWorkbook();
        String[] headers = getCourseUserHeaders();
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

        for (CourseUserListDto dto : getCourseUserList(criteria).getContent()) {
            Row row = sheet.createRow(rowNum++);
            String[] fields = populateCourseUser(dto);
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
            throw new RuntimeException("Cant create Excel!");
        }

        return byteArrayOutputStream.toByteArray();

    }


    public String[] getCourseUserHeaders() {
        return new String[]{
                "Emer", "Mbiemer", "Email", "Nr. telefonit", "Emer kursi","Fillon", "Mbaron","Statusi","Comment","Referenca","cmimi i paguar","Ulje cmimi",
        };
    }
    public String[] populateCourseUser(CourseUserListDto dto) {
        if (dto.getPricePaid() == null){
            dto.setPricePaid(0.0);
        }
        if (dto.getPriceReduction() == null){
            dto.setPriceReduction(0.0);
        }
        return new String[]{
                dto.getUserDto().getFirstName(),
                dto.getUserDto().getLastName(),
                dto.getUserDto().getEmail(),
                dto.getUserDto().getPhoneNumber(),
                dto.getCourseDto().getCourseName(),
                dto.getCourseDto().getCourseStartDate().toString(),
                dto.getCourseDto().getCourseEndDate().toString(),
                dto.getCourseDto().getStatus().toString(),
                dto.getComment(),
                dto.getReference(),
                dto.getPricePaid().toString(),
                dto.getPriceReduction().toString()
        };
    }


}

