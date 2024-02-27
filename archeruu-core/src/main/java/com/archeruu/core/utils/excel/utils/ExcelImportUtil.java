package com.archeruu.core.utils.excel.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.archeruu.core.utils.excel.listener.SaveListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Excel导入工具
 *
 * @author Archer
 */
public class ExcelImportUtil {

    /**
     * 保存excel
     * 自定义Listener
     *
     * @param inputStream 输入流
     * @param clazz       表头
     * @param listener    listener
     * @param <T>         T
     */
    public static <T> void saveExcel(InputStream inputStream, Class<T> clazz,
                                     AnalysisEventListener<T> listener) {
        EasyExcel.read(inputStream, clazz, listener)
                .sheet()
                .doRead();
    }

    /**
     * 保存普通类型excel
     * SaveListener
     *
     * @param inputStream    输入流
     * @param clazz          表头
     * @param headerConsumer 表头处理 FunctionalInterface
     * @param dataConsumer   单线程数据处理 FunctionalInterface
     * @param <T>            T
     */
    public static <T> void saveExcel(InputStream inputStream, Class<T> clazz,
                                           Consumer<Map<Integer, String>> headerConsumer,
                                           BiConsumer<List<T>, Integer> dataConsumer) {
        EasyExcel.read(inputStream, clazz, SaveListener.<T>builder()
                        .dataList(new ArrayList<>(SaveListener.BATCH_COUNT))
                        .tClass(clazz)
                        .headerConsumer(headerConsumer)
                        .dataConsumer(dataConsumer)
                        .build())
                .sheet()
                .doRead();
    }

    /**
     * 保存notice类型excel
     * SaveListener
     *
     * @param inputStream    输入流
     * @param clazz          表头
     * @param headerConsumer 表头处理 FunctionalInterface
     * @param dataConsumer   单线程数据处理 FunctionalInterface
     * @param <T>            T
     */
    public static <T> void saveNoticeExcel(InputStream inputStream, Class<T> clazz,
                                           Consumer<Map<Integer, String>> headerConsumer,
                                           BiConsumer<List<T>, Integer> dataConsumer) {
        EasyExcel.read(inputStream, clazz, SaveListener.<T>builder()
                        .dataList(new ArrayList<>(SaveListener.BATCH_COUNT))
                        .tClass(clazz)
                        .headerConsumer(headerConsumer)
                        .dataConsumer(dataConsumer)
                        .build())
                .sheet()
                .headRowNumber(2)
                .doRead();
    }

    /**
     * 多线程保存excel
     * SaveListener
     *
     * @param inputStream      输入流
     * @param clazz            表头
     * @param headerConsumer   表头处理 FunctionalInterface
     * @param muchDataConsumer 多线程数据处理 FunctionalInterface
     * @param forkJoinPool     线程池
     * @param <T>              T
     */
    public static <T> void multiThreadSaveExcel(InputStream inputStream, Class<T> clazz,
                                                Consumer<Map<Integer, String>> headerConsumer,
                                                BiConsumer<List<T>, Map<String, Integer>> muchDataConsumer,
                                                ForkJoinPool forkJoinPool) {
        EasyExcel.read(inputStream, clazz, SaveListener.<T>builder()
                        .dataList(new ArrayList<>(SaveListener.BATCH_COUNT))
                        .tClass(clazz)
                        .headerConsumer(headerConsumer)
                        .muchDataConsumer(muchDataConsumer)
                        .forkJoinPool(forkJoinPool)
                        .build())
                .sheet()
                .doRead();
    }
}
