package com.archeruu.core.utils.excel.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.archeruu.core.utils.excel.complex.ExcelPageHelper;
import com.archeruu.core.utils.excel.complex.ExcelStyle;
import com.archeruu.core.utils.excel.constant.CommonConstant;
import com.archeruu.core.utils.excel.function.ExcelDataFunction;
import com.github.pagehelper.PageHelper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 * Excel导出工具
 *
 * @author Archer
 */
@Log4j2
public class ExcelExportUtil {

    /**
     * 注意事项excel模板
     * 文件名：fileName + dateFormat格式当前时间
     *
     * @param response     响应
     * @param fileName     文件名
     * @param dateFormat   日期格式
     * @param sheetName    表名
     * @param data         表数据
     * @param clazz        表头
     * @param noticeHeight notice行高
     * @param columns      标红必填列
     */
    public static <T> void noticeTemplate(HttpServletResponse response,
                                      String fileName, String dateFormat, String sheetName,
                                      List<T> data, Class<T> clazz,
                                      int noticeHeight, List<Integer> columns) {
        try {
            EasyExcel.write(OutputStreamUtil.getOutputStream(response, fileName, dateFormat), clazz)
                    .sheet(sheetName)
                    .registerWriteHandler(ExcelStyle.noticeStyle(noticeHeight, columns))
                    .doWrite(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 普通excel模板
     * 文件名：fileName + dateFormat格式当前时间
     *
     * @param response   响应
     * @param fileName   文件名
     * @param dateFormat 日期格式
     * @param sheetName  sheet名
     * @param data       表数据
     * @param clazz      表头
     */
    public static <T> void commonTemplate(HttpServletResponse response,
                                      String fileName, String dateFormat, String sheetName,
                                      List<T> data, Class<T> clazz) {
        try {
            EasyExcel.write(OutputStreamUtil.getOutputStream(response, fileName, dateFormat), clazz)
                    .sheet(sheetName)
                    .registerWriteHandler(ExcelStyle.commonStyle())
                    .doWrite(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 默认excel模板
     * 文件名：fileName + dateFormat格式当前时间
     *
     * @param response   响应
     * @param fileName   文件名
     * @param dateFormat 日期格式
     * @param sheetName  sheet名
     * @param data       表数据
     * @param clazz      表头
     */
    public static <T> void defaultTemplate(HttpServletResponse response,
                                       String fileName, String dateFormat, String sheetName,
                                       List<T> data, Class<T> clazz) {
        try {
            EasyExcel.write(OutputStreamUtil.getOutputStream(response, fileName, dateFormat), clazz)
                    .sheet(sheetName)
                    .registerWriteHandler(ExcelStyle.defaultStyle())
                    .doWrite(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量导出
     * 根据count选择导出逻辑
     * 数据由函数式接口function提供
     *
     * @param response   响应
     * @param fileName   文件名
     * @param dateFormat 日期格式
     * @param sheetName  表名
     * @param count      数据总数
     * @param function   数据获取函数
     * @param clazz      表头
     * @param <T>        T
     */
    public static <T> void batchExport(HttpServletResponse response,
                                       String fileName, String dateFormat, String sheetName,
                                       int count, ExcelDataFunction<T> function, Class<T> clazz) {
        int oneSheetRowCount = ExcelPageHelper.MAX_ONE_SHEET_ROW_COUNT;
        if (count <= oneSheetRowCount) {
            if (count <= ExcelPageHelper.PER_WRITE_ROW_COUNT) {
                List<T> data = function.getData();
                commonTemplate(response, fileName, dateFormat, sheetName, data, clazz);
            } else {
                batchMultiTimes(fileName, sheetName, count, function, clazz, response);
            }
        } else {
            batchMultiSheets(fileName, sheetName, count, function, clazz, response);
        }
    }

    /**
     * 多sheet页导出
     * 相同表头
     *
     * @param fileName        文件名
     * @param multiSheetsData 多sheet页数据
     * @param clazz           表头
     * @param response        响应
     * @param <T>             T
     */
    public static <T> void multiSheets(String fileName, Map<String, List<T>> multiSheetsData,
                                       Class<T> clazz, HttpServletResponse response) {
        ExcelWriter excelWriter = null;
        try {
            excelWriter = getExcelWriter(response, fileName, clazz);
            AtomicInteger i = new AtomicInteger();
            final ExcelWriter finalExcelWriter = excelWriter;
            multiSheetsData.forEach((key, value) -> {
                writeOneSheet(finalExcelWriter, i.get(), key, value);
                i.getAndIncrement();
            });
        } catch (IOException e) {
            log.error("Exception", e);
        } finally {
            finish(excelWriter);
        }
    }

    /**
     * 获取ExcelWriter
     * commonStyle
     *
     * @param response 响应
     * @param fileName 文件名
     * @param clazz    表头
     * @return ExcelWriter
     * @throws IOException IOException
     */
    private static <T> ExcelWriter getExcelWriter(HttpServletResponse response, String fileName, Class<T> clazz) throws IOException {
        return EasyExcel.write(OutputStreamUtil.getOutputStream(response, fileName, CommonConstant.YYYY_MM_DD_HH_MM_SS_SSS), clazz)
                .registerWriteHandler(ExcelStyle.commonStyle())
                .build();
    }

    /**
     * 写入指定Sheet
     *
     * @param excelWriter excelWriter
     * @param sheetNum    表序号
     * @param sheetName   表名
     * @param data        表数据
     */
    private static <T> void writeOneSheet(ExcelWriter excelWriter, int sheetNum, String sheetName, List<T> data) {
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetNum, sheetName).build();
        excelWriter.write(data, writeSheet);
    }

    /**
     * 大于100w的数据分批次多sheet页导出
     *
     * @param fileName  文件名
     * @param sheetName 表名
     * @param count     数据总数
     * @param function  数据获取函数
     * @param clazz     表头
     * @param response  响应
     * @param <T>       T
     */
    private static <T> void batchMultiSheets(String fileName, String sheetName, int count, ExcelDataFunction<T> function,
                                             Class<T> clazz, HttpServletResponse response) {
        ExcelWriter excelWriter = null;
        try {
            excelWriter = getExcelWriter(response, fileName, clazz);
            ExcelPageHelper excelPageHelper = ExcelPageHelper.getExcelPageHelper(count);
            for (int i = 0; i < excelPageHelper.sheetCount; i++) {
                for (int j = 0; j < (i != excelPageHelper.sheetCount - 1 ? excelPageHelper.preSheetWriteCount : excelPageHelper.lastSheetWriteCount); j++) {
                    int pageNum = j + 1 + excelPageHelper.preSheetWriteCount * i;
                    PageHelper.startPage(pageNum, ExcelPageHelper.PER_WRITE_ROW_COUNT, false);
                    List<T> data = function.getData();
                    writeOneSheet(excelWriter, i, sheetName + i, data);
                }
            }
        } catch (IOException e) {
            log.error("Exception", e);
        } finally {
            finish(excelWriter);
        }
    }

    /**
     * 小于100w大于20w的数据单表多批次导出
     *
     * @param fileName  文件名
     * @param sheetName 表名
     * @param count     数据总数
     * @param function  数据获取函数
     * @param clazz     表头
     * @param response  响应
     * @param <T>       T
     */
    private static <T> void batchMultiTimes(String fileName, String sheetName, int count, ExcelDataFunction<T> function,
                                            Class<T> clazz, HttpServletResponse response) {
        ExcelWriter excelWriter = null;
        try {
            int pageSize = ExcelPageHelper.PER_WRITE_ROW_COUNT;
            excelWriter = getExcelWriter(response, fileName, clazz);
            int writeCount = count % pageSize == 0 ? (count / pageSize) : (count / pageSize + 1);
            for (int i = 0; i < writeCount; i++) {
                PageHelper.startPage(i, pageSize, false);
                List<T> data = function.getData();
                writeOneSheet(excelWriter, 0, sheetName, data);
            }
        } catch (IOException e) {
            log.error("Exception", e);
        } finally {
            finish(excelWriter);
        }
    }

    /**
     * 关闭ExcelWriter
     *
     * @param excelWriter excelWriter
     */
    private static void finish(ExcelWriter excelWriter) {
        if (excelWriter != null) {
            excelWriter.finish();
        }
    }
}
