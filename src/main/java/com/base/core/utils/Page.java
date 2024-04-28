package com.base.core.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YiMing on 2017/05/17
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = -3323321457300243220L;



    /** 总记录数 */
    private long total;

    /** 当前页对应的记录列表 */
    private List<T> rows;

    /** 分页查询条件对应的参数Map */
    private Map<String, Object> paramMap;

    /** 排序字符串 */
    private String orderBy;
    /**一次取多少条**/
    private int limit;
    /**从第几条开始去**/
    private int offset;
    /**
     * 当前页 默认 1
     */
    private int current;


    public Page() {
        current = 1;
        paramMap = new HashMap<String, Object>();
    }

    /**
     * 计算总页数
     *
     * @param totalCount
     *            总记录数
     * @param pageSize
     *            每页显示记录数
     * @return int 总页数
     */
    public static long computeTotalPage(final long totalCount, final int pageSize) {
        return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
    }

    /**
     * 计算开始页
     *
     * @param currentPage
     *            当前页数
     * @param pageSize
     *            每页显示记录数
     * @return int 开始页
     */
    public static int computeStartPage(final int currentPage, final int pageSize) {
        return pageSize * (currentPage - 1);
    }

    /**
     * 计算结束页
     *
     * @param currentPage
     *            当前页数
     * @param pageSize
     *            每页显示记录数
     * @return int 结束页
     */
    public static int computeEndPage(final int currentPage, final int pageSize) {
        return pageSize * currentPage;
    }



    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}

