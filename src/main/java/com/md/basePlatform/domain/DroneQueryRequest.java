package com.md.basePlatform.domain;

/**
 * 无人机查询参数.
 */
public class DroneQueryRequest {

    private int page = 1;
    private int size = 10;
    private String keyword;

    /**
     * 规范化分页参数.
     */
    public void normalize() {
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = 10;
        }
        if (size > 100) {
            size = 100;
        }
    }

    /**
     * 计算 SQL offset.
     *
     * @return offset
     */
    public int getOffset() {
        return (page - 1) * size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
