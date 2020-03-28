package com.eugene.sumarry.permission.service.impl;

import com.eugene.sumarry.jwtutil.entry.JwtSign;
import com.eugene.sumarry.jwtutil.model.JwtProperty;
import com.eugene.sumarry.permission.exception.BusinessException;
import com.eugene.sumarry.permission.dao.UserDao;
import com.eugene.sumarry.permission.model.User;
import com.eugene.sumarry.permission.service.UserService;
import com.eugene.sumarry.permission.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtSign jwtSign;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String login(String userName, String password) throws BusinessException {
        return loginHandler(userName, password);
    }

    private String loginHandler(String userName, String password) throws BusinessException {
        User user = userDao.checkLoginInfo(userName, password);

        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        if (!redisUtil.isExists(jwtSign.buildJwtTokenRedisKey(userName))) {
            String newJwtToken = jwtSign.sign(userName, user);
            redisUtil.set(jwtSign.buildJwtTokenRedisKey(userName), newJwtToken, jwtSign.getJwtProperty().getExpiration());
            return newJwtToken;
        }

        return (String)redisUtil.get(jwtSign.buildJwtTokenRedisKey(userName));
    }

    @Override
    public List<User> fetchAllInfo() {
        return userDao.getAll();
    }
}
