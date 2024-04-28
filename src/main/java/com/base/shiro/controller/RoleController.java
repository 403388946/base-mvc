package com.base.shiro.controller;

import com.google.common.collect.Maps;
import com.base.core.utils.Page;
import com.base.shiro.model.Resource;
import com.base.shiro.model.Role;
import com.base.shiro.model.State;
import com.base.shiro.service.ResourceService;
import com.base.shiro.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;


    @RequestMapping(value = "/index")
    public String index() {
        return "shiro/role/list";
    }

    @RequestMapping(value = "/findData")
    @ResponseBody
    public Page<Role> findData(String role, Page<Role> page) {
        Map<String, Object> param = Maps.newHashMap();
        if(StringUtils.isNotEmpty(role)) {
            param.put("role", "%" + role + "%");
        }
        page.setParamMap(param);
        return roleService.findAll(page);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(Role role) {
        Role saved = roleService.save(role);
        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "保存失败!");
        if(saved.getId() != null) {
            result.put("status", 1);
            result.put("message", "保存成功!");
        }
        return result;
    }

    @RequestMapping(value = "/updateResource")
    @ResponseBody
    public boolean updateResource(Long roleId, String ids) {
        Role saved = new Role();
        saved.setId(roleId);
        ids = StringUtils.removeStart(ids, "1,");
        if(StringUtils.isNotEmpty(ids)) {
            resourceService.deletePIdByRoleId(roleId);
            String[] roleIds = StringUtils.split(ids, ",");
            for (String id : roleIds) {
                resourceService.saveRole4Permission(Long.valueOf(id), roleId);
            }
        }
        return roleService.updateRole(saved) > 0;
    }

    @RequestMapping(value = "findResource")
    @ResponseBody
    public Resource findResource(Long id) {
        Role select = roleService.findOne(id);
        Resource parent = resourceService.findOne(1L);
        List<Resource> children = resourceService.findByParentId(parent.getId());
        List<Long> resourceIds = resourceService.findByRoleId(select.getId());
        children = children.stream().map(child -> {
            if(resourceIds.contains(child.getId())) {
                child.setState(new State(true, false, true));
            }
            List<Resource> childIn = child.getChildren().stream().map(childNext -> {
                if(resourceIds.contains(childNext.getId())) {
                    childNext.setState(new State(true, false, true));
                }
                return childNext;
            }).collect(Collectors.toList());
            child.setChildren(childIn);
            return child;
        }).collect(Collectors.toList());
        parent.setState(new State());
        parent.setChildren(children);
        return parent;
    }


    @RequestMapping(value = "delete")
    @ResponseBody
    public Map<String, Object> delete(Long id) {
        int flag = roleService.deleteRole(id);
        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "删除失败!");
        if(flag > 0) {
            result.put("status", 1);
            result.put("message", "删除成功!");
        }
        return result;
    }
}
