package com.archeruu.core.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import java.util.Date;
import java.util.Objects;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis自动填充字段
 *
 * @author Archer
 */
@Configuration
@ConditionalOnClass(MetaObjectHandler.class)
public class BaseMyBatisMetaObjectHandler implements MetaObjectHandler {
    private static final String UPDATE_TIME_KEY = "updateTime";
    private static final String UPDATE_ID_KEY = "updateId";
    private static final String CREATE_TIME_KEY = "createTime";
    private static final String CREATE_ID = "createId";

    private static Long getCurrentUserId() {
        return 0L;
    }

    /**
     * 新增的时候自动填充
     *
     * @param metaObject metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Long currentUserId = getCurrentUserId();
        Date date = new Date();

        Object createTime = getFieldValByName(CREATE_TIME_KEY, metaObject);
        setFieldValByName(CREATE_TIME_KEY, Objects.isNull(createTime) ? date : createTime, metaObject);

        Object createId = getFieldValByName(CREATE_ID, metaObject);
        setFieldValByName(CREATE_ID, Objects.isNull(createId) ? currentUserId : createId, metaObject);

        Object updateTime = getFieldValByName(UPDATE_TIME_KEY, metaObject);
        setFieldValByName(UPDATE_TIME_KEY, Objects.isNull(updateTime) ? date : updateTime, metaObject);

        Object updateId = getFieldValByName(UPDATE_ID_KEY, metaObject);
        setFieldValByName(UPDATE_ID_KEY, Objects.isNull(updateId) ? currentUserId : updateId, metaObject);
    }

    /**
     * 更新的时候自动填充
     *
     * @param metaObject metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName(UPDATE_TIME_KEY, new Date(), metaObject);
        setFieldValByName(UPDATE_ID_KEY, getCurrentUserId(), metaObject);
    }
}
