package al.ikubinfo.registrationmanagement.service.export;


import be.quodlibet.boxable.*;
import be.quodlibet.boxable.line.LineStyle;
import be.quodlibet.boxable.utils.FontUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomDataTable {
    private Table table;
    private final Cell headerCellTemplate;
    private final Cell dataCellTemplate;

    public CustomDataTable(Table table, PDPage page) throws IOException {
        this.table = table;
        // Create a dummy pdf document, page and table to create template cells
        PDDocument ddoc = new PDDocument();
        PDPage dpage = new PDPage();
        dpage.setMediaBox(page.getMediaBox());
        dpage.setRotation(page.getRotation());
        ddoc.addPage(dpage);
        BaseTable dummyTable = new BaseTable(10f, 10f, 10f, table.getWidth(),
                10f, ddoc, dpage, false, false);
        Row dr = dummyTable.createRow(0f);
        headerCellTemplate = dr.createCell(10f, "A", HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
        dataCellTemplate = dr.createCell(10f, "A", HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
        setDefaultStyles();
        ddoc.close();
    }

    public void addDataToTable(List<String[]> data, Boolean hasHeader) {
        List<Float> textWidths = new ArrayList<>(Collections.nCopies(20, 0f));
        List<Float> colWidths = new ArrayList<>(Collections.nCopies(20, 0f));
        for (String[] line : data) {

            //1
            for (int i = 0; i < line.length; i++) {

                float textWidth = FontUtils.getStringWidth(headerCellTemplate.getFont(),
                        line[i] != null ? line[i] : "", headerCellTemplate.getFontSize());
                if (textWidths.get(i) < textWidth) {
                    textWidths.set(i, textWidth);
                }
            }
        }

        //2
        float totalWidth = 0f;
        for (float f : textWidths) totalWidth += f;

        //3
        float sizeFactor = table.getWidth() / totalWidth;
        for (int i = 0; i < textWidths.size(); i++) {
            colWidths.set(i, textWidths.get(i) * 100 / table.getWidth() * sizeFactor);
        }


        for (String[] line : data) {

            if (hasHeader) {
                Row h = table.createRow(headerCellTemplate.getCellHeight());
                for (int i = 0; i < line.length; i++) {
                    String cellValue = line[i] != null ? line[i] : "";
                    Cell c = h.createCell(colWidths.get(i), cellValue, headerCellTemplate.getAlign(),
                            headerCellTemplate.getValign());
                    // Apply style of header cell to this cell
                    c.copyCellStyle(headerCellTemplate);
                    c.setText(cellValue);
                }
                table.addHeaderRow(h);
                hasHeader = false;
            } else {
                Row r = table.createRow(dataCellTemplate.getCellHeight());
                for (int i = 0; i < line.length; i++) {
                    String cellValue = line[i] != null ? line[i] : "";
                    Cell c = r.createCell(colWidths.get(i), cellValue, dataCellTemplate.getAlign(), dataCellTemplate.getValign());
                    // Apply style of header cell to this cell
                    c.copyCellStyle(dataCellTemplate);
                    c.setText(cellValue);
                }
            }
        }
    }

    private void setDefaultStyles() {
        LineStyle thinline = new LineStyle(Color.BLACK, 0.75f);
        // Header style
        headerCellTemplate.setTextColor(new Color(0, 71, 187));
        headerCellTemplate.setFillColor(new Color(238, 220, 0));
        headerCellTemplate.setFont(PDType1Font.HELVETICA_BOLD);
        headerCellTemplate.setBorderStyle(thinline);
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
