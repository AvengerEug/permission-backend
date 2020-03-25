/*
 用户角色表
 */
CREATE TABLE user_role(
  user_role_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  user_name VARCHAR(225),
  role_id INT,
  role_name VARCHAR(225)
)

/**
  角色表
 */
CREATE TABLE role(
  role_id INT PRIMARY KEY AUTO_INCREMENT,
  role_name VARCHAR(225)
)

/**
  角色权限表
 */
CREATE TABLE role_permission(
  role_permission_id INT PRIMARY KEY AUTO_INCREMENT,
  role_id INT,
  role_name VARCHAR(225),
  permission_id INT
)

/**
  权限表
 */
CREATE TABLE permission(
  permission_id INT PRIMARY KEY AUTO_INCREMENT,
  permission_name VARCHAR(255),
  permission_direction INT COMMENT "权限应用的方向: 目前有BACKEND(0)和FRONTEND(1)",
  parent_permission_id INT COMMENT "父权限ID, 类型为api的为null",
  permission_type INT COMMENT "权限类型: html(0)、api(1)"
)

/**
  初始化后台api权限, 若有新增api则需要手动插入
 */
INSERT INTO permission(
permission_name,
permission_direction,
parent_permission_id,
permission_type)
VALUES()

