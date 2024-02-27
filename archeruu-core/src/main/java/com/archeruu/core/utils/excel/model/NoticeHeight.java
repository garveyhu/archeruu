package com.archeruu.core.utils.excel.model;

/**
 * 填写须知行高
 *
 * @author Archer
 */
public interface NoticeHeight {

    String getNotice();

    default int getHeadHeight() {
        int count = 0;
        for (int i = 0; i < getNotice().length(); i++) {
            if (getNotice().charAt(i) == '\n'){
                count++;
            }
        }
        return (count + 2) * 15;
    }
}
