package com.base.shiro.controller;

import com.base.core.bind.annotation.CurrentUser;
import com.base.shiro.model.Resource;
import com.base.shiro.model.User;
import com.base.shiro.service.ResourceService;
import com.base.shiro.service.UserService;
import com.base.shiro.utils.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

@Controller
public class IndexController {

    private static final Long MENU_ROOT_ID = 1l;

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;



    @RequestMapping(value = "/index")
    public String index(Model model) {
        User loginUser = LoginUtils.getCurrentUser();
        Set<String> permissions = userService.findPermissions(loginUser.getUsername());
        List<Resource> menus = resourceService.findMenusByRootId(permissions, MENU_ROOT_ID);
        model.addAttribute("realName", loginUser.getRealName());
        model.addAttribute("menus", menus);
        return "index";
    }

    @RequestMapping(value = "/menus")
    @ResponseBody
    public List<Resource> menus(@CurrentUser User loginUser) {
        Set<String> permissions = userService.findPermissions(loginUser.getUsername());
        List<Resource> menus = resourceService.findMenusByRootId(permissions, MENU_ROOT_ID);
        return menus;
    }

}
