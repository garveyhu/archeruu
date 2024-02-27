package com.archeruu.core.utils.excel.listener;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 保存Listener
 *
 * @author Archer
 */
@Log4j2
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaveListener<T> extends AnalysisEventListener<T> {

    /**
     * 批次读取条数阈值
     */
    public static final Integer BATCH_COUNT = 100000;

    /**
     * 单任务读取条数阈值
     */
    private static final Integer THRESHOLD = 2000;

    /**
     * 表头处理 FunctionalInterface
     */
    private Consumer<Map<Integer, String>> headerConsumer;

    /**
     * 单线程处理 FunctionalInterface
     */
    private BiConsumer<List<T>, Integer> dataConsumer;

    /**
     * 多线程处理 FunctionalInterface
     */
    private BiConsumer<List<T>, Map<String, Integer>> muchDataConsumer;

    /**
     * 线程池 ForkJoinPool
     */
    private ForkJoinPool forkJoinPool;

    /**
     * 数据类型
     */
    private Class<T> tClass;

    /**
     * 读取数据
     */
    private List<T> dataList;

    /**
     * 数据总数
     */
    private int total;

    /**
     * 批次数
     */
    private int batchNum;

    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        if (ObjectUtil.isNotEmpty(data)) {
            total++;
        }
        if (JSONObject.class.equals(tClass)) {
            dataList.add(((JSONObject) JSONObject.toJSON(data)).toJavaObject(tClass));
        } else {
            dataList.add(data);
        }
        if (dataList.size() >= BATCH_COUNT) {
            if (muchDataConsumer != null) {
                muchSaveData();
            } else {
                saveData();
            }
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (dataList.size() > 0) {
            if (muchDataConsumer != null) {
                muchSaveData();
            } else {
                saveData();
            }
        }
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        headerConsumer.accept(headMap);
        super.invokeHeadMap(headMap, context);
    }

    private void saveData() {
        dataConsumer.accept(dataList, total);
        dataList.clear();
    }

    private void muchSaveData() {
        long l = System.currentTimeMillis();
        DealExcelDataTask task = new DealExcelDataTask(BATCH_COUNT * batchNum, BATCH_COUNT * batchNum + dataList.size());
        forkJoinPool.submit(task);
        try {
            task.get();
        } catch (ExecutionException | InterruptedException e) {
            log.error("Exception", e);
        } finally {
            dataList = new ArrayList<>(BATCH_COUNT);
        }
        log.warn("批次执行总耗时:" + (System.currentTimeMillis() - l));
        batchNum++;
    }

    class DealExcelDataTask extends RecursiveTask<Boolean> {
        int low;
        int high;

        public DealExcelDataTask(int low, int high) {
            this.low = low;
            this.high = high;
        }

        @Override
        protected Boolean compute() {
            if (high - low <= THRESHOLD) {
                Map<String, Integer> map = new HashMap<>(5);
                map.put("low", low);
                map.put("high", high);
                map.put("total", total);
                map.put("batchNum", batchNum);
                map.put("batchCount", BATCH_COUNT);
                muchDataConsumer.accept(dataList, map);
                return true;
            } else {
                int mid = low + (high - low) / 2;
                DealExcelDataTask left = new DealExcelDataTask(low, mid);
                DealExcelDataTask right = new DealExcelDataTask(mid, high);
                left.fork();
                right.fork();
                Boolean a = left.join();
                Boolean b = right.join();
                return a && b;
            }
        }
    }
}
