# 权限管理后台

## 一、目标

|                             目标                             |                             状态                             |  开始时间  |  完成时间  |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :--------: | :--------: |
|        搭建spring boot后端项目, 集成jwt、mysql、redis        |                            已完成                            | 2020/03/24 | 2020/03/24 |
|               完成用户登录后使用jwt验证的功能                |                            已完成                            | 2020/03/24 | 2020/03/24 |
| 添加jwt操作util jar包, 包含生成jwtToken和解析jwtToken, 以及jwt配置的自动装配 |                            已完成                            | 2020/03/24 | 2020/03/24 |
|              权限功能数据库设计(包括前端和后端)              |                            已完成                            | 2020/03/25 | 2020/03/25 |
|  完成后台api的权限功能(先手动操作数据库为某个用户添加权限)   |                            已完成                            | 2020/03/25 | 2020/03/25 |
|                 完成后端对角色权限的增删改查                 | 1. 20%, 完成对用户角色的修改--- 2020/03/25</br>2. 添加新增用户角色、新增权限、批量新增权限、新增角色、批量新增角色api | 2020/03/25 |            |
| 前端集成后台登录api并设计管理员页面, 使用element ui tree组件渲染角色及权限 |                                                              |            |            |
|     管理员端设置对用户赋予角色、对角色赋予权限等相关功能     |                                                              |            |            |
|  前端每个页面集成路由，控制当前登录系统的用户的页面访问权限  |                                                              |            |            |
|                                                              |                                                              |            |            |



## 二、api文档

* 前提: 请求头的content-type为application/json

* 请求头中有token

* 管理员的token

  ```java
  eyJhbGciOiJIUzUxMiJ9.eyJldWdlbmUiOnsidXNlcklkIjoxLCJ1c2VyTmFtZSI6ImV1Z2VuZSIsInBhc3N3b3JkIjoiZXVnZW5lMSIsInVzZXJSb2xlcyI6bnVsbH0sImV4cCI6MTU4NjU3NTQ4Mn0.c2qbGwHnwsmVxIl6GzVoA02hGdtQMsKaJi6W_6v8hl4x20zIwTnU5Rwp_5ctKLaQJuSro9_EbAMBRSQINypjdA
  ```

* 批量添加角色api

  ```
  url: localhost:8890/role/batch
  请求方法: post
  请求体数据: 
  [
  	{
  		"roleName": "其他人员7",
  		"rolePermissions": [
  			{
  				"permissionId": 1
  			},
  			{
  				"permissionId": 4
  			}
  		]
  	},
  	
  	{
  		"roleName": "其他人员8",
  		"rolePermissions": [
  			{
  				"permissionId": 2
  			},
  			{
  				"permissionId": 3
  			}
  		]
  	}
  ]
  ```

* 批量添加权限api

  ```
  url: localhost:8890/permission/batch
  请求方法: post
  请求体:
  [{
  	"permissionName": "测试新增权限api1",
  	"permissionDirection": "BACKEND",
  	"parentPermissionId": null,
  	"permissionType": "API",
  	"permissionKey": "NOT#KEY1"
  },
  {
  	"permissionName": "测试新增权限api2",
  	"permissionDirection": "BACKEND",
  	"parentPermissionId": null,
  	"permissionType": "API",
  	"permissionKey": "NOT#KEY2"
  }]
  ```

* 删除用户角色api

  ```
  url: http://localhost:8890/user-role/2
  请求方法: delete
  ```

  




