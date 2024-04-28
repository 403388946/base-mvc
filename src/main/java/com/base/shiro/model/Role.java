package com.base.shiro.model;

import java.io.Serializable;


public class Role implements Serializable {
    private Long id; //编号
    private String name; //角色标识 程序中判断使用,如"admin"
    private Boolean available = Boolean.TRUE; //是否可用,如果不可用将不会添加给用户

    public Role() {
    }

    public Role(String name, Boolean available) {
        this.name = name;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
