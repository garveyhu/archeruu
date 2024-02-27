package com.archeruu.core.utils.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

/**
 * 保存校检Listener
 *
 * @author Archer
 */
public class ValidateSaveListener<T> extends AnalysisEventListener<T> {

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    private void saveData() {

    }

    private void validateData() {

    }
}
