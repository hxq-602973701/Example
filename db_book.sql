/*
Navicat MySQL Data Transfer

Source Server         : mine
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : db_book

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2019-03-18 18:49:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activemq_acks
-- ----------------------------
DROP TABLE IF EXISTS `activemq_acks`;
CREATE TABLE `activemq_acks` (
  `CONTAINER` varchar(250) NOT NULL,
  `SUB_DEST` varchar(250) DEFAULT NULL,
  `CLIENT_ID` varchar(250) NOT NULL,
  `SUB_NAME` varchar(250) NOT NULL,
  `SELECTOR` varchar(250) DEFAULT NULL,
  `LAST_ACKED_ID` bigint(20) DEFAULT NULL,
  `PRIORITY` bigint(20) NOT NULL DEFAULT '5',
  `XID` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`CONTAINER`,`CLIENT_ID`,`SUB_NAME`,`PRIORITY`),
  KEY `ACTIVEMQ_ACKS_XIDX` (`XID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of activemq_acks
-- ----------------------------

-- ----------------------------
-- Table structure for activemq_lock
-- ----------------------------
DROP TABLE IF EXISTS `activemq_lock`;
CREATE TABLE `activemq_lock` (
  `ID` bigint(20) NOT NULL,
  `TIME` bigint(20) DEFAULT NULL,
  `BROKER_NAME` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of activemq_lock
-- ----------------------------
INSERT INTO `activemq_lock` VALUES ('1', null, null);

-- ----------------------------
-- Table structure for activemq_msgs
-- ----------------------------
DROP TABLE IF EXISTS `activemq_msgs`;
CREATE TABLE `activemq_msgs` (
  `ID` bigint(20) NOT NULL,
  `CONTAINER` varchar(250) NOT NULL,
  `MSGID_PROD` varchar(250) DEFAULT NULL,
  `MSGID_SEQ` bigint(20) DEFAULT NULL,
  `EXPIRATION` bigint(20) DEFAULT NULL,
  `MSG` longblob,
  `PRIORITY` bigint(20) DEFAULT NULL,
  `XID` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ACTIVEMQ_MSGS_MIDX` (`MSGID_PROD`,`MSGID_SEQ`),
  KEY `ACTIVEMQ_MSGS_CIDX` (`CONTAINER`),
  KEY `ACTIVEMQ_MSGS_EIDX` (`EXPIRATION`),
  KEY `ACTIVEMQ_MSGS_PIDX` (`PRIORITY`),
  KEY `ACTIVEMQ_MSGS_XIDX` (`XID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of activemq_msgs
-- ----------------------------
INSERT INTO `activemq_msgs` VALUES ('26', 'topic://topic', 'ID:PC-60217-1534912753454-1:1:1:1', '1', '0', 0x000000E21C0000000501017B01001D49443A50432D36303231372D313533343931323735333435342D313A31000000000000000100000000000000010165010005746F7069630000016E00017B01001D49443A50432D36303231372D313533343931323735333435342D313A310000000000000001000000000000000100000000000000010000000000000161000000000000000100000000000000000400000001655FEE41760001000000130000000FE4B880E4B8AAE4B99FE6B2A1E69C89000000000000000000000000000000000000000000000001655FEE4177000000000000000000, '0', null);
INSERT INTO `activemq_msgs` VALUES ('27', 'topic://topic', 'ID:PC-60217-1534912753454-1:1:2:1', '1', '0', 0x000000E21C0000000A01017B01001D49443A50432D36303231372D313533343931323735333435342D313A31000000000000000100000000000000020165010005746F7069630000016E00017B01001D49443A50432D36303231372D313533343931323735333435342D313A310000000000000001000000000000000200000000000000010000000000000164000000000000000100000000000000000400000001655FEE7CAC0001000000130000000FE4B880E4B8AAE4B99FE6B2A1E69C89000000000000000000000000000000000000000000000001655FEE7CAC000000000000000000, '0', null);
INSERT INTO `activemq_msgs` VALUES ('28', 'topic://topic', 'ID:PC-50115-1534984585051-1:5:2:1', '1', '0', 0x000000E21C0000000901017B01001D49443A50432D35303131352D313533343938343538353035312D313A35000000000000000100000000000000020165010005746F7069630000016E00017B01001D49443A50432D35303131352D313533343938343538353035312D313A35000000000000000100000000000000020000000000000001000000000000016B00000000000000010000000000000000040000000165643819730001000000130000000FE4B880E4B8AAE4B99FE6B2A1E69C890000000000000000000000000000000000000000000000016564381975000000000000000000, '0', null);
INSERT INTO `activemq_msgs` VALUES ('29', 'topic://topic', 'ID:PC-50541-1534984814188-1:1:1:1', '1', '0', 0x000000EE1C0000000501017B01001D49443A50432D35303534312D313533343938343831343138382D313A31000000000000000100000000000000010165010005746F7069630000016E00017B01001D49443A50432D35303534312D313533343938343831343138382D313A310000000000000001000000000000000100000000000000010000000000000172000000000000000100000000000000000400000001656439AA7800010000001F0000001BE4B880E4B8AAE4B99FE6B2A1E69C89E5958AE5958AE5958AE5958A000000000000000000000000000000000000000000000001656439AA78000000000000000000, '0', null);
INSERT INTO `activemq_msgs` VALUES ('104', 'queue://ActiveMQ.DLQ', 'ID:PC-61398-1537446311913-1:1:4:1', '1', '0', 0x0000011D1C0000001101017B01001D49443A50432D36313339382D313533373434363331313931332D313A3100000000000000010000000000000004016401000C4163746976654D512E444C510001640100057175657565016E00017B01001D49443A50432D36313339382D313533373434363331313931332D313A31000000000000000100000000000000040000000000000001000000000000032F00000000000000010000000000000000040000000165F6F124D700010000001900000015E68891E698AFE9989FE58897E6B688E681AF30303201000000210000000100126F726967696E616C45787069726174696F6E060000000000000000000000000000000000000000000000000000000000000165F6F1290100000165F6F1251400, '0', null);
INSERT INTO `activemq_msgs` VALUES ('113', 'queue://ActiveMQ.DLQ', 'ID:PC-61578-1537446605801-1:1:4:1', '1', '0', 0x0000011D1C0000000E01017B01001D49443A50432D36313537382D313533373434363630353830312D313A3100000000000000010000000000000004016401000C4163746976654D512E444C510001640100057175657565016E00017B01001D49443A50432D36313537382D313533373434363630353830312D313A31000000000000000100000000000000040000000000000001000000000000035900000000000000010000000000000000040000000165F6F5FD3B00010000001900000015E68891E698AFE9989FE58897E6B688E681AF30303201000000210000000100126F726967696E616C45787069726174696F6E060000000000000000000000000000000000000000000000000000000000000165F6F601BE00000165F6F5FDD300, '0', null);
INSERT INTO `activemq_msgs` VALUES ('122', 'queue://ActiveMQ.DLQ', 'ID:PC-61857-1537446840488-1:1:4:1', '1', '0', 0x0000011D1C0000000E01017B01001D49443A50432D36313835372D313533373434363834303438382D313A3100000000000000010000000000000004016401000C4163746976654D512E444C510001640100057175657565016E00017B01001D49443A50432D36313835372D313533373434363834303438382D313A31000000000000000100000000000000040000000000000001000000000000038500000000000000010000000000000000040000000165F6F9120E00010000001900000015E68891E698AFE9989FE58897E6B688E681AF30303201000000210000000100126F726967696E616C45787069726174696F6E060000000000000000000000000000000000000000000000000000000000000165F6F91A0000000165F6F9122C00, '0', null);
INSERT INTO `activemq_msgs` VALUES ('125', 'queue://queue', 'ID:PC-61895-1537446883103-1:1:5:1', '1', '0', 0x000000E81C0000001301017B01001D49443A50432D36313839352D313533373434363838333130332D313A3100000000000000010000000000000005016401000571756575650000016E00017B01001D49443A50432D36313839352D313533373434363838333130332D313A31000000000000000100000000000000050000000000000001000000000000039600000000000000010000000000000000040000000165F6F9C62900010000001900000015E68891E698AFE9989FE58897E6B688E681AF30303300000000000000000000000000000000000000000000000165F6F9C62A000000000000000000, '0', null);
INSERT INTO `activemq_msgs` VALUES ('126', 'queue://queue', 'ID:PC-61895-1537446883103-1:1:6:1', '1', '0', 0x000000E81C0000001801017B01001D49443A50432D36313839352D313533373434363838333130332D313A3100000000000000010000000000000006016401000571756575650000016E00017B01001D49443A50432D36313839352D313533373434363838333130332D313A31000000000000000100000000000000060000000000000001000000000000039900000000000000010000000000000000040000000165F6F9C65800010000001900000015E68891E698AFE9989FE58897E6B688E681AF30303400000000000000000000000000000000000000000000000165F6F9C658000000000000000000, '0', null);
INSERT INTO `activemq_msgs` VALUES ('127', 'queue://ActiveMQ.DLQ', 'ID:PC-61895-1537446883103-1:1:3:1', '1', '0', 0x0000011D1C0000000901017B01001D49443A50432D36313839352D313533373434363838333130332D313A3100000000000000010000000000000003016401000C4163746976654D512E444C510001640100057175657565016E00017B01001D49443A50432D36313839352D313533373434363838333130332D313A31000000000000000100000000000000030000000000000001000000000000039B00000000000000010000000000000000040000000165F6F9C5CB00010000001900000015E68891E698AFE9989FE58897E6B688E681AF30303101000000210000000100126F726967696E616C45787069726174696F6E060000000000000000000000000000000000000000000000000000000000000165F6F9CDFB00000165F6F9C5F300, '0', null);
INSERT INTO `activemq_msgs` VALUES ('128', 'queue://ActiveMQ.DLQ', 'ID:PC-61895-1537446883103-1:1:4:1', '1', '0', 0x0000011D1C0000000E01017B01001D49443A50432D36313839352D313533373434363838333130332D313A3100000000000000010000000000000004016401000C4163746976654D512E444C510001640100057175657565016E00017B01001D49443A50432D36313839352D313533373434363838333130332D313A31000000000000000100000000000000040000000000000001000000000000039D00000000000000010000000000000000040000000165F6F9C5F800010000001900000015E68891E698AFE9989FE58897E6B688E681AF30303201000000210000000100126F726967696E616C45787069726174696F6E060000000000000000000000000000000000000000000000000000000000000165F6F9CE2F00000165F6F9C62600, '0', null);

-- ----------------------------
-- Table structure for o_open_third_in
-- ----------------------------
DROP TABLE IF EXISTS `o_open_third_in`;
CREATE TABLE `o_open_third_in` (
  `open_id` int(20) NOT NULL,
  `app_key` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `app_secrety` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `app_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `app_memo` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `dept_code` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `dept_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标志',
  `create_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '修改人',
  `modified_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of o_open_third_in
-- ----------------------------
INSERT INTO `o_open_third_in` VALUES ('1', '38729696CDB6071CE0530A769F1B999C', 'E5905A8E0530A769F1B05A837F702AFB', '测试', '测试', '330118000000', '测试', '0', '1', '2018-06-14 16:58:59', '1', '2018-06-14 17:01:23');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` int(11) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `url` varchar(200) DEFAULT '#' COMMENT '请求地址',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1058 DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', '0', '1', '#', 'M', '0', '', 'fa fa-gear', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统管理目录');
INSERT INTO `sys_menu` VALUES ('2', '系统监控', '0', '2', '#', 'M', '0', '', 'fa fa-video-camera', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统监控目录');
INSERT INTO `sys_menu` VALUES ('3', '系统工具', '0', '3', '#', 'M', '0', '', 'fa fa-bars', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统工具目录');
INSERT INTO `sys_menu` VALUES ('100', '用户管理', '1', '1', '/system/user', 'C', '0', 'system:user:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '用户管理菜单');
INSERT INTO `sys_menu` VALUES ('101', '角色管理', '1', '2', '/system/role', 'C', '0', 'system:role:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '角色管理菜单');
INSERT INTO `sys_menu` VALUES ('102', '菜单管理', '1', '3', '/system/menu', 'C', '0', 'system:menu:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '菜单管理菜单');
INSERT INTO `sys_menu` VALUES ('103', '部门管理', '1', '4', '/system/dept', 'C', '0', 'system:dept:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '部门管理菜单');
INSERT INTO `sys_menu` VALUES ('104', '岗位管理', '1', '5', '/system/post', 'C', '0', 'system:post:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '岗位管理菜单');
INSERT INTO `sys_menu` VALUES ('105', '字典管理', '1', '6', '/system/dict', 'C', '0', 'system:dict:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '字典管理菜单');
INSERT INTO `sys_menu` VALUES ('106', '参数设置', '1', '7', '/system/config', 'C', '0', 'system:config:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '参数设置菜单');
INSERT INTO `sys_menu` VALUES ('107', '通知公告', '1', '8', '/system/notice', 'C', '0', 'system:notice:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知公告菜单');
INSERT INTO `sys_menu` VALUES ('108', '日志管理', '1', '9', '#', 'M', '0', '', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '日志管理菜单');
INSERT INTO `sys_menu` VALUES ('109', '在线用户', '2', '1', '/monitor/online', 'C', '0', 'monitor:online:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '在线用户菜单');
INSERT INTO `sys_menu` VALUES ('110', '定时任务', '2', '2', '/monitor/job', 'C', '0', 'monitor:job:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '定时任务菜单');
INSERT INTO `sys_menu` VALUES ('111', '数据监控', '2', '3', '/monitor/data', 'C', '0', 'monitor:data:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '数据监控菜单');
INSERT INTO `sys_menu` VALUES ('112', '服务监控', '2', '3', '/monitor/server', 'C', '0', 'monitor:server:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '服务监控菜单');
INSERT INTO `sys_menu` VALUES ('113', '表单构建', '3', '1', '/tool/build', 'C', '0', 'tool:build:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '表单构建菜单');
INSERT INTO `sys_menu` VALUES ('114', '代码生成', '3', '2', '/tool/gen', 'C', '0', 'tool:gen:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '代码生成菜单');
INSERT INTO `sys_menu` VALUES ('115', '系统接口', '3', '3', '/tool/swagger', 'C', '0', 'tool:swagger:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统接口菜单');
INSERT INTO `sys_menu` VALUES ('500', '操作日志', '108', '1', '/monitor/operlog', 'C', '0', 'monitor:operlog:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '操作日志菜单');
INSERT INTO `sys_menu` VALUES ('501', '登录日志', '108', '2', '/monitor/logininfor', 'C', '0', 'monitor:logininfor:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '登录日志菜单');
INSERT INTO `sys_menu` VALUES ('1000', '用户查询', '100', '1', '#', 'F', '0', 'system:user:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1001', '用户新增', '100', '2', '#', 'F', '0', 'system:user:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1002', '用户修改', '100', '3', '#', 'F', '0', 'system:user:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1003', '用户删除', '100', '4', '#', 'F', '0', 'system:user:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1004', '用户导出', '100', '5', '#', 'F', '0', 'system:user:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1005', '用户导入', '100', '6', '#', 'F', '0', 'system:user:import', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1006', '重置密码', '100', '7', '#', 'F', '0', 'system:user:resetPwd', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1007', '角色查询', '101', '1', '#', 'F', '0', 'system:role:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1008', '角色新增', '101', '2', '#', 'F', '0', 'system:role:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1009', '角色修改', '101', '3', '#', 'F', '0', 'system:role:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1010', '角色删除', '101', '4', '#', 'F', '0', 'system:role:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1011', '角色导出', '101', '5', '#', 'F', '0', 'system:role:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1012', '菜单查询', '102', '1', '#', 'F', '0', 'system:menu:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1013', '菜单新增', '102', '2', '#', 'F', '0', 'system:menu:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1014', '菜单修改', '102', '3', '#', 'F', '0', 'system:menu:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1015', '菜单删除', '102', '4', '#', 'F', '0', 'system:menu:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1016', '部门查询', '103', '1', '#', 'F', '0', 'system:dept:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1017', '部门新增', '103', '2', '#', 'F', '0', 'system:dept:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1018', '部门修改', '103', '3', '#', 'F', '0', 'system:dept:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1019', '部门删除', '103', '4', '#', 'F', '0', 'system:dept:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1020', '岗位查询', '104', '1', '#', 'F', '0', 'system:post:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1021', '岗位新增', '104', '2', '#', 'F', '0', 'system:post:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1022', '岗位修改', '104', '3', '#', 'F', '0', 'system:post:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1023', '岗位删除', '104', '4', '#', 'F', '0', 'system:post:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1024', '岗位导出', '104', '5', '#', 'F', '0', 'system:post:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1025', '字典查询', '105', '1', '#', 'F', '0', 'system:dict:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1026', '字典新增', '105', '2', '#', 'F', '0', 'system:dict:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1027', '字典修改', '105', '3', '#', 'F', '0', 'system:dict:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1028', '字典删除', '105', '4', '#', 'F', '0', 'system:dict:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1029', '字典导出', '105', '5', '#', 'F', '0', 'system:dict:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1030', '参数查询', '106', '1', '#', 'F', '0', 'system:config:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1031', '参数新增', '106', '2', '#', 'F', '0', 'system:config:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1032', '参数修改', '106', '3', '#', 'F', '0', 'system:config:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1033', '参数删除', '106', '4', '#', 'F', '0', 'system:config:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1034', '参数导出', '106', '5', '#', 'F', '0', 'system:config:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1035', '公告查询', '107', '1', '#', 'F', '0', 'system:notice:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1036', '公告新增', '107', '2', '#', 'F', '0', 'system:notice:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1037', '公告修改', '107', '3', '#', 'F', '0', 'system:notice:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1038', '公告删除', '107', '4', '#', 'F', '0', 'system:notice:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1039', '操作查询', '500', '1', '#', 'F', '0', 'monitor:operlog:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1040', '操作删除', '500', '2', '#', 'F', '0', 'monitor:operlog:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1041', '详细信息', '500', '3', '#', 'F', '0', 'monitor:operlog:detail', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1042', '日志导出', '500', '4', '#', 'F', '0', 'monitor:operlog:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1043', '登录查询', '501', '1', '#', 'F', '0', 'monitor:logininfor:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1044', '登录删除', '501', '2', '#', 'F', '0', 'monitor:logininfor:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1045', '日志导出', '501', '3', '#', 'F', '0', 'monitor:logininfor:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1046', '在线查询', '109', '1', '#', 'F', '0', 'monitor:online:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1047', '批量强退', '109', '2', '#', 'F', '0', 'monitor:online:batchForceLogout', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1048', '单条强退', '109', '3', '#', 'F', '0', 'monitor:online:forceLogout', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1049', '任务查询', '110', '1', '#', 'F', '0', 'monitor:job:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1050', '任务新增', '110', '2', '#', 'F', '0', 'monitor:job:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1051', '任务修改', '110', '3', '#', 'F', '0', 'monitor:job:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1052', '任务删除', '110', '4', '#', 'F', '0', 'monitor:job:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1053', '状态修改', '110', '5', '#', 'F', '0', 'monitor:job:changeStatus', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1054', '任务详细', '110', '6', '#', 'F', '0', 'monitor:job:detail', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1055', '任务导出', '110', '7', '#', 'F', '0', 'monitor:job:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1056', '生成查询', '114', '1', '#', 'F', '0', 'tool:gen:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES ('1057', '生成代码', '114', '2', '#', 'F', '0', 'tool:gen:code', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限）',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', 'admin', '1', '1', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2019-01-24 17:08:17', '管理员');
INSERT INTO `sys_role` VALUES ('2', '普通角色', 'common', '2', '2', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2019-02-22 18:38:31', '普通角色');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1');
INSERT INTO `sys_role_menu` VALUES ('1', '2');
INSERT INTO `sys_role_menu` VALUES ('1', '3');
INSERT INTO `sys_role_menu` VALUES ('1', '100');
INSERT INTO `sys_role_menu` VALUES ('1', '101');
INSERT INTO `sys_role_menu` VALUES ('1', '102');
INSERT INTO `sys_role_menu` VALUES ('1', '103');
INSERT INTO `sys_role_menu` VALUES ('1', '104');
INSERT INTO `sys_role_menu` VALUES ('1', '105');
INSERT INTO `sys_role_menu` VALUES ('1', '106');
INSERT INTO `sys_role_menu` VALUES ('1', '107');
INSERT INTO `sys_role_menu` VALUES ('1', '108');
INSERT INTO `sys_role_menu` VALUES ('1', '109');
INSERT INTO `sys_role_menu` VALUES ('1', '110');
INSERT INTO `sys_role_menu` VALUES ('1', '111');
INSERT INTO `sys_role_menu` VALUES ('1', '112');
INSERT INTO `sys_role_menu` VALUES ('1', '113');
INSERT INTO `sys_role_menu` VALUES ('1', '114');
INSERT INTO `sys_role_menu` VALUES ('1', '115');
INSERT INTO `sys_role_menu` VALUES ('1', '500');
INSERT INTO `sys_role_menu` VALUES ('1', '501');
INSERT INTO `sys_role_menu` VALUES ('1', '1000');
INSERT INTO `sys_role_menu` VALUES ('1', '1001');
INSERT INTO `sys_role_menu` VALUES ('1', '1002');
INSERT INTO `sys_role_menu` VALUES ('1', '1003');
INSERT INTO `sys_role_menu` VALUES ('1', '1004');
INSERT INTO `sys_role_menu` VALUES ('1', '1005');
INSERT INTO `sys_role_menu` VALUES ('1', '1006');
INSERT INTO `sys_role_menu` VALUES ('1', '1007');
INSERT INTO `sys_role_menu` VALUES ('1', '1008');
INSERT INTO `sys_role_menu` VALUES ('1', '1009');
INSERT INTO `sys_role_menu` VALUES ('1', '1010');
INSERT INTO `sys_role_menu` VALUES ('1', '1011');
INSERT INTO `sys_role_menu` VALUES ('1', '1012');
INSERT INTO `sys_role_menu` VALUES ('1', '1013');
INSERT INTO `sys_role_menu` VALUES ('1', '1014');
INSERT INTO `sys_role_menu` VALUES ('1', '1015');
INSERT INTO `sys_role_menu` VALUES ('1', '1016');
INSERT INTO `sys_role_menu` VALUES ('1', '1017');
INSERT INTO `sys_role_menu` VALUES ('1', '1018');
INSERT INTO `sys_role_menu` VALUES ('1', '1019');
INSERT INTO `sys_role_menu` VALUES ('1', '1020');
INSERT INTO `sys_role_menu` VALUES ('1', '1021');
INSERT INTO `sys_role_menu` VALUES ('1', '1022');
INSERT INTO `sys_role_menu` VALUES ('1', '1023');
INSERT INTO `sys_role_menu` VALUES ('1', '1024');
INSERT INTO `sys_role_menu` VALUES ('1', '1025');
INSERT INTO `sys_role_menu` VALUES ('1', '1026');
INSERT INTO `sys_role_menu` VALUES ('1', '1027');
INSERT INTO `sys_role_menu` VALUES ('1', '1028');
INSERT INTO `sys_role_menu` VALUES ('1', '1029');
INSERT INTO `sys_role_menu` VALUES ('1', '1030');
INSERT INTO `sys_role_menu` VALUES ('1', '1031');
INSERT INTO `sys_role_menu` VALUES ('1', '1032');
INSERT INTO `sys_role_menu` VALUES ('1', '1033');
INSERT INTO `sys_role_menu` VALUES ('1', '1034');
INSERT INTO `sys_role_menu` VALUES ('1', '1035');
INSERT INTO `sys_role_menu` VALUES ('1', '1036');
INSERT INTO `sys_role_menu` VALUES ('1', '1037');
INSERT INTO `sys_role_menu` VALUES ('1', '1038');
INSERT INTO `sys_role_menu` VALUES ('1', '1039');
INSERT INTO `sys_role_menu` VALUES ('1', '1040');
INSERT INTO `sys_role_menu` VALUES ('1', '1041');
INSERT INTO `sys_role_menu` VALUES ('1', '1042');
INSERT INTO `sys_role_menu` VALUES ('1', '1043');
INSERT INTO `sys_role_menu` VALUES ('1', '1044');
INSERT INTO `sys_role_menu` VALUES ('1', '1045');
INSERT INTO `sys_role_menu` VALUES ('1', '1046');
INSERT INTO `sys_role_menu` VALUES ('1', '1047');
INSERT INTO `sys_role_menu` VALUES ('1', '1048');
INSERT INTO `sys_role_menu` VALUES ('1', '1049');
INSERT INTO `sys_role_menu` VALUES ('1', '1050');
INSERT INTO `sys_role_menu` VALUES ('1', '1051');
INSERT INTO `sys_role_menu` VALUES ('1', '1052');
INSERT INTO `sys_role_menu` VALUES ('1', '1053');
INSERT INTO `sys_role_menu` VALUES ('1', '1054');
INSERT INTO `sys_role_menu` VALUES ('1', '1055');
INSERT INTO `sys_role_menu` VALUES ('1', '1056');
INSERT INTO `sys_role_menu` VALUES ('1', '1057');
INSERT INTO `sys_role_menu` VALUES ('2', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '2');
INSERT INTO `sys_role_menu` VALUES ('2', '3');
INSERT INTO `sys_role_menu` VALUES ('2', '100');
INSERT INTO `sys_role_menu` VALUES ('2', '101');
INSERT INTO `sys_role_menu` VALUES ('2', '102');
INSERT INTO `sys_role_menu` VALUES ('2', '103');
INSERT INTO `sys_role_menu` VALUES ('2', '105');
INSERT INTO `sys_role_menu` VALUES ('2', '106');
INSERT INTO `sys_role_menu` VALUES ('2', '107');
INSERT INTO `sys_role_menu` VALUES ('2', '108');
INSERT INTO `sys_role_menu` VALUES ('2', '109');
INSERT INTO `sys_role_menu` VALUES ('2', '110');
INSERT INTO `sys_role_menu` VALUES ('2', '111');
INSERT INTO `sys_role_menu` VALUES ('2', '112');
INSERT INTO `sys_role_menu` VALUES ('2', '113');
INSERT INTO `sys_role_menu` VALUES ('2', '114');
INSERT INTO `sys_role_menu` VALUES ('2', '115');
INSERT INTO `sys_role_menu` VALUES ('2', '500');
INSERT INTO `sys_role_menu` VALUES ('2', '501');
INSERT INTO `sys_role_menu` VALUES ('2', '1000');
INSERT INTO `sys_role_menu` VALUES ('2', '1001');
INSERT INTO `sys_role_menu` VALUES ('2', '1002');
INSERT INTO `sys_role_menu` VALUES ('2', '1003');
INSERT INTO `sys_role_menu` VALUES ('2', '1004');
INSERT INTO `sys_role_menu` VALUES ('2', '1005');
INSERT INTO `sys_role_menu` VALUES ('2', '1006');
INSERT INTO `sys_role_menu` VALUES ('2', '1007');
INSERT INTO `sys_role_menu` VALUES ('2', '1008');
INSERT INTO `sys_role_menu` VALUES ('2', '1009');
INSERT INTO `sys_role_menu` VALUES ('2', '1010');
INSERT INTO `sys_role_menu` VALUES ('2', '1011');
INSERT INTO `sys_role_menu` VALUES ('2', '1012');
INSERT INTO `sys_role_menu` VALUES ('2', '1013');
INSERT INTO `sys_role_menu` VALUES ('2', '1014');
INSERT INTO `sys_role_menu` VALUES ('2', '1015');
INSERT INTO `sys_role_menu` VALUES ('2', '1016');
INSERT INTO `sys_role_menu` VALUES ('2', '1017');
INSERT INTO `sys_role_menu` VALUES ('2', '1018');
INSERT INTO `sys_role_menu` VALUES ('2', '1019');
INSERT INTO `sys_role_menu` VALUES ('2', '1025');
INSERT INTO `sys_role_menu` VALUES ('2', '1026');
INSERT INTO `sys_role_menu` VALUES ('2', '1027');
INSERT INTO `sys_role_menu` VALUES ('2', '1028');
INSERT INTO `sys_role_menu` VALUES ('2', '1029');
INSERT INTO `sys_role_menu` VALUES ('2', '1030');
INSERT INTO `sys_role_menu` VALUES ('2', '1031');
INSERT INTO `sys_role_menu` VALUES ('2', '1032');
INSERT INTO `sys_role_menu` VALUES ('2', '1033');
INSERT INTO `sys_role_menu` VALUES ('2', '1034');
INSERT INTO `sys_role_menu` VALUES ('2', '1035');
INSERT INTO `sys_role_menu` VALUES ('2', '1036');
INSERT INTO `sys_role_menu` VALUES ('2', '1037');
INSERT INTO `sys_role_menu` VALUES ('2', '1038');
INSERT INTO `sys_role_menu` VALUES ('2', '1039');
INSERT INTO `sys_role_menu` VALUES ('2', '1040');
INSERT INTO `sys_role_menu` VALUES ('2', '1041');
INSERT INTO `sys_role_menu` VALUES ('2', '1042');
INSERT INTO `sys_role_menu` VALUES ('2', '1043');
INSERT INTO `sys_role_menu` VALUES ('2', '1044');
INSERT INTO `sys_role_menu` VALUES ('2', '1045');
INSERT INTO `sys_role_menu` VALUES ('2', '1046');
INSERT INTO `sys_role_menu` VALUES ('2', '1047');
INSERT INTO `sys_role_menu` VALUES ('2', '1048');
INSERT INTO `sys_role_menu` VALUES ('2', '1049');
INSERT INTO `sys_role_menu` VALUES ('2', '1050');
INSERT INTO `sys_role_menu` VALUES ('2', '1051');
INSERT INTO `sys_role_menu` VALUES ('2', '1052');
INSERT INTO `sys_role_menu` VALUES ('2', '1053');
INSERT INTO `sys_role_menu` VALUES ('2', '1054');
INSERT INTO `sys_role_menu` VALUES ('2', '1055');
INSERT INTO `sys_role_menu` VALUES ('2', '1056');
INSERT INTO `sys_role_menu` VALUES ('2', '1057');

-- ----------------------------
-- Table structure for sys_sequence
-- ----------------------------
DROP TABLE IF EXISTS `sys_sequence`;
CREATE TABLE `sys_sequence` (
  `seq_name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '表名',
  `current_value` bigint(20) NOT NULL DEFAULT '1' COMMENT '当前值',
  `increment` int(11) NOT NULL DEFAULT '1' COMMENT '增量',
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='序列表';

-- ----------------------------
-- Records of sys_sequence
-- ----------------------------
INSERT INTO `sys_sequence` VALUES ('Book', '4', '1');
INSERT INTO `sys_sequence` VALUES ('Dept', '5', '1');
INSERT INTO `sys_sequence` VALUES ('Menu', '9', '1');
INSERT INTO `sys_sequence` VALUES ('Property', '1', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门ID',
  `login_name` varchar(30) NOT NULL COMMENT '登录账号',
  `user_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像路径',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `salt` varchar(20) DEFAULT '' COMMENT '盐加密',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(50) DEFAULT '' COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '103', 'admin', '若依', '00', 'ry@163.com', '15888888888', '1', '', '29c67a30398638269fe600f73a054934', '111111', '0', '0', '127.0.0.1', '2019-02-26 18:03:13', 'admin', '2018-03-16 11:33:00', 'ry', '2019-02-26 18:03:12', '管理员');
INSERT INTO `sys_user` VALUES ('2', '105', 'ry', '若依', '00', 'ry@qq.com', '15666666666', '1', '', '29c67a30398638269fe600f73a054934', '111111', '0', '2', '127.0.0.1', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '测试员');
INSERT INTO `sys_user` VALUES ('100', '103', 'hyf', 'hyf', '00', '602943701@qq.com', '15669947917', '0', '', '29c67a30398638269fe600f73a054934', '111111', '0', '0', '', null, 'admin', '2019-01-03 10:16:53', 'admin', '2019-02-22 18:38:54', '');
INSERT INTO `sys_user` VALUES ('101', '103', 'hyf1', 'hyf1', '00', '6029437012@qq.com', '15669947916', '0', '', '055332b8ef208619716bade12d5da6bc', 'b0346c', '0', '0', '127.0.0.1', '2019-02-22 18:40:38', 'admin', '2019-02-22 18:40:25', '', '2019-02-22 18:40:37', '');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2');
INSERT INTO `sys_user_role` VALUES ('100', '2');
INSERT INTO `sys_user_role` VALUES ('101', '2');

-- ----------------------------
-- Table structure for s_config
-- ----------------------------
DROP TABLE IF EXISTS `s_config`;
CREATE TABLE `s_config` (
  `config_id` int(11) NOT NULL,
  `dept_ids` varchar(255) DEFAULT NULL,
  `config_key` varchar(255) DEFAULT NULL,
  `config_value` varchar(255) DEFAULT NULL,
  `config_memo` varchar(255) DEFAULT NULL,
  `ranking` int(20) DEFAULT NULL,
  `config_module` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标志',
  `create_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '修改人',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_config
-- ----------------------------
INSERT INTO `s_config` VALUES ('1', '1', '1', '134324', '4523432423', null, 'houd', '0', '1', '2018-06-11 19:43:29', '1', '2018-06-11 19:43:43');

-- ----------------------------
-- Table structure for s_dept
-- ----------------------------
DROP TABLE IF EXISTS `s_dept`;
CREATE TABLE `s_dept` (
  `dept_id` bigint(20) NOT NULL,
  `dept_parent_id` bigint(20) DEFAULT NULL,
  `dept_code` varchar(255) DEFAULT NULL,
  `dept_name` varchar(255) DEFAULT NULL,
  `dept_short_name` varchar(255) DEFAULT NULL,
  `dept_type` bigint(20) DEFAULT NULL,
  `dept_area` text,
  `ranking` int(20) DEFAULT NULL,
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标志',
  `create_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '修改人',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_dept
-- ----------------------------
INSERT INTO `s_dept` VALUES ('1', '0', '110', '总经理办公室', '总经理办公室', '32', null, null, '0', '1', '2018-06-05 18:21:12', '1', '2018-06-05 18:21:12');
INSERT INTO `s_dept` VALUES ('2', '1', '112', '前台', '前台', '128', '', null, '0', '1', '2018-06-05 18:21:25', '1', '2018-06-05 18:21:25');
INSERT INTO `s_dept` VALUES ('3', '1', '113', '研发', '研发', '128', null, null, '0', '1', '2018-06-05 18:21:33', '1', '2018-06-05 18:21:33');
INSERT INTO `s_dept` VALUES ('4', '2', '114', '市场', '市场', '128', null, null, '0', '1', '2018-06-05 18:29:09', '1', '2018-06-05 18:29:09');
INSERT INTO `s_dept` VALUES ('5', '1', '1212', '后勤', '后勤', '8192', '', null, '1', '1', '2018-06-05 18:53:15', '1', '2018-06-05 19:30:32');

-- ----------------------------
-- Table structure for s_menu
-- ----------------------------
DROP TABLE IF EXISTS `s_menu`;
CREATE TABLE `s_menu` (
  `menu_id` bigint(20) NOT NULL,
  `menu_parent_id` bigint(20) DEFAULT NULL,
  `page_id` varchar(255) DEFAULT NULL,
  `menu_name` varchar(255) DEFAULT NULL,
  `menu_pinyin` varchar(255) DEFAULT NULL,
  `menu_title` varchar(255) DEFAULT NULL,
  `menu_icon` varchar(255) DEFAULT NULL,
  `menu_image` varchar(255) DEFAULT NULL,
  `menu_url` varchar(255) DEFAULT NULL,
  `menu_roles` bigint(20) DEFAULT NULL,
  `menu_memo` varchar(255) DEFAULT NULL,
  `ranking` varchar(255) DEFAULT NULL,
  `menu_show` tinyint(2) DEFAULT NULL,
  `smart_link` int(255) DEFAULT NULL,
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标志',
  `create_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '修改人',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_menu
-- ----------------------------
INSERT INTO `s_menu` VALUES ('1', '0', 'XT_INDEX_01', '系统入口', null, null, null, null, '/', '8191', null, '0', '1', null, '0', '1', '2018-06-01 11:38:58', '1', '2018-06-01 11:38:58');
INSERT INTO `s_menu` VALUES ('2', '1', 'XT_WH_01', '系统维护', null, null, null, null, '/system/menu-list.html', '8191', null, '0', '1', null, '0', '1', '2018-06-01 11:39:05', '1', '2018-06-01 11:39:05');
INSERT INTO `s_menu` VALUES ('3', '2', 'XT_CD_01', '菜单管理', null, null, 'fa fa-hand-rock-o', '', '/system/menu-list.html', '1', '这是可以管理系统菜单的地方1212232共和国刚多发点', '0', '1', '0', '0', '1', '2018-06-01 11:42:10', '1', '2018-06-01 11:42:10');
INSERT INTO `s_menu` VALUES ('4', '2', 'XT_YH_01', '用户管理', null, null, null, null, '/system/user-list.html', '8191', null, '1', '1', null, '0', '1', '2018-06-01 11:43:15', '1', '2018-06-01 11:43:15');
INSERT INTO `s_menu` VALUES ('5', '2', 'XT_DW_01', '单位管理', null, null, null, null, '/system/dept-list', '8191', null, '2', '1', null, '0', '1', '2018-06-01 11:43:42', '1', '2018-06-01 11:43:42');
INSERT INTO `s_menu` VALUES ('6', '2', 'XT_JS_01', '角色管理', null, null, null, null, '/system/role-list', '8191', null, '3', '1', null, '0', '1', '2018-06-01 11:44:34', '1', '2018-06-01 11:44:34');
INSERT INTO `s_menu` VALUES ('7', '2', 'YW_CS_01', '业务参数管理', null, null, null, null, '/property/property-index', '8191', null, '4', '1', null, '0', '1', '2018-06-01 11:45:13', '1', '2018-06-01 11:45:13');
INSERT INTO `s_menu` VALUES ('8', '2', 'XT_CS_01', '系统参数管理', null, null, null, null, '/system/config-list.html', '8191', null, '5', '1', null, '0', '1', '2018-06-01 11:47:55', '1', '2018-06-01 11:47:55');
INSERT INTO `s_menu` VALUES ('9', '2', 'XT_TZ_01', '系统通知消息', null, null, null, null, '/hello', '1', null, '6', '0', null, '0', '1', '2018-06-14 09:23:40', '1', '2018-06-14 09:23:40');
INSERT INTO `s_menu` VALUES ('10', '2', 'XT_RW_01', '系统通知消息', null, null, null, null, '/task/task-list', '1', null, '7', '1', null, '0', '1', '2018-08-02 17:22:33', '1', '2018-08-02 17:22:33');

-- ----------------------------
-- Table structure for s_property
-- ----------------------------
DROP TABLE IF EXISTS `s_property`;
CREATE TABLE `s_property` (
  `prop_id` int(11) NOT NULL,
  `prop_parent_id` int(11) DEFAULT NULL,
  `prop_type` int(20) DEFAULT NULL,
  `prop_key` varchar(255) DEFAULT NULL,
  `prop_value` varchar(255) DEFAULT NULL,
  `prop_icon` varchar(255) DEFAULT NULL,
  `prop_memo` varchar(255) DEFAULT NULL,
  `ranking` int(20) DEFAULT NULL,
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标志',
  `create_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '修改人',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`prop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_property
-- ----------------------------
INSERT INTO `s_property` VALUES ('1', '3703', '1', '12121', '121', '', '', null, '0', '1', '2018-06-11 20:06:50', '1', '2018-06-11 20:06:50');
INSERT INTO `s_property` VALUES ('3700', '0', '1', '3700', '单位类型', '324324', '', '1', '1', '1', '2018-06-05 18:50:10', '1', '2018-06-11 20:06:34');
INSERT INTO `s_property` VALUES ('3701', '3700', '1', '128', '区分局', null, null, null, '0', '1', '2018-06-05 18:50:31', '1', '2018-06-05 18:50:31');
INSERT INTO `s_property` VALUES ('3702', '3700', '1', '256', '县分局', null, null, null, '0', '1', '2018-06-05 18:50:50', '1', '2018-06-05 18:50:50');
INSERT INTO `s_property` VALUES ('3703', '3700', '1', '8192', '派出所', null, null, null, '0', '1', '2018-06-05 18:51:33', '1', '2018-06-05 18:51:33');
INSERT INTO `s_property` VALUES ('3704', '0', '1', '3704', '来源', null, null, null, '0', '1', '2018-06-14 11:13:20', '1', '2018-06-14 11:13:20');
INSERT INTO `s_property` VALUES ('3705', '3704', '1', '3705', '一般', null, null, null, '0', '1', '2018-06-14 11:13:47', '1', '2018-06-14 11:13:47');
INSERT INTO `s_property` VALUES ('3706', '3704', '1', '3706', '资源', null, null, null, '0', '1', '2018-06-14 11:14:03', '1', '2018-06-14 11:14:03');
INSERT INTO `s_property` VALUES ('3707', '3705', '1', '3707', '其它', null, null, null, '0', '1', '2018-06-14 11:14:21', '1', '2018-06-14 11:14:21');
INSERT INTO `s_property` VALUES ('3708', '3705', '1', '3708', '本人', null, null, null, '0', '1', '2018-06-14 11:14:54', '1', '2018-06-14 11:14:54');
INSERT INTO `s_property` VALUES ('3709', '3706', '1', '3709', '保安', null, null, null, '0', '1', '2018-06-14 11:16:05', '1', '2018-06-14 11:16:05');
INSERT INTO `s_property` VALUES ('3710', '3706', '1', '3710', '后勤', null, null, null, '0', '1', '2018-06-14 11:16:14', '1', '2018-06-14 11:16:14');

-- ----------------------------
-- Table structure for s_role
-- ----------------------------
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role` (
  `role_id` int(11) NOT NULL,
  `branch_id` bigint(20) DEFAULT NULL,
  `auth_type` bigint(20) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `role_memo` varchar(255) DEFAULT NULL,
  `ranking` int(20) DEFAULT NULL,
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标志',
  `create_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '修改人',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_role
-- ----------------------------
INSERT INTO `s_role` VALUES ('1', '1', '1', '超级管理员', '有很多权限', '1', '0', '1', '2018-06-04 18:40:18', '1', '2018-06-11 19:12:34');

-- ----------------------------
-- Table structure for s_sms
-- ----------------------------
DROP TABLE IF EXISTS `s_sms`;
CREATE TABLE `s_sms` (
  `sms_id` bigint(50) NOT NULL,
  `phone` varchar(20) NOT NULL COMMENT '电话',
  `content` varchar(500) NOT NULL COMMENT '信息内容',
  `sms_type` bigint(4) NOT NULL DEFAULT '0' COMMENT '0 短信 1彩信',
  `police_num` varchar(255) DEFAULT NULL COMMENT '发送人警号',
  `send_name` varchar(255) DEFAULT NULL COMMENT '发送人姓名',
  `receive_name` varchar(255) DEFAULT NULL COMMENT '接收人姓名',
  `send_status` tinyint(4) DEFAULT '1' COMMENT '短信状态（2成功、-1失败、1发送中、0草稿）',
  `sync_flag` tinyint(4) DEFAULT '0' COMMENT '0未同步，1同步中 -1 同步失败 2 同步成功',
  `group_code` varchar(255) DEFAULT NULL COMMENT '标识同一批次数据',
  `send_time` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '创建人',
  `modified_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '修改人',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标志',
  PRIMARY KEY (`sms_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of s_sms
-- ----------------------------

-- ----------------------------
-- Table structure for s_token
-- ----------------------------
DROP TABLE IF EXISTS `s_token`;
CREATE TABLE `s_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `device_type` varchar(255) DEFAULT NULL,
  `push_token` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_token
-- ----------------------------
INSERT INTO `s_token` VALUES ('1', 'e2538ef1f6722452d52821d5d59c5471', '1', null, '2018-06-01 11:30:53', '2018-06-01 15:12:45');
INSERT INTO `s_token` VALUES ('1', '1f89a12b2e7b2fe2880a2f4e6e45789c', '0', null, '2018-07-24 16:12:47', '2018-08-02 17:56:25');

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `user_id` bigint(20) NOT NULL,
  `account` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `id_card_num` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `auth_type` bigint(20) DEFAULT NULL,
  `dept_id` bigint(20) DEFAULT NULL,
  `login_way` int(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `device_type` int(20) DEFAULT NULL,
  `push_token` varchar(255) DEFAULT NULL,
  `device_name` varchar(255) DEFAULT NULL,
  `mac` varchar(255) DEFAULT NULL,
  `reg_ip` varchar(255) DEFAULT NULL,
  `last_ip` varchar(255) DEFAULT NULL,
  `login_sum` bigint(20) DEFAULT '0',
  `branch_id` bigint(20) DEFAULT NULL,
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标志',
  `create_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '修改人',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_user
-- ----------------------------
INSERT INTO `s_user` VALUES ('1', 'hyf', '胡云飞', '15669947917', '152631', null, null, null, '1', '4', '1', null, 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, null, '127.0.0.1', '60', '2', '0', '1', '2018-06-01 10:43:48', '1', '2018-06-13 17:29:27');

-- ----------------------------
-- Table structure for t
-- ----------------------------
DROP TABLE IF EXISTS `t`;
CREATE TABLE `t` (
  `xm` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `km` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `cj` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t
-- ----------------------------
INSERT INTO `t` VALUES ('小明', '语文', '60');
INSERT INTO `t` VALUES ('小明', '数学', '90');
INSERT INTO `t` VALUES ('小明', '英语', '50');
INSERT INTO `t` VALUES ('小红', '语文', '90');
INSERT INTO `t` VALUES ('小红', '数学', '90');
INSERT INTO `t` VALUES ('小红', '英语', '90');

-- ----------------------------
-- Table structure for task_schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `task_schedule_job`;
CREATE TABLE `task_schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `job_name` varchar(255) DEFAULT NULL,
  `job_group` varchar(255) DEFAULT NULL,
  `job_status` varchar(255) DEFAULT NULL,
  `cron_expression` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `bean_class` varchar(255) DEFAULT NULL,
  `is_concurrent` varchar(255) DEFAULT NULL COMMENT '1',
  `spring_id` varchar(255) DEFAULT NULL,
  `method_name` varchar(255) NOT NULL,
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '创建人',
  `modified_uid` bigint(20) NOT NULL DEFAULT '1' COMMENT '修改人',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标志',
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `name_group` (`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_schedule_job
-- ----------------------------
INSERT INTO `task_schedule_job` VALUES ('3', '2018-08-02 16:49:09', '2018-08-02 17:59:40', 'task', 'group', '0', '0/1 * * * * ?', '描述', 'com.java1234.web.controller.task.TaskTest', '1', '', 'run', '2018-08-02 16:49:11', '1', '1', '0');

-- ----------------------------
-- Table structure for t_book
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book` (
  `book_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(20) DEFAULT NULL,
  `book_author` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES ('1', '121', '34343', '2018-06-14 09:17:47');
INSERT INTO `t_book` VALUES ('3', '123232', '不好了', null);
INSERT INTO `t_book` VALUES ('4', '5465', '657', '2018-06-14 09:17:47');
INSERT INTO `t_book` VALUES ('5', '12121撒打算', '好的', null);
INSERT INTO `t_book` VALUES ('6', '34232324', '32551232321', null);
INSERT INTO `t_book` VALUES ('7', '455432432432', '56534521212', null);
INSERT INTO `t_book` VALUES ('8', '水浒传11', '4588', null);
INSERT INTO `t_book` VALUES ('9', '2321421343', '4324', null);
INSERT INTO `t_book` VALUES ('10', '你是', '4324', null);
INSERT INTO `t_book` VALUES ('11', '121', '121', null);
INSERT INTO `t_book` VALUES ('12', '22', '3443', null);

-- ----------------------------
-- Function structure for sequence_nextval
-- ----------------------------
DROP FUNCTION IF EXISTS `sequence_nextval`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `sequence_nextval`(table_name VARCHAR(64)) RETURNS bigint(20)
    DETERMINISTIC
    COMMENT '获取序列'
BEGIN
    SET @VALUE = 1;
    UPDATE
      sys_sequence
    SET
      current_value = @VALUE := current_value + increment
    WHERE
      seq_name = table_name;
    IF @VALUE = 1
    THEN
      INSERT INTO sys_sequence (seq_name) VALUES (table_name);
    END IF;
    RETURN @VALUE;
  END
;;
DELIMITER ;
