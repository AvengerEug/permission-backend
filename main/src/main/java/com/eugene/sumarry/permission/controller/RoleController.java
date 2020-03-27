package com.eugene.sumarry.permission.controller;

import com.eugene.sumarry.permission.anno.AuthApiPerm;
import com.eugene.sumarry.permission.basic.Message;
import com.eugene.sumarry.permission.model.Role;
import com.eugene.sumarry.permission.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 创建角色, 若角色中存在权限，则一并创建
     * @param role
     * @return
     */
    @AuthApiPerm
    @PostMapping
    public Message create(@Validated @RequestBody Role role) {
        return Message.ok(roleService.create(role));
    }

    @AuthApiPerm
    @PostMapping("/batch")
    public Message batchCreate(@RequestBody List<Role> roles) {
        roleService.batchCreate(roles);
        return Message.ok();
    }
}
