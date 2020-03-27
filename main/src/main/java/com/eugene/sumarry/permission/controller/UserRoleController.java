package com.eugene.sumarry.permission.controller;

import com.eugene.sumarry.permission.anno.AuthApiPerm;
import com.eugene.sumarry.permission.basic.Message;
import com.eugene.sumarry.permission.model.UserRole;
import com.eugene.sumarry.permission.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-role")
public class UserRoleController extends BaseController {

    @Autowired
    private UserRoleService userRoleService;

    @AuthApiPerm
    @DeleteMapping("/{userId}")
    public Message deleteUserRole(@PathVariable("userId") Long userId) {
        userRoleService.deleteUserRoles(userId);
        return Message.ok();
    }

    @AuthApiPerm
    @PutMapping("/{updatedTarget}")
    public Message updateUserRole(
            @RequestBody List<UserRole> userRoleList,
            @PathVariable("updatedTarget") Long updatedTarget) {
        userRoleService.updateUserRoles(userRoleList, updatedTarget);
        return Message.ok();
    }

    @AuthApiPerm
    @PostMapping("/{addedTarget}")
    public Message addUserRoles(
            @RequestBody List<UserRole> userRoles,
            @PathVariable("addedTarget") Long addedTarget) {

        userRoleService.addUserRoles(userRoles, addedTarget);
        return Message.ok();
    }
}
