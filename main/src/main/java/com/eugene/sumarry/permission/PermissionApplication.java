package com.eugene.sumarry.permission;

import com.eugene.sumarry.jwtutil.anno.EnableJwtSign;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.eugene.sumarry.permission.dao")
@EnableJwtSign
public class PermissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermissionApplication.class, args);
    }
}
