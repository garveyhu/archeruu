package com.archeruu.core.vo;

import com.archeruu.core.utils.BeanUtil;

/**
 * bean转换方法虚拟实体类
 *
 * @author Archer
 */
public abstract class AbstractBaseRequestVO {

    public <T> T toConvert(Class<T> tClass) {
        return BeanUtil.covertBean(this, tClass);
    }
}
