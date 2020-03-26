package com.eugene.sumarry.permission.dao;

import com.eugene.sumarry.permission.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao extends BaseDao<User, Long> {

    User checkLoginInfo(@Param("userName") String userName, @Param("password") String password);
}
