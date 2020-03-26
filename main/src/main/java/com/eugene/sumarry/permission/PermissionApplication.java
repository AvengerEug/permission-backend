package com.eugene.sumarry.permission;

import com.eugene.sumarry.jwtutil.anno.EnableJwtSign;
import com.eugene.sumarry.permission.controller.UserController;
import com.eugene.sumarry.permission.model.UserRole;
import com.eugene.sumarry.permission.service.UserRoleService;
import com.eugene.sumarry.permission.utils.SpringContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.eugene.sumarry.permission.dao")
@EnableJwtSign
public class PermissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermissionApplication.class, args);
    }


    @Bean
    public ApplicationRunner applicationRunner() {

        // 拿db的权限数据全部load进缓存(用用户权限表去连接剩下的表)
        return (object) -> {
            SpringContextHolder.getBean(UserRoleService.class).findAll();
        };
    }
}
