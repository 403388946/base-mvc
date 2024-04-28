package com.base.shiro.service;



import com.base.core.utils.ListUtils;
import com.base.shiro.mapper.ResourceMapper;
import com.base.shiro.model.Resource;
import com.base.shiro.utils.ConstantsAuth;
import com.base.shiro.utils.LoginUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service(value = "resourceService")
public class ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    public Resource saveResource(Resource resource) {
        if(resource.getId() != null) {
            if(resourceMapper.updateBySelective(resource) > 0) {
                return resourceMapper.get(resource.getId());
            }
        } else {
            if(resourceMapper.insertBySelective(resource) > 0) {
                return resourceMapper.get(resource.getId());
            }
        }
        return null;
    }

    public Resource updateResource(Resource resource) {
        if(resourceMapper.updateBySelective(resource) > 0) {
            return resourceMapper.get(resource.getId());
        }
        return null;
    }

    public int deleteResource(Long resourceId) {
        return resourceMapper.delete(resourceId);
    }

    public Resource findOne(Long resourceId) {
        return resourceMapper.get(resourceId);
    }


    public List<Resource> findAll() {
        return resourceMapper.findAll();
    }

    public List<Long> findByRoleId(Long roleId) {
        return resourceMapper.findPermissions(roleId);
    }

    public List<Resource> findByParentId(Long parentId) {
        List<Resource> menus = new ArrayList<>();
        //查所有一级菜单
        List<Resource> oneLevelMenus = resourceMapper.findByParentId(parentId);
        for(Resource parent : oneLevelMenus) {
            parent.setChildren(this.findByParentId(parent.getId()));
            menus.add(parent);
        }
        return menus;
    }

    public Set<String> findPermissions(Long roleId) {
        List<Resource> resources = resourceMapper.queryPermissions(roleId);
        Set<String> permissions = new HashSet<>();
        for(Resource resource : resources) {
            permissions.add(resource.getPermission());
        }
        return permissions;
    }

    public Set<String> findAllPermissions() {
        Set<String> permissions = new HashSet<>();
        permissions.addAll(ListUtils.extractToStringList(resourceMapper.findAll(), "permission"));
        return permissions;
    }

    public List<Resource> findMenus(Set<String> permissions) {
        List<Resource> allResources = findAll();
        List<Resource> menus = new ArrayList<>();
        for(Resource resource : allResources) {
            if(resource.isRootNode()) {
                continue;
            }
            if(resource.getType() != Resource.ResourceType.menu) {
                continue;
            }
            if(!hasPermission(permissions, resource)) {
                continue;
            }
            menus.add(resource);
        }
        return menus;
    }

    public List<Resource> findMenusByRootId(Set<String> permissions, Long parentId) {
        List<Resource> menus = new ArrayList<>();
        //查所有一级菜单
        List<Resource> oneLevelMenus = resourceMapper.findByParentId(parentId);
        for(Resource parent : oneLevelMenus) {
            if(parent.isRootNode()) {
                continue;
            }
            if(parent.getType() != Resource.ResourceType.menu) {
                continue;
            }
            if(!hasPermission(permissions, parent)) {
                continue;
            }
            parent.setChildren(this.findMenusByRootId(permissions, parent.getId()));
            menus.add(parent);
        }
        return menus;
    }

    public int saveRole4Permission(Long permissionId, Long roleId) {
        return resourceMapper.saveRole4Permission(permissionId, roleId);
    }

    public int deletePIdByRoleId(Long roleId) {
        return resourceMapper.deletePIdByRoleId(roleId);
    }


    private boolean hasPermission(Set<String> permissions, Resource resource) {
        String loginName = LoginUtils.getLoginName();
        //是否最高权限用户 是则加入权限
        if(StringUtils.equals(ConstantsAuth.ROOT_PERMISSION, loginName)) {
            return true;
        }
        if(StringUtils.isEmpty(resource.getPermission())) {
            return true;
        }
        for(String permission : permissions) {
            WildcardPermission p1 = new WildcardPermission(permission);
            WildcardPermission p2 = new WildcardPermission(resource.getPermission());
            if(p1.implies(p2) || p2.implies(p1)) {
                return true;
            }
        }
        return false;
    }

}
