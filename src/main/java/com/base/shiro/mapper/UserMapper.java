package com.base.shiro.mapper;

import com.base.core.utils.Page;
import com.base.shiro.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Delete({
        "delete from sys_user where id = #{id,jdbcType=BIGINT} and user_name != 'admin'"
    })
    int delete(Long id);

    int insertSelective(User record);


    User getOne(@Param("id") Long id);

    int update(User record);

    User findUserByName(@Param("userName") String userName);

    List<User> findUsers(@Param("userName") String userName, @Param("start") int start, @Param("pageSize") int pageSize);

    List<Long> findAll();

    List<User> queryByPage(Page page);

    Integer queryByPageCount(Page page);
}