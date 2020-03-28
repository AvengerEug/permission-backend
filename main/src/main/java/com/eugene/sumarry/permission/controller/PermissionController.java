package com.eugene.sumarry.permission.controller;

import com.eugene.sumarry.permission.anno.AuthApiPerm;
import com.eugene.sumarry.permission.basic.Message;
import com.eugene.sumarry.permission.model.Permission;
import com.eugene.sumarry.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController {


    @Autowired
    private PermissionService permissionService;

    @AuthApiPerm
    @PostMapping
    public Message create(
            @Validated @RequestBody Permission permission) {
        return Message.ok(permissionService.create(permission));
    }

    @AuthApiPerm
    @PostMapping("/batch")
    public Message batchCreate(
            // TODO 需要解决校验list中的某个参数
            @RequestBody List<Permission> permissions) {
        permissionService.batchCreate(permissions);
        return Message.ok();
    }

}
