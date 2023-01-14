package al.ikubinfo.registrationmanagement.service;

import al.ikubinfo.registrationmanagement.entity.BaseEntity;
import al.ikubinfo.registrationmanagement.repository.BaseJpaRepository;
import al.ikubinfo.registrationmanagement.repository.criteria.BaseCriteria;
import al.ikubinfo.registrationmanagement.repository.specification.SpecificationBuilder;
import al.ikubinfo.registrationmanagement.service.export.CustomDataTable;
import be.quodlibet.boxable.BaseTable;
import com.opencsv.CSVWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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

public abstract class ServiceTemplate<
        C extends BaseCriteria,
        E extends BaseEntity,
        R extends BaseJpaRepository<E>,
        S extends SpecificationBuilder<E, C>> {
    protected final R repository;

    protected final S specificationBuilder;


    protected ServiceTemplate(@NonNull R repository,
                              @NonNull S specificationBuilder) {
        this.repository = repository;
        this.specificationBuilder = specificationBuilder;
    }

    public byte[] createPdf(C criteria) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        List<String[]> entries = new ArrayList<>();
        entries.add(getHeaders());
        getExportList(criteria).forEach(entity -> entries.add(populate(entity)));

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
            throw new RuntimeException("Cant create Pdf");
        }

        return output.toByteArray();
    }

    public byte[] createCsv(@Nullable C criteria) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        List<String[]> entries = new ArrayList<>();
        entries.add(getHeaders());
        getExportList(criteria).forEach(entity -> entries.add(populate(entity)));

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

    public byte[] createExcel(@Nullable C criteria) {

        Workbook workbook = new XSSFWorkbook();
        String[] headers = getHeaders();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("course sheet");
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

        for (E entity : getExportList(criteria)) {
            Row row = sheet.createRow(rowNum++);
            String[] fields = populate(entity);
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
            throw new RuntimeException("Cant create Excel");
        }

        return byteArrayOutputStream.toByteArray();

    }

    public abstract String[] getHeaders();

    public abstract String[] populate(E entity);

    protected List<E> getExportList(@Nullable C criteria) {
        if (criteria != null) {
            criteria.setOrderBy(null);
            criteria.setSortDirection(null);
        }

        return (criteria != null)
                ? repository.findAll(specificationBuilder.filter(criteria))
                : repository.findAll();

    }
}
