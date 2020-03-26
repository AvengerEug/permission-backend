package com.eugene.sumarry.permission.controller;

import com.eugene.sumarry.permission.anno.AuthApiPerm;
import com.eugene.sumarry.permission.basic.Message;
import com.eugene.sumarry.permission.basic.RequestContext;
import com.eugene.sumarry.permission.exception.BusinessException;
import com.eugene.sumarry.permission.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public Message login(@RequestBody Map<String, String> params) throws BusinessException {
        String jwtToken = userService.login(params.get("userName"), params.get("password"));
        return Message.ok(jwtToken);
    }

    @AuthApiPerm
    @GetMapping(value = "/fetch-all-info")
    public Message fetchAllInfo() {
        return Message.ok(userService.fetchAllInfo());
    }

    @AuthApiPerm
    @PutMapping(value = "/{userId}")
    public Message updateUserById(@PathVariable(value = "userId") Integer userId) {
        System.out.println(RequestContext.getHttpServletRequestParam("userIdParam"));
        return Message.ok("PathVariable: " + userId);
    }

}
