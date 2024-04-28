package com.base.shiro.mapper;

import com.base.shiro.model.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("resourceMapper")
public interface ResourceMapper {

    int insertBySelective(Resource resource);

    int updateBySelective(Resource resource);

    int delete(@Param("id") Long id);

    Resource get(@Param("id") Long id);

    List<Resource> findAll();

    List<Resource> findByParentId(@Param("parentId") Long parentId);


    int saveRole4Permission(@Param("permissionId") Long permissionId, @Param("roleId") Long roleId);

    List<Long> findPermissions(@Param("roleId") Long roleId);
    List<Resource> queryPermissions(@Param("roleId") Long roleId);

    int deletePIdByRoleId(@Param("roleId") Long roleId);
}