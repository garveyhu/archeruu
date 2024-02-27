package com.archeruu.core.utils.tree;


import java.util.List;

/**
 * 树形数据接口
 *
 * @author Archer
 */
public interface DataTree<T> {

    Long getId();

    Long getParentId();

    void setChildList(List<T> childList);

    List<T> getChildList();
}
