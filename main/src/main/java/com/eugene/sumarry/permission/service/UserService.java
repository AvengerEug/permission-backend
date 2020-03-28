package com.eugene.sumarry.permission.service;

import com.eugene.sumarry.permission.exception.BusinessException;
import com.eugene.sumarry.permission.model.User;

import java.util.List;

public interface UserService extends BaseService<User, Long> {

    String login(String userName, String password) throws BusinessException;

    List<User> fetchAllInfo();
}
