package com.archeruu.core.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.archeruu.core.utils.PageUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.IPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 起始页、页长度虚拟实体类
 *
 * @author Archer
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractPageRequestVO extends AbstractBaseRequestVO {

    /**
     * 长度
     */
    private Integer length;

    /**
     * 起始位置
     */
    private Integer start;

    /**
     * 获取分页对象
     *
     * @return 分页对象
     */
    @JsonIgnore
    @JSONField(serialize = false)
    public IPage getPage() {
        return PageUtil.getPageFormData(this.start, this.length);
    }
}
