package com.base.shiro.controller;

import com.base.shiro.model.Resource;
import com.base.shiro.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;


    @RequestMapping(value = "/findData")
    @ResponseBody
    public Resource findData(Long id) {
        Resource parent = resourceService.findOne(id);
        List<Resource> children = resourceService.findByParentId(parent.getId());
        parent.setChildren(children);
        return parent;
    }
}
