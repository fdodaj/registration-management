package al.ikubinfo.registrationmanagement.service.impl.export;

import al.ikubinfo.registrationmanagement.dto.CourseUserListDto;
import al.ikubinfo.registrationmanagement.repository.criteria.CourseUserCriteria;
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
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.opencsv.ICSVParser.DEFAULT_ESCAPE_CHARACTER;
import static com.opencsv.ICSVParser.DEFAULT_SEPARATOR;
import static com.opencsv.ICSVWriter.DEFAULT_LINE_END;
import static com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER;

@Service
public class CourseUserExports {

    @Autowired
    private CourseService courseService;

    public byte[] createCourseUserCvs(CourseUserCriteria criteria) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        List<String[]> entries = new ArrayList<>();
        entries.add(getHeaders());

        courseService.getCourseUserList(criteria).getContent().forEach(e -> entries.add(populate(e)));


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

    public byte[] createCourseUserPdf(CourseUserCriteria criteria) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        List<String[]> entries = new ArrayList<>();
        entries.add(getHeaders());
        courseService.getCourseUserList(criteria).getContent().forEach(e -> entries.add(populate(e)));

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
            throw new RuntimeException("cant create pdf!");
        }

        return output.toByteArray();
    }

    public byte[] createCourseUserExcel(CourseUserCriteria criteria) {

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

        for (CourseUserListDto dto : courseService.getCourseUserList(criteria).getContent()) {
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
            throw new RuntimeException("Cant create Excel!");
        }

        return byteArrayOutputStream.toByteArray();

    }


    public String[] getHeaders() {
        return new String[]{
                "Emer", "Mbiemer", "Email", "Nr. telefonit", "Emer kursi","Fillon", "Mbaron","Statusi","Comment","Referenca","cmimi i paguar","Ulje cmimi",
        };
    }
    public String[] populate(CourseUserListDto dto) {
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
