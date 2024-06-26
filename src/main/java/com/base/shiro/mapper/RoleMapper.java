package com.base.shiro.mapper;

import com.base.core.utils.Page;
import com.base.shiro.model.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleMapper {

    @Delete({
        "delete from sys_roles where id = #{id,jdbcType=BIGINT}"
    })
    int delete(Long id);


    int insert(Role record);


    Role getOne(@Param("id") Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role
     *
     * @mbggenerated
     */
    int update(Role record);


    List<Role> findAllRoles();
    List<Role> findRoles(Page<Role> page);
    Integer countRoles(Page<Role> page);

    List<Role> findIds(@Param("ids") Long... roleIds);
}