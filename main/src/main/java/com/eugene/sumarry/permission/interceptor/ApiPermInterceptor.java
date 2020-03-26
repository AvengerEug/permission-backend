package com.eugene.sumarry.permission.interceptor;

import com.eugene.sumarry.permission.anno.AuthApiPerm;
import com.eugene.sumarry.permission.basic.RequestContext;
import com.eugene.sumarry.permission.dao.UserRoleDao;
import com.eugene.sumarry.permission.exception.BusinessException;
import com.eugene.sumarry.permission.model.Permission;
import com.eugene.sumarry.permission.model.RolePermission;
import com.eugene.sumarry.permission.model.UserRole;
import com.eugene.sumarry.permission.service.UserRoleService;
import com.eugene.sumarry.permission.utils.SpringContextHolder;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiPermInterceptor implements HandlerInterceptor {

    private static final String HASH_TAG = "#";

    private static final Logger logger = LoggerFactory.getLogger(ApiPermInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            if (handlerMethod.hasMethodAnnotation(AuthApiPerm.class)) {

                if (ObjectUtils.isEmpty(RequestContext.getCurrentId())) {
                    logger.error("无token, 无法认证api权限");
                    // 开发人员看到此异常，要么就是在没配置api权限至db的情况话就使用了@AuthApiPerm注解
                    // 要么就是此api需要当前登录的用户id但是没有在JWTAuthFilter中被过滤
                    throw new BusinessException("无token, 无法认证api权限");
                }
                logger.info("从redis中查看, 当前用户是否有访问此api的权限");
                logger.info("当前权限的key为: {}", buildApiPermKey(handlerMethod));
                logger.info("当前用户id为: {}", RequestContext.getCurrentId());

                List<UserRole> userRoles = findUserRoleByUserId();
                List<Permission> ownPermissions = getAllPermission(userRoles);
                if (!authApiPerm(ownPermissions, buildApiPermKey(handlerMethod))) {
                    handlerResponse(response);
                    return false;
                }

            }
        }

        return true;
    }
    
    private void handlerResponse(HttpServletResponse response) throws IOException {
        response.sendError(Response.SC_FORBIDDEN, "No permission access this api");
    }

    private String getCurrentClassName(HandlerMethod handlerMethod) {
        return handlerMethod.getBean().getClass().getSimpleName();
    }

    private String getCurrentMethodsName(HandlerMethod handlerMethod) {
        return handlerMethod.getMethod().getName();
    }

    private String buildApiPermKey(HandlerMethod handlerMethod) {
        return getCurrentClassName(handlerMethod) + HASH_TAG + getCurrentMethodsName(handlerMethod);
    }

    private List<UserRole> findUserRoleByUserId() {
        UserRoleService userRoleService = SpringContextHolder.getBean(UserRoleService.class);
        return userRoleService.fetchUserRoles((Long) RequestContext.getCurrentId());
    }

    private List<Permission> getAllPermission(List<UserRole> userRoles) {
        List<Permission> ownPermission = new ArrayList<>();
        List<String> permissionKeys = new ArrayList<>();
        List<String> ownRoleNames = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userRoles)) {
            for (UserRole userRole : userRoles) {
                ownRoleNames.add(userRole.getRoleName());
                List<RolePermission> rolePermissions = userRole.getRole().getRolePermissions();
                if (!CollectionUtils.isEmpty(rolePermissions)) {
                    for (RolePermission rolePermission : rolePermissions) {
                        ownPermission.add(rolePermission.getPermission());
                        permissionKeys.add(rolePermission.getPermission().getPermissionKey());
                    }
                }
            }
        }

        logger.info("当前用户ID:{}, 拥有的角色名: {}, 拥有的权限key: {}", RequestContext.getCurrentId(), ownRoleNames, permissionKeys);

        return ownPermission;
    }

    private boolean authApiPerm(List<Permission> ownPermission, String permissionKey) {
        for (Permission permission : ownPermission) {
            if (permission.getPermissionKey().contains(permissionKey)) {
                return true;
            }
        }

        return false;
    }

}
