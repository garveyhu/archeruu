package com.archeruu.core.utils.excel.function;

import java.util.List;

/**
 * Excel导出数据获取
 * 函数式接口
 *
 * @author Archer
 */
@FunctionalInterface
public interface ExcelDataFunction<T> {

    /**
     * 获取全部导出数据
     * 内部使用PageHelper分页
     */
    List<T> getData();
}
