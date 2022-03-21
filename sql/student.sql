/*
 Navicat Premium Data Transfer

 Source Server         : MySql
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : student_system

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 21/03/2022 21:29:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `sid` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  `name` varchar(10) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  `sex` enum('男','女') CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  `birthday` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT 'CURRENT_TIMESTAMP',
  `province` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  `hobby` varchar(100) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
