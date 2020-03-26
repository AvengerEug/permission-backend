package com.eugene.sumarry.permission.controller;

import com.eugene.sumarry.permission.basic.Message;
import com.eugene.sumarry.permission.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-role")
public class UserRoleController extends BaseController {

    @Autowired
    private UserRoleService userRoleService;

    @DeleteMapping("/{userId}")
    public Message deleteUserRole(@PathVariable("userId") Long userId) {
        userRoleService.deleteUserRoles(userId);
        return Message.ok();
    }
}
