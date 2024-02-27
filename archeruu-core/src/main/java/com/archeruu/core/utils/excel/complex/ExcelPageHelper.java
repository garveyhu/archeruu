package com.archeruu.core.utils.excel.complex;

/**
 * Excel分页参数
 *
 * @author Archer
 */

public class ExcelPageHelper {

    /**
     * 单表最大数据量
     */
    public static final Integer MAX_ONE_SHEET_ROW_COUNT = 1000000;

    /**
     * 每次向EXCEL写入的记录数(查询每页数据大小) 20W
     */
    public static final Integer PER_WRITE_ROW_COUNT = 200000;

    /**
     * 最后一个sheet页需要写入的数据总数 0 说明刚刚好写满
     */
    public int lastSheetRowCount;

    /**
     * 表序号
     */
    public int sheetCount;

    /**
     * 每个sheet页需要查询写入的次数
     */
    public int preSheetWriteCount;

    /**
     * 最后一个sheet页需要查询写入的次数未排除0的情况
     */
    public int lastSheetSelectCount;

    /**
     * 最后一个sheet页需要查询写入的次数
     */
    public int lastSheetWriteCount;

    public ExcelPageHelper(int total) {
        //最后一个sheet页需要写入的数据总数 0 说明刚刚好写满
        lastSheetRowCount = total % MAX_ONE_SHEET_ROW_COUNT;
        //需要分几个sheet页
        sheetCount = lastSheetRowCount == 0 ? (total / MAX_ONE_SHEET_ROW_COUNT) : (total / MAX_ONE_SHEET_ROW_COUNT + 1);
        //每个sheet页需要查询写入的次数
        preSheetWriteCount = MAX_ONE_SHEET_ROW_COUNT / PER_WRITE_ROW_COUNT;
        //最后一个sheet页需要查询写入的次数 未排除0的情况
        lastSheetSelectCount = lastSheetRowCount % PER_WRITE_ROW_COUNT == 0 ? lastSheetRowCount / PER_WRITE_ROW_COUNT : (lastSheetRowCount / PER_WRITE_ROW_COUNT + 1);
        //最后一个sheet页需要查询写入的次数
        lastSheetWriteCount = lastSheetRowCount == 0 ? preSheetWriteCount : lastSheetSelectCount;
    }

    /**
     * 每个sheet存储的记录数 100W
     */
    public static ExcelPageHelper getExcelPageHelper(int total) {
        return new ExcelPageHelper(total);
    }

}
