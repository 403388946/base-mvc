package com.base.shiro.service;


import com.base.cache.service.CacheTService;
import com.base.core.utils.Page;
import com.base.shiro.mapper.RoleMapper;
import com.base.shiro.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service(value = "roleService")
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private CacheTService<Role> cacheTService;

    @Autowired
    private ResourceService resourceService;

    public Role save(Role role) {
        if(role.getId() != null) {
            if(roleMapper.update(role) > 0) {
                return roleMapper.getOne(role.getId());
            }
        } else {
            if(roleMapper.insert(role) > 0) {
                return roleMapper.getOne(role.getId());
            }
        }
        return null;
    }

    public Integer updateRole(Role role) {

        return roleMapper.update(role);
    }

    public int deleteRole(Long roleId) {
        return roleMapper.delete(roleId);
    }

    public Role findOne(Long roleId) {
        return roleMapper.getOne(roleId);
    }

    public List<Role> findAllRole() {
        return roleMapper.findAllRoles();
    }

    public Page<Role> findAll(Page<Role> page) {
        List<Role> rows = roleMapper.findRoles(page);
        Integer total = roleMapper.countRoles(page);
        page.setRows(rows);
        page.setTotal(total);
        return page;
    }

    public Set<String> findAllRoles() {
        Set<String> roles = new HashSet<>();
        List<Role> roleList = roleMapper.findAllRoles();
        for(Role role : roleList) {
            roles.add(role.getName());
        }
        return roles;
    }

    public Set<String> findRoles(Long... roleIds) {
        Set<String> roles = new HashSet<>();
        List<Role> roleList = roleMapper.findIds(roleIds);
        for(Role role : roleList) {
            roles.add(role.getName());
        }
        return roles;
    }

    public Set<String> findPermissions(Long roleId) {
        return resourceService.findPermissions(roleId);
    }

    public Set<String> findAllPermissions() {
        return resourceService.findAllPermissions();
    }
}
