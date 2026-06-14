package com.md.basePlatform.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 分页结果封装.
 *
 * @param <T> 列表元素类型
 */
public class PageResult<T> {

    private final long total;
    private final int page;
    private final int size;
    private final List<T> items;

    /**
     * 构造分页结果.
     *
     * @param total 总记录数
     * @param page  当前页码
     * @param size  每页条数
     * @param items 当前页数据
     */
    public PageResult(long total, int page, int size, List<T> items) {
        this.total = total;
        this.page = page;
        this.size = size;
        this.items = Collections.unmodifiableList(new ArrayList<>(items));
    }

    public long getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public List<T> getItems() {
        return items;
    }
}
