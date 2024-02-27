package com.archeruu.core.utils.excel.converter;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;
import java.util.Date;

/**
 * 时间转换
 * Converter
 *
 * @author Archer
 */
public class DateConverter implements Converter<Date> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Date convertToJavaData(ReadConverterContext<?> context) {
        return DateUtil.parse(context.getReadCellData().getStringValue(), "yyyy-MM-dd");
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Date> context) {
        return new WriteCellData<>(DateUtil.format(context.getValue(), "yyyy-MM-dd"));
    }
}
