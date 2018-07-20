/*
 Navicat MySQL Data Transfer

 Source Server         : FindPeers
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : project02

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 20/07/2018 19:15:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `session_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `open_session` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ct_time` datetime(0) NULL DEFAULT NULL,
  `up_time` datetime(0) NULL DEFAULT NULL,
  `prepare` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '111', '111', '111', NULL, NULL, NULL);
INSERT INTO `user` VALUES (2, '222', '222', '222', NULL, NULL, NULL);
INSERT INTO `user` VALUES (3, '333', '333', '333', NULL, NULL, NULL);
INSERT INTO `user` VALUES (4, '444', '444', '444', NULL, NULL, NULL);
INSERT INTO `user` VALUES (5, '555', '555', '555', NULL, NULL, NULL);
INSERT INTO `user` VALUES (6, '666', '666', '666', NULL, NULL, NULL);
INSERT INTO `user` VALUES (7, '777', '777', '777', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for user_card
-- ----------------------------
DROP TABLE IF EXISTS `user_card`;
CREATE TABLE `user_card`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户标识',
  `app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小程序ID',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `user_wechat` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户微信号',
  `user_company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户公司',
  `user_industry` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户行业',
  `user_city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '城市',
  `user_job` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户职务',
  `user_img` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像地址',
  `user_phone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户电话',
  `user_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `synopsis` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `demand` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '需求',
  `resources` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源',
  `give_like_num` int(11) NULL DEFAULT NULL COMMENT '点赞次数',
  `del_flag` tinyint(4) NULL DEFAULT 1 COMMENT '删除标识（1-可用，2-不可用，默认可用）',
  `ct_time` datetime(0) NULL DEFAULT NULL,
  `up_time` datetime(0) NULL DEFAULT NULL,
  `prepare` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_card
-- ----------------------------
INSERT INTO `user_card` VALUES (1, '111', '111', '111111', '111111', '111111', '111111', '111111', '111111', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL);
INSERT INTO `user_card` VALUES (2, '222', '222', '222222', '222222', '222222', '222222', '222222', '222222', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL);
INSERT INTO `user_card` VALUES (3, '333', '333', '333333', '333333', '333333', '333333', '333333', '333333', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL);
INSERT INTO `user_card` VALUES (4, '444', '444', '444444', '444444', '444444', '444444', '444444', '444444', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL);
INSERT INTO `user_card` VALUES (5, '555', '555', '555555', '555555', '555555', '555555', '555555', '555555', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL);
INSERT INTO `user_card` VALUES (6, '666', '666', '666666', '666666', '666666', '666666', '666666', '666666', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL);
INSERT INTO `user_card` VALUES (7, '777', 'wx34fefa15f676d944', '777', '987', '777777', '777777', '777777', '777777', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, '2018-07-20 18:35:36', '2018-07-20 19:15:06', NULL);

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`  (
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群组ID',
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户标识',
  `app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小程序ID',
  `del_flag` tinyint(4) NULL DEFAULT 1 COMMENT '删除标识（1-未删除，2-已删除，默认未删除）',
  `ct_time` datetime(0) NULL DEFAULT NULL,
  `up_time` datetime(0) NULL DEFAULT NULL,
  `prepare` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_group
-- ----------------------------
INSERT INTO `user_group` VALUES ('1', '111', '111', 1, NULL, NULL, NULL);
INSERT INTO `user_group` VALUES ('1', '222', '111', 1, NULL, NULL, NULL);
INSERT INTO `user_group` VALUES ('1', '333', '111', 1, NULL, NULL, NULL);
INSERT INTO `user_group` VALUES ('1', '444', '111', 1, NULL, NULL, NULL);
INSERT INTO `user_group` VALUES ('1', '555', '111', 1, NULL, NULL, NULL);
INSERT INTO `user_group` VALUES ('1', '666', '111', 1, NULL, NULL, NULL);
INSERT INTO `user_group` VALUES ('1', '777', 'wx34fefa15f676d944', 1, '2018-07-20 19:01:37', NULL, NULL);

-- ----------------------------
-- Table structure for user_peer
-- ----------------------------
DROP TABLE IF EXISTS `user_peer`;
CREATE TABLE `user_peer`  (
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户标识',
  `card_id` int(11) NULL DEFAULT NULL COMMENT '用户关联名片',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '群ID(个人分享时，为0)',
  `share_flag` tinyint(4) NULL DEFAULT NULL COMMENT '分享标志(1-个人分享，2-群分享)',
  `save_flag` tinyint(4) NULL DEFAULT 1 COMMENT '保存状态(1-未保存，2-保存，默认未保存)',
  `del_flag` tinyint(4) NULL DEFAULT 1 COMMENT '删除标识(1-未删除，2-已删除，默认未删除)',
  `ct_time` datetime(0) NULL DEFAULT NULL,
  `up_time` datetime(0) NULL DEFAULT NULL,
  `prepare` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_peer
-- ----------------------------
INSERT INTO `user_peer` VALUES ('111', 2, '1', 2, 1, 1, NULL, NULL, NULL);
INSERT INTO `user_peer` VALUES ('222', 1, '1', 2, 1, 1, NULL, NULL, NULL);
INSERT INTO `user_peer` VALUES ('333', 1, '1', 2, 1, 1, NULL, NULL, NULL);
INSERT INTO `user_peer` VALUES ('444', 1, '1', 2, 1, 1, NULL, NULL, NULL);
INSERT INTO `user_peer` VALUES ('555', 1, '1', 2, 1, 1, NULL, NULL, NULL);
INSERT INTO `user_peer` VALUES ('666', 1, '1', 2, 1, 1, NULL, NULL, NULL);
INSERT INTO `user_peer` VALUES ('333', 2, '1', 2, 1, 1, NULL, NULL, NULL);
INSERT INTO `user_peer` VALUES ('444', 2, '1', 2, 1, 1, NULL, NULL, NULL);
INSERT INTO `user_peer` VALUES ('555', 2, '1', 2, 1, 1, NULL, NULL, NULL);
INSERT INTO `user_peer` VALUES ('666', 2, '1', 2, 1, 1, NULL, NULL, NULL);
INSERT INTO `user_peer` VALUES ('777', 7, '0', 1, 1, 1, '2018-07-20 18:56:53', '2018-07-20 18:59:31', NULL);
INSERT INTO `user_peer` VALUES ('777', 6, '0', 1, 1, 1, '2018-07-20 18:57:21', '2018-07-20 18:59:45', NULL);
INSERT INTO `user_peer` VALUES ('111', 7, '1', 2, 1, 1, '2018-07-20 19:01:37', NULL, NULL);
INSERT INTO `user_peer` VALUES ('222', 7, '1', 2, 1, 1, '2018-07-20 19:01:37', NULL, NULL);
INSERT INTO `user_peer` VALUES ('333', 7, '1', 2, 1, 1, '2018-07-20 19:01:37', NULL, NULL);
INSERT INTO `user_peer` VALUES ('444', 7, '1', 2, 1, 1, '2018-07-20 19:01:37', NULL, NULL);
INSERT INTO `user_peer` VALUES ('555', 7, '1', 2, 1, 1, '2018-07-20 19:01:37', NULL, NULL);
INSERT INTO `user_peer` VALUES ('666', 7, '1', 2, 1, 1, '2018-07-20 19:01:37', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
