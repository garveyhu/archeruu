package com.archeruu.core.utils.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.archeruu.core.utils.excel.converter.YesOrNoConverter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Data;

/**
 * 实体类声明demo
 *
 * @author Archer
 */
@Data
public class PropertyDemo implements PropertyMap, NoticeHeight {

    public static final String notice = "填写须知： \n" +
            "1.请勿修改当前模板结构\n" +
            "2.以下示例数据，导入前请先删除\n" +
            "3.【所属机构】非顶级部门填写，请查找系统编码填写，例如：科技部\n" +
            "4.【机构类型】填支行或分行，会插入默认值\n" +
            "5.【所属机构】顶级节点填写0，其余写父节点编码\n" +
            "6.红色为必填，黑色为选填\n" +
            "7.请严格按照填写规则输入数据，不合规的数据无法成功导入";

    @NotEmpty
    @ExcelProperty(value = {notice, "名称"}, index = 0)
    private String name;

    @Pattern(regexp = "^(0|1)$", message = "只能填是或否")
    @ExcelProperty(value = {notice, "是否有效"}, converter = YesOrNoConverter.class, index = 1)
    private String flag;

    @Override
    public String getNotice() {
        return notice;
    }
}
