/*
 用户角色表
 */
CREATE TABLE user_role(
  user_role_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  user_name VARCHAR(225),
  role_id INT,
  role_name VARCHAR(225)
);

/**
  角色表
 */
CREATE TABLE role(
  role_id INT PRIMARY KEY AUTO_INCREMENT,
  role_name VARCHAR(225)
);

/**
  角色权限表
 */
CREATE TABLE role_permission(
  role_permission_id INT PRIMARY KEY AUTO_INCREMENT,
  role_id INT,
  role_name VARCHAR(225),
  permission_id INT
);

/**
  权限表
 */
CREATE TABLE permission(
  permission_id INT PRIMARY KEY AUTO_INCREMENT,
  permission_name VARCHAR(255),
  permission_direction INT COMMENT "权限应用的方向: 目前有BACKEND(0)和FRONTEND(1)。对应PermissionDirection类",
  parent_permission_id INT COMMENT "父权限ID, 类型为api的为null。",
  permission_type INT COMMENT "权限类型: HTML(0)、API(1)。对应PermissionType类",
  permission_key VARCHAR(255) COMMENT "权限的key, 权限类型为HTML时，key为前端的路由。权限类型为API时，key为类名#方法名"
);

/**
  初始化后台api权限, 若有新增的且需要认证权限的api则需要手动插入
 */
INSERT INTO permission(
permission_name,
permission_direction,
parent_permission_id,
permission_type,
permission_key)
VALUES
("获取用户列表", 0, null, 1, "UserController#fetchAllInfo"),
("根据用户ID更新信息", 0, null, 1, "UserController#updateUserById");

/**
  初始化两个角色: 管理员角色和测试角色
 */
INSERT INTO role(role_name) VALUES("管理员"),("测试人员");

/**
  为角色分配权限, 管理员拥有所有权限, 测试人员只拥有获取用户列表权限
 */
INSERT INTO role_permission(role_id, role_name, permission_id)
VALUES
((SELECT role_id FROM role WHERE role_name = "管理员"), "管理员", (SELECT permission_id FROM permission WHERE permission_key = "UserController#fetchAllInfo"));

INSERT INTO role_permission(role_id, role_name, permission_id)
VALUES
((SELECT role_id FROM role WHERE role_name = "管理员"), "管理员", (SELECT permission_id FROM permission WHERE permission_key = "UserController#updateUserById"));

INSERT INTO role_permission(role_id, role_name, permission_id)
VALUES
((SELECT role_id FROM role WHERE role_name = "测试人员"), "测试人员", (SELECT permission_id FROM permission WHERE permission_key = "UserController#fetchAllInfo"));

/**
  为用户赋予权限
 */
INSERT INTO user_role(user_id, user_name, role_id, role_name)
VALUES
((SELECT user_id FROM user WHERE user_name = "eugene"), "eugene", (SELECT role_id FROM role WHERE role_name = "管理员"), "管理员");

INSERT INTO user_role(user_id, user_name, role_id, role_name)
VALUES
((SELECT user_id FROM user WHERE user_name = "eugene2"), "eugene2", (SELECT role_id FROM role WHERE role_name = "测试人员"), "测试人员");


/**
  2020-03-26 新增两个api，添加权限并赋值给管理员角色
 */
-- 新增两个api的权限
INSERT INTO permission(
permission_name,
permission_direction,
parent_permission_id,
permission_type,
permission_key)
VALUES
("删除用户角色", 0, null, 1, "UserRoleController#deleteUserRole"),
("更新用户角色", 0, null, 1, "UserRoleController#updateUserRole");

-- 将权限赋值给admin
INSERT INTO role_permission(role_id, role_name, permission_id)
VALUES
((SELECT role_id FROM role WHERE role_name = "管理员"), "管理员", (SELECT permission_id FROM permission WHERE permission_key = "UserRoleController#deleteUserRole"));

INSERT INTO role_permission(role_id, role_name, permission_id)
VALUES
((SELECT role_id FROM role WHERE role_name = "管理员"), "管理员", (SELECT permission_id FROM permission WHERE permission_key = "UserRoleController#updateUserRole"));
