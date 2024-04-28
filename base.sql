-- ----------------------------
-- 角色表
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles`;
CREATE TABLE `sys_roles` (
  `id` BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `available` BOOLEAN DEFAULT FALSE COMMENT '使用状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间'
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '角色表';

INSERT INTO sys_roles (id, name, available, create_time, update_time) VALUES (1, '系统管理员', 1, '2019-06-18 21:13:31', '2019-06-18 21:13:31');
-- ----------------------------
-- 后台管理员
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
  `role_id` BIGINT(20) NOT NULL COMMENT '用户角色',
  `real_name` varchar(50) NOT NULL COMMENT '用户名',
  `user_name` varchar(20) NOT NULL COMMENT '登录名',
  `password` varchar(128) NOT NULL COMMENT '用户名',
  `telephone` varchar(11) NOT NULL COMMENT '手机号',
  `locked` BOOLEAN DEFAULT FALSE COMMENT '是否启用 0 否 1 是',
  `salt` VARCHAR(100) NOT NULL COMMENT '盐',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  index idx_role_id(`role_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '后台管理员';

INSERT INTO sys_user (id, user_name, user_role, shop_code, shop_name, real_name, password, telephone, salt, role_id, locked) VALUES (1, 'admin', 1, 'admin', 'admin', 'admin', 'a90faad70f1a0dac2664cfd2f8d701cd', '66666666666','7a087888dc3f90ef5da8dafe9508efb7', '1', 0);

-- ----------------------------
-- 权限表
-- ----------------------------
DROP TABLE IF EXISTS `sys_permissions`;
CREATE TABLE `sys_permissions`(
  `id` BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(100) DEFAULT '' COMMENT '资源名',
  `type` VARCHAR(50) DEFAULT '' COMMENT '资源类型:菜单/按钮',
  `permission` varchar(50) NOT NULL COMMENT '权限标识',
  `url` varchar(100) NOT NULL COMMENT '地址',
  `parent_id` BIGINT(20) NULL DEFAULT 0 COMMENT '父级ID',
  `sort` INT(3) NOT NULL DEFAULT 0 COMMENT '排序',
  `available` BOOLEAN DEFAULT FALSE,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  INDEX index_parent_id(`parent_id`),
  INDEX index_sort(`sort`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '权限表';


INSERT INTO sys_permissions (id, name, type, permission, url, parent_id, available, create_time, sort) VALUES (1, '权限菜单', 'menu', 'permission:*', '', 0, 1, '2019-06-18 21:52:55', 0);
INSERT INTO sys_permissions (id, name, type, permission, url, parent_id, available, create_time, sort) VALUES (2, '角色管理', 'menu', 'role:*', 'role/index', 1, 1, '2019-06-18 21:52:56', 999);
INSERT INTO sys_permissions (id, name, type, permission, url, parent_id, available, create_time, sort) VALUES (3, '权限编辑', 'button', 'role:permission', '', 2, 1, '2019-06-18 21:52:56', 0);
-- ----------------------------
-- 角色权限对照
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_permissions`;
CREATE TABLE `sys_roles_permissions` (
  `permissions_id` BIGINT(20) NOT NULL COMMENT '权限ID',
  `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
  index index_role_id(`role_id`),
  index index_permissions_id(`permissions_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '角色权限对照';

INSERT INTO sys_roles_permissions (permissions_id, role_id) VALUES (1, 1);
INSERT INTO sys_roles_permissions (permissions_id, role_id) VALUES (2, 1);
