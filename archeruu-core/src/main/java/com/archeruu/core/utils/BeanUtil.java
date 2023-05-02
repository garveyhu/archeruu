package com.archeruu.core.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.lang.NonNull;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean转换工具
 *
 * @author Archer
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BeanUtil {

    private static final ObjectMapper MAPPER = SpringContextUtil.getBean(ObjectMapper.class);

    @SneakyThrows
    public static String bean2Json(@NonNull Object obj) {
        StringWriter sw = new StringWriter();
        JsonGenerator gen = new JsonFactory().createGenerator(sw);
        MAPPER.writeValue(gen, obj);
        gen.close();
        return sw.toString();
    }

    @SneakyThrows
    public static <T> T json2Bean(@NonNull String jsonStr, @NonNull Class<T> objClass) {
        return MAPPER.readValue(jsonStr, objClass);
    }

    @SneakyThrows
    public static <S, T> T covertBean(@NonNull S source, @NonNull Class<T> objClass) {
        return MAPPER.convertValue(source, objClass);
    }

    @SneakyThrows
    public static <S, T> List<T> covertList(@NonNull List<S> sourceList, @NonNull Class<T> objClass) {
        List<T> targetList = new ArrayList<>();
        sourceList.forEach(s -> targetList.add(covertBean(s, objClass)));
        return targetList;
    }
}
