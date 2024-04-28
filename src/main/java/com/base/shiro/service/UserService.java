package com.base.shiro.service;



import com.base.cache.service.CacheTService;
import com.base.core.utils.Page;
import com.base.shiro.mapper.UserMapper;
import com.base.shiro.model.Role;
import com.base.shiro.model.User;
import com.base.shiro.utils.ConstantsAuth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service(value = "userService")
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordHelper passwordHelper;


    @Autowired
    private RoleService roleService;

    @Resource
    private Map<String, String> settings;

    @Autowired
    private CacheTService<User> cacheTService;

    /**
     * 创建用户
     *
     * @param user
     */
    public int save(User user) {
        //加密密码
        if (user.getId() != null) {
            return userMapper.update(user);
        } else {
            user.setTelephone(user.getUsername());
            user.setPassword(user.getUsername());
            user.setSalt(user.getUsername());
            return userMapper.insertSelective(user);
        }
    }

    public int deleteUser(Long userId) {
        return userMapper.delete(userId);
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param newPassword
     */
    public int savePassword(Long userId, String newPassword) {
        User user = userMapper.getOne(userId);
        if (StringUtils.isNotEmpty(newPassword)) {
            user.setPassword(newPassword);
        } else {
            user.setPassword(settings.get(ConstantsAuth.NEW_PASSWORD));
        }
        passwordHelper.encryptPassword(user);
        return userMapper.update(user);
    }

    /**
     * 修改密码
     *
     * @param username
     * @param newPassword
     */
    public int savePassword(String username, String newPassword) {
        User user = userMapper.findUserByName(username);
        if (StringUtils.isNotEmpty(newPassword)) {
            user.setPassword(newPassword);
        } else {
            user.setPassword(settings.get(ConstantsAuth.NEW_PASSWORD));
        }
        passwordHelper.encryptPassword(user);
        return userMapper.update(user);
    }

    public User findOne(Long userId) {
        return userMapper.getOne(userId);
    }

    public List<User> findAll() {
        return userMapper.findUsers(null, 0, 0);
    }

    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userMapper.findUserByName(username);
    }

    /**
     * 根据用户名查找其角色
     *
     * @param username
     * @return
     */
    public Set<String> findRoles(String username) {
        User user = findByUsername(username);
        if (user == null) {
            return Collections.emptySet();
        } else if (StringUtils.equals(ConstantsAuth.ROOT_PERMISSION, username)) {
            return roleService.findAllRoles();
        }
        //return roleService.findRoles(user.getRoleIdsList().toArray(new Long[0]));
        return null;
    }

    /**
     * 根据用户名查找其权限
     *
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username) {
        User user = findByUsername(username);
        if (user == null) {
            return Collections.emptySet();
        } else {
            //是否最高权限用户 是则返回全部权限
            if (StringUtils.equals(ConstantsAuth.ROOT_PERMISSION, username)) {
                return roleService.findAllPermissions();
            } else {
                return roleService.findPermissions(user.getRoleId());
            }
        }
    }

    public Page<User> findAllByPage(Page param) {
        List<User> rows = userMapper.queryByPage(param);
        Integer total = userMapper.queryByPageCount(param);
        param.setRows(rows);
        param.setTotal(total);
        return param;
    }

}
