package com.archeruu.core.utils;

import com.alicp.jetcache.Cache;
import com.archeruu.core.enums.ResultCode;
import com.archeruu.core.exception.CustomException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 校检工具
 * 开启实体类中的注解校检
 *
 * @author Archer
 */
public class ValidateUtil {
    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * 校验失败时抛出异常
     *
     * @param object 校检对象
     * @param <T>    校检对象类型
     */
    public static <T> void validateAndThrow(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            String errorMsg = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; "));
            throw new CustomException(ResultCode.FAIL, errorMsg);
        }
    }

    /**
     * 校验失败时返回错误信息，不抛出异常
     *
     * @param object 校检对象
     * @param <T>    校检对象类型
     * @return 校检错误信息
     */
    public static <T> String validateAndReturn(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            return violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; "));
        }
        return null;
    }

    /**
     * 校验失败时缓存错误信息，不抛出异常
     *
     * @param data           待校检的数据集合
     * @param clazz          校检对象类型
     * @param clazz1         错误记录实体类
     * @param errorListCache 错误记录实体类缓存变量
     * @param key            缓存key
     * @param rowIndexList   数据行号集合
     * @param <T>            校检对象类型
     * @param <E>            错误记录实体类类型
     * @return 校检通过的数据集合
     */
    public static <T, E> List<T> validateAndCache(List<T> data, Class<T> clazz, Class<E> clazz1, Cache<String, List<E>> errorListCache, String key, List<Integer> rowIndexList) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<T> validList = new ArrayList<>();
        List<E> errorList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            T t = data.get(i);
            Set<ConstraintViolation<T>> violations = validator.validate(t);
            if (violations.isEmpty()) {
                validList.add(t);
            } else {
                StringBuilder sb = new StringBuilder();
                for (ConstraintViolation<T> violation : violations) {
                    Method getPropertyValueMap = clazz.getMethod("getPropertyValueMap");
                    Map<String, String> propertyValueMap = (Map<String, String>) getPropertyValueMap.invoke(null);
                    String property = propertyValueMap.get(violation.getPropertyPath().toString());
                    String message = violation.getMessage();

                    int rowIndex = rowIndexList.get(i);
                    Method getPropertyIndexMap = clazz.getMethod("getPropertyIndexMap");
                    Map<String, Integer> propertyIndexMap = (Map<String, Integer>) getPropertyIndexMap.invoke(null);
                    int columnIndex = propertyIndexMap.get(violation.getPropertyPath().toString());
                    sb.append("第").append(rowIndex + 1).append("行").append("，第").append(columnIndex + 1).append("列，").append("【").append(property).append("】").append(message).append("; ");

                }
                // 错误报告记录
                E errorEntity = BeanUtil.covertBean(t, clazz1);
                Method setErrorMsgMethod = errorEntity.getClass().getMethod("setErrorMsg", String.class);
                setErrorMsgMethod.invoke(errorEntity, sb.toString());
                errorList.add(errorEntity);
            }
        }
        if (!errorList.isEmpty()) {
            List<E> cachedErrorList = errorListCache.get(key);
            if (cachedErrorList == null) {
                cachedErrorList = new ArrayList<>();
            }
            cachedErrorList.addAll(errorList);
            errorListCache.put(key, cachedErrorList);
        }

        return validList;
    }
}
