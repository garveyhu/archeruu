package com.archeruu.core.utils.excel.handler;

import com.alibaba.excel.event.Order;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

/**
 * 通用writeHandler
 *
 * @author Archer
 */
public class CommonWriteHandler implements CellWriteHandler, Order {

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {

        Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        XSSFCellStyle xssfCellStyle = (XSSFCellStyle) workbook.createCellStyle();

        CellStyle cellStyle_5 = workbook.createCellStyle();
        cellStyle_5.setDataFormat((short) 49);
        writeSheetHolder.getSheet().setDefaultColumnStyle(head.getColumnIndex(), cellStyle_5);

        Font font = workbook.createFont();
        if (cell.getRowIndex() == 0) {
            // 表头行格式设置
            // 行高
            Row row = cell.getRow();
            row.setHeightInPoints(25);
            // 行宽
            writeSheetHolder.getSheet().setDefaultColumnWidth(10);
            // 单元格样式
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
            // 单元格边框
            cellStyle.setBorderBottom(BorderStyle.THIN);//设置底边框;
            cellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());//设置底边框颜色;
            cellStyle.setBorderLeft(BorderStyle.THIN);  //设置左边框;
            cellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());//设置左边框颜色;
            cellStyle.setBorderRight(BorderStyle.THIN);//设置右边框;
            cellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());//设置右边框颜色;
            cellStyle.setBorderTop(BorderStyle.THIN);//设置顶边框;
            cellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex()); ///设置顶边框颜色;
            // 单元格字体
            font.setFontHeightInPoints((short) 11);
            font.setFontName("宋体");
            font.setBold(true);
            cellStyle.setFont(font);

            // 自定义颜色对象
            xssfCellStyle.cloneStyleFrom(cellStyle);

            java.awt.Color color = new java.awt.Color(Integer.parseInt("e6e6e6", 16));
            XSSFColor THIN_GREY = new XSSFColor(color, new DefaultIndexedColorMap());
            xssfCellStyle.setFillForegroundColor(THIN_GREY);
            // 这里需要指定 FillPattern 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
            xssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        cell.setCellStyle(xssfCellStyle);
    }

    @Override
    public int order() {
        return 1000000;
    }
}
