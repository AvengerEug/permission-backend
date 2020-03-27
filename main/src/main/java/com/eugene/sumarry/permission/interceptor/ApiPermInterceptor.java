package com.eugene.sumarry.permission.interceptor;

import com.eugene.sumarry.permission.Enum.HttpErrorStatus;
import com.eugene.sumarry.permission.anno.AuthApiPerm;
import com.eugene.sumarry.permission.basic.RequestContext;
import com.eugene.sumarry.permission.dao.UserRoleDao;
import com.eugene.sumarry.permission.exception.BusinessException;
import com.eugene.sumarry.permission.model.Permission;
import com.eugene.sumarry.permission.model.RolePermission;
import com.eugene.sumarry.permission.model.UserRole;
import com.eugene.sumarry.permission.service.UserRoleService;
import com.eugene.sumarry.permission.utils.Assert;
import com.eugene.sumarry.permission.utils.PermissionUtil;
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
import java.io.PrintWriter;
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
                String apiPermKey = buildApiPermKey(handlerMethod);
                PermissionUtil.traversal(userRoles, null, (permissions) -> {
                    List<String> roleNames = new ArrayList<>();
                    for (UserRole userRole : userRoles) {
                        roleNames.add(userRole.getRole().getRoleName());
                    }

                    List<String> permissionKeys = new ArrayList<>();
                    for (Permission permission : permissions) {
                        permissionKeys.add(permission.getPermissionKey());
                    }

                    logger.info("当前用户ID:{}, 拥有的角色: {} 拥有的权限key: {}", RequestContext.getCurrentId(), roleNames, permissionKeys);
                    if (!authApiPerm(permissions, apiPermKey)) {
                        try {
                            handlerResponse(response);
                            // 此处抛出运行时异常是因为PermissionUtil的traversal方法的设计。
                            // 若不喜欢此方法的设计，不使用它
                            Assert.stopProcess(
                                    HttpErrorStatus.NOT_ACCESS_API_PERMISSION.getCode(),
                                    HttpErrorStatus.NOT_ACCESS_API_PERMISSION.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        return true;
    }
    
    private void handlerResponse(HttpServletResponse response) throws IOException {
        response.sendError(Response.SC_FORBIDDEN);
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

    private boolean authApiPerm(List<Permission> ownPermission, String permissionKey) {
        for (Permission permission : ownPermission) {
            if (permission.getPermissionKey().contains(permissionKey)) {
                return true;
            }
        }

        return false;
    }

}
