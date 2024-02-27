package com.archeruu.core.utils.excel.utils;

import com.archeruu.core.utils.excel.constant.CommonConstant;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取输出流
 *
 * @author Archer
 */
public class OutputStreamUtil {

    /**
     * 获取输出流
     * 文件名：yyyyMMddHHmmssSSS格式当前时间
     *
     * @param response 响应
     * @return 输出流
     * @throws IOException IO异常
     */
    public static OutputStream getOutputStream(HttpServletResponse response) throws IOException {
        return getOutputStream(response, null, null);
    }

    /**
     * 获取输出流
     * 文件名：baseFileName
     *
     * @param response     响应
     * @param baseFileName 基础文件名
     * @return 输出流
     * @throws IOException IO异常
     */
    public static OutputStream getOutputStream(String baseFileName, HttpServletResponse response) throws IOException {
        return getOutputStream(response, baseFileName, null);
    }

    /**
     * 获取输出流
     * 文件名：dateFormat格式当前时间
     *
     * @param response   响应
     * @param dateFormat 日期格式
     * @return 输出流
     * @throws IOException IO异常
     */
    public static OutputStream getOutputStream(HttpServletResponse response, String dateFormat) throws IOException {
        return getOutputStream(response, null, dateFormat);
    }

    /**
     * 获取输出流
     * 文件名：baseFileName + dateFormat格式当前时间
     *
     * @param response     响应
     * @param baseFileName 基础文件名
     * @param dateFormat   日期格式
     * @return 输出流
     * @throws IOException IO异常
     */
    public static OutputStream getOutputStream(HttpServletResponse response, String baseFileName, String dateFormat) throws IOException {
        String fullFileName = generateFullFileName(baseFileName, dateFormat);
        setResponseHeaders(response, fullFileName);
        return response.getOutputStream();
    }

    /**
     * 生成文件名
     *
     * @param baseFileName 基础文件名
     * @param dateFormat   日期格式
     * @return 文件名
     * @throws UnsupportedEncodingException 编码异常
     */
    private static String generateFullFileName(String baseFileName, String dateFormat) throws UnsupportedEncodingException {
        String dateStr = "";
        if (dateFormat != null) {
            dateStr = new SimpleDateFormat(dateFormat).format(new Date());
        } else if (baseFileName == null) {
            dateStr = new SimpleDateFormat(CommonConstant.YYYY_MM_DD_HH_MM_SS_SSS).format(new Date());
        }

        String fileName = (baseFileName != null ? baseFileName : "") + dateStr;
        return URLEncoder.encode(fileName, "UTF-8");
    }

    /**
     * 设置响应头
     *
     * @param response 响应
     * @param fileName 文件名
     */
    private static void setResponseHeaders(HttpServletResponse response, String fileName) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "no-store, max-age=0");
    }
}
