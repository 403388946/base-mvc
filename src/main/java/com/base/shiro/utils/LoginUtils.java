package com.base.shiro.utils;

import com.base.core.spring.SpringUtils;
import com.base.shiro.model.User;
import com.base.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;

import java.util.Set;

/**
 * @author YiMing on 2017/05/17
 */
public class LoginUtils {

    private static UserService userService = SpringUtils.getBean(UserService.class);

    public static String getLoginName() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    public static User getCurrentUser() {
        return userService.findByUsername(getLoginName());
    }

    public static Set<String> getPermissions() {
        return userService.findPermissions(getLoginName());
    }

    public static boolean isPermission(String permission) {
        return getPermissions().contains(permission);
    }
}
