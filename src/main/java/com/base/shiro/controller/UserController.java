package com.base.shiro.controller;

import com.google.common.collect.Maps;
import com.base.core.utils.Page;
import com.base.shiro.model.User;
import com.base.shiro.service.RoleService;
import com.base.shiro.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/index")
    public String list(Model model) {
        return "shiro/user/index";
    }

    @RequestMapping(value = "/edit")
    public String edit(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAllRole());
        return "shiro/user/edit";
    }

    @RequestMapping(value = "findData")
    @ResponseBody
    public Page<User> findData(String username, Page page) {
        Map<String, String> paramMap = Maps.newHashMap();
        if(StringUtils.isNotEmpty(username)) {
            paramMap.put("username", "%" + username + "%");
        }
        page.setParamMap(paramMap);
        return userService.findAllByPage(page);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(User user) {
        Map<String, Object> result = new HashMap<>();
        int flag = userService.save(user);
        result.put("status", 0);
        result.put("message", "保存失败!");
        if(flag > 0) {
            result.put("status", 1);
            result.put("message", "保存成功!");
        }
        return result;
    }

    @RequestMapping(value = "/findUserName")
    @ResponseBody
    public Boolean findUserName(String username) {
        User had = userService.findByUsername(username);
        return had.getId() == null;
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public User get(Long id) {
        return userService.findOne(id);
    }



    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, Object> delete(Long id) {
        int flag = userService.deleteUser(id);
        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "删除失败!");
        if(flag > 0) {
            result.put("status", 1);
            result.put("message", "删除成功!");
        }
        return result;
    }


    @RequestMapping(value = "/reset")
    @ResponseBody
    public  Map<String, Object> reset(Long id) {
        int reset = userService.savePassword(id,null);
        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "重置失败!");
        if(reset > 0) {
            result.put("status", 1);
            result.put("message", "重置成功!");
        }
        return result;
    }




    private void setCommonData(Model model) {
        model.addAttribute("roleList", roleService.findAllRole());
    }
}
