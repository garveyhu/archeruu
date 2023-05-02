package com.archeruu.core.utils;


import com.github.pagehelper.IPage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 分页公共方法
 *
 * @author Archer
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageUtil {
    /**
     * 获得分页对象
     */
    public static IPage getPageFormData(int start, int length) {
        return new PageImpl(getPageNum(start, length), length);
    }

    /**
     * 计算页数
     */
    private static int getPageNum(int start, int length) {
        return length > 0 && start >= 0 ? start / length + 1 : 1;
    }

    @AllArgsConstructor
    private final static class PageImpl implements IPage {
        private final Integer pageNum;
        private final Integer pageSize;

        @Override
        public Integer getPageNum() {
            return pageNum;
        }

        @Override
        public Integer getPageSize() {
            return pageSize;
        }

        @Override
        public String getOrderBy() {
            return null;
        }
    }

}
