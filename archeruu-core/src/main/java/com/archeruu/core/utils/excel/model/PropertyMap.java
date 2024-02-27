package com.archeruu.core.utils.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 属性Map
 *
 * @author Archer
 */
public interface PropertyMap {

    default Map<String, String> getPropertyValueMap() {
        Map<String, String> propertyMap = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                String[] value = annotation.value();
                if (value.length >= 2) {
                    propertyMap.put(field.getName(), value[1]);
                }
            }
        }
        return propertyMap;
    }

    default Map<String, Integer> getPropertyIndexMap() {
        Map<String, Integer> propertyMap = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                propertyMap.put(field.getName(), annotation.index());
            }
        }
        return propertyMap;
    }
}
