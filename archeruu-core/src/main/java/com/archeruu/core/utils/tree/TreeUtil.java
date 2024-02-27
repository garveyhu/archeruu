package com.archeruu.core.utils.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

/**
 * 树形结构数据
 *
 * @author Archer
 */
public class TreeUtil {

    /**
     * 获取树形结构数据
     * @param topId 父节点id
     * @param entityList 数据列表
     * @return 树形结构数据
     * @param <T> 树形数据类型
     */
    public static <T extends DataTree<T>> List<T> getTreeList(Long topId, List<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return new ArrayList<>();
        }
        List<T> resultList = new ArrayList<>();
        Map<Object, T> treeMap = new HashMap<>(16);
        T itemTree;

        for (int i = 0; i < entityList.size() && !entityList.isEmpty(); i++) {
            itemTree = entityList.get(i);
            // 所有数据放到map中
            treeMap.put(itemTree.getId(), itemTree);
            // 获取顶级节点
            if (topId.equals(itemTree.getParentId()) || itemTree.getParentId() == null) {
                resultList.add(itemTree);
            }
        }

        // 循环数据，把数据放到上一级的children中
        for (int i = 0; i < entityList.size() && !entityList.isEmpty(); i++) {
            itemTree = entityList.get(i);
            T data = treeMap.get(itemTree.getParentId());
            // 判断有没有父节点
            if (data != null) {
                if (data.getChildList() == null) {
                    data.setChildList(new ArrayList<>());
                }
                // 把子节点放到父节点childrenList中
                data.getChildList().add(itemTree);
                // 将数据放回到map中
                treeMap.put(itemTree.getParentId(), data);
            }
        }
        return resultList;
    }
}
