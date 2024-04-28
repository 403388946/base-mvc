package com.base.core.model;


/**
 * @author: yiming
 * @DATE: 2020/5/6
 * @Description: 基础模型
 */
public class BaseModel {

    private Long id;

    /**
     * 是否已删除 0未删除 1已删除
     */
    private Integer deleted;
    private String createTime;
    private String updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
