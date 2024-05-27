/*
 Navicat Premium Data Transfer

 Source Server         : sqlEx
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : sky_take_out

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 27/05/2024 16:08:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address_book
-- ----------------------------
DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `consignee` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '收货人',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `province_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区级名称',
  `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '默认 0 否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '地址簿' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address_book
-- ----------------------------
INSERT INTO `address_book` VALUES (16, 8, ' ', ' ', ' ', NULL, ' ', NULL, '到店堂食', NULL, ' ', ' ', NULL, 1);
INSERT INTO `address_book` VALUES (17, 8, ' ', ' ', ' ', NULL, ' ', NULL, '到店自取', NULL, ' ', ' ', NULL, 0);
INSERT INTO `address_book` VALUES (18, 8, '张三', '0', '18875471523', '50', '重庆市', '5001', '市辖区', '500101', '万州区', '百安坝街道天星路666号重庆三峡学院', '3', 0);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   1 菜品分类 2 套餐分类',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '顺序',
  `status` int(11) NULL DEFAULT NULL COMMENT '分类状态 0:禁用，1:启用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_category_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品及套餐分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (11, 1, '酒水饮料', 10, 1, '2022-06-09 22:09:18', '2022-06-09 22:09:18', 1, 1);
INSERT INTO `category` VALUES (12, 1, '传统主食', 9, 1, '2022-06-09 22:09:32', '2022-06-09 22:18:53', 1, 1);
INSERT INTO `category` VALUES (13, 2, '人气套餐', 12, 1, '2022-06-09 22:11:38', '2022-06-10 11:04:40', 1, 1);
INSERT INTO `category` VALUES (15, 2, '商务套餐', 13, 1, '2022-06-09 22:14:10', '2022-06-10 11:04:48', 1, 1);
INSERT INTO `category` VALUES (16, 1, '特色鱼类', 1, 1, '2022-06-09 22:15:37', '2024-05-27 14:08:47', 1, 1);
INSERT INTO `category` VALUES (19, 1, '新鲜时蔬', 7, 1, '2022-06-09 22:18:12', '2024-05-06 10:32:52', 1, 6);
INSERT INTO `category` VALUES (21, 1, '汤类', 11, 1, '2022-06-10 10:51:47', '2024-05-06 15:29:33', 1, 1);
INSERT INTO `category` VALUES (23, 1, '特色面食', 15, 1, '2024-05-06 10:17:38', '2024-05-06 10:17:38', 1, 1);
INSERT INTO `category` VALUES (24, 2, '基础套餐', 14, 1, '2024-05-06 10:18:07', '2024-05-06 10:18:07', 1, 1);

-- ----------------------------
-- Table structure for dish
-- ----------------------------
DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品名称',
  `category_id` bigint(20) NOT NULL COMMENT '菜品分类id',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '菜品价格',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
  `status` int(11) NULL DEFAULT 1 COMMENT '0 停售 1 起售',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_dish_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 93 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dish
-- ----------------------------
INSERT INTO `dish` VALUES (70, '酸菜鱼', 16, 33.00, 'http://127.0.0.1:8080/images/3da2e0ec-c1cb-495b-aa71-297956c0a387豆腐汤~1.jpg', '酸咸，鱼肉，豆腐，酸菜', 1, '2024-05-27 14:29:04', '2024-05-27 14:29:04', 1, 1);
INSERT INTO `dish` VALUES (71, '蒜烧鱼', 16, 33.00, 'http://127.0.0.1:8080/images/8a701a0d-deb1-458d-acf7-ac1c50870ed3豆腐汤~1.jpg', '原味，鱼肉', 1, '2024-05-27 14:30:49', '2024-05-27 14:30:49', 1, 1);
INSERT INTO `dish` VALUES (72, '水煮鱼', 16, 30.00, 'http://127.0.0.1:8080/images/a3235c8b-475b-4158-9806-238555f7974b豆腐汤~1.jpg', '', 1, '2024-05-27 14:31:15', '2024-05-27 14:31:15', 1, 1);
INSERT INTO `dish` VALUES (73, '三鲜汤', 21, 18.00, 'http://127.0.0.1:8080/images/fff79176-ff44-475b-8d77-2db2d0a77e9f豆腐汤~1.jpg', '咸鲜，豆腐，虾', 1, '2024-05-27 14:32:18', '2024-05-27 14:32:18', 1, 1);
INSERT INTO `dish` VALUES (74, '酥肉汤', 21, 22.00, 'http://127.0.0.1:8080/images/51517662-9d64-4ab4-9343-0eb1da0f800f豆腐汤~1.jpg', '咸鲜，酥肉', 1, '2024-05-27 14:36:02', '2024-05-27 14:36:02', 1, 1);
INSERT INTO `dish` VALUES (75, '紫菜蛋花汤', 21, 16.00, 'http://127.0.0.1:8080/images/c5399e88-d3d6-482f-96bd-af9be0eef8bd豆腐汤~1.jpg', ' 咸鲜，紫菜，鸡蛋', 1, '2024-05-27 14:36:33', '2024-05-27 14:36:33', 1, 1);
INSERT INTO `dish` VALUES (76, '西红柿鸡蛋汤', 21, 16.00, 'http://127.0.0.1:8080/images/1da8faef-19a4-4a6e-b27f-976cf731954c豆腐汤~1.jpg', '咸鲜，西红柿，鸡蛋', 1, '2024-05-27 14:37:02', '2024-05-27 14:37:02', 1, 1);
INSERT INTO `dish` VALUES (77, '手撕包菜', 19, 15.00, 'http://127.0.0.1:8080/images/7534d3ea-d13a-4182-be0c-4f0fb61255a7豆腐汤~1.jpg', '原味，包菜', 1, '2024-05-27 14:38:16', '2024-05-27 14:38:16', 1, 1);
INSERT INTO `dish` VALUES (78, '青椒土豆丝', 19, 15.00, 'http://127.0.0.1:8080/images/83827b49-ef61-453e-b8dd-bbc267d01207豆腐汤~1.jpg', '土豆丝，青椒', 1, '2024-05-27 14:38:45', '2024-05-27 14:38:45', 1, 1);
INSERT INTO `dish` VALUES (79, '青椒炒蛋', 19, 15.00, 'http://127.0.0.1:8080/images/3e2a77b3-7720-4b18-ab9a-a7eda0c06d4c豆腐汤~1.jpg', '青椒，鸡蛋', 1, '2024-05-27 14:39:12', '2024-05-27 14:39:12', 1, 1);
INSERT INTO `dish` VALUES (80, '红烧茄子', 19, 15.00, 'http://127.0.0.1:8080/images/fb925d06-898f-45df-84bc-549a77093d80豆腐汤~1.jpg', '酸甜，茄子', 1, '2024-05-27 14:39:34', '2024-05-27 14:39:34', 1, 1);
INSERT INTO `dish` VALUES (81, '扬州炒饭', 12, 15.00, 'http://127.0.0.1:8080/images/084e8970-cb8c-4335-a6b0-bdd722eba380豆腐汤~1.jpg', '米，鸡蛋', 1, '2024-05-27 14:41:24', '2024-05-27 14:41:24', 1, 1);
INSERT INTO `dish` VALUES (82, '火腿炒饭', 12, 12.00, 'http://127.0.0.1:8080/images/a514b4cf-04d3-492f-95a8-4df81b7e24da豆腐汤~1.jpg', '米，火腿肠', 1, '2024-05-27 14:41:53', '2024-05-27 14:41:53', 1, 1);
INSERT INTO `dish` VALUES (83, '土豆肉丝炒饭', 12, 15.00, 'http://127.0.0.1:8080/images/37787262-e9dc-4939-8633-09eae90edcde豆腐汤~1.jpg', '米，土豆丝', 1, '2024-05-27 14:42:50', '2024-05-27 14:42:50', 1, 1);
INSERT INTO `dish` VALUES (84, '鸡蛋肥牛炒饭', 12, 15.00, 'http://127.0.0.1:8080/images/046505bd-8124-4d3a-a3e1-56570b86746f豆腐汤~1.jpg', '米，鸡蛋，肥牛', 1, '2024-05-27 14:43:20', '2024-05-27 14:43:20', 1, 1);
INSERT INTO `dish` VALUES (85, '清汤小面', 23, 9.00, 'http://127.0.0.1:8080/images/d7a189b2-487f-40bb-b6b1-5c7304070ff2豆腐汤~1.jpg', '', 1, '2024-05-27 14:44:11', '2024-05-27 14:44:11', 1, 1);
INSERT INTO `dish` VALUES (86, '红烧牛肉面', 23, 12.00, 'http://127.0.0.1:8080/images/c015e3b3-2d62-48ce-ae65-447ff758faea豆腐汤~1.jpg', '牛肉', 1, '2024-05-27 14:44:35', '2024-05-27 14:44:35', 1, 1);
INSERT INTO `dish` VALUES (87, '酸菜肉丝面', 23, 12.00, 'http://127.0.0.1:8080/images/20daeea6-3a1f-4180-a23f-49b1c8f9109c豆腐汤~1.jpg', '酸菜，不辣', 1, '2024-05-27 14:45:02', '2024-05-27 14:45:02', 1, 1);
INSERT INTO `dish` VALUES (88, '红烧肥肠面', 23, 12.00, 'http://127.0.0.1:8080/images/a3e9d2ed-b740-414c-aed2-2e85b0eac4ea豆腐汤~1.jpg', '12', 1, '2024-05-27 14:46:27', '2024-05-27 14:46:27', 1, 1);
INSERT INTO `dish` VALUES (89, '红牛', 11, 5.50, 'http://127.0.0.1:8080/images/364baef5-8e65-408d-9652-30fbe31a5bce豆腐汤~1.jpg', '500ml', 1, '2024-05-27 15:00:34', '2024-05-27 15:00:34', 1, 1);
INSERT INTO `dish` VALUES (90, '可乐', 11, 3.50, 'http://127.0.0.1:8080/images/dde95f29-2649-4ac7-a352-2a2dfdb2bc3f豆腐汤~1.jpg', '500ml', 1, '2024-05-27 15:01:05', '2024-05-27 15:01:05', 1, 1);
INSERT INTO `dish` VALUES (91, '绿茶', 11, 5.00, 'http://127.0.0.1:8080/images/a75874f3-78e8-4c60-b787-f7e120553b7d豆腐汤~1.jpg', '1000ml', 1, '2024-05-27 15:01:46', '2024-05-27 15:01:46', 1, 1);
INSERT INTO `dish` VALUES (92, '雪花啤酒', 11, 5.00, 'http://127.0.0.1:8080/images/690881d7-fcec-48e6-8137-ab8984a86504豆腐汤~1.jpg', '500ml', 1, '2024-05-27 15:02:26', '2024-05-27 15:02:26', 1, 1);

-- ----------------------------
-- Table structure for dish_flavor
-- ----------------------------
DROP TABLE IF EXISTS `dish_flavor`;
CREATE TABLE `dish_flavor`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dish_id` bigint(20) NOT NULL COMMENT '菜品',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味名称',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味数据list',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品口味关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dish_flavor
-- ----------------------------
INSERT INTO `dish_flavor` VALUES (106, 72, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]');
INSERT INTO `dish_flavor` VALUES (107, 81, '', '[]');
INSERT INTO `dish_flavor` VALUES (108, 82, '', '[]');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '身份证号',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:启用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '员工信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2022-02-15 15:51:20', '2024-05-27 11:22:10', 10, 1);
INSERT INTO `employee` VALUES (5, '李四', 'lisi', 'e10adc3949ba59abbe56e057f20f883e', '18854213265', '1', '500121200011235232', 0, '2024-05-03 19:25:59', '2024-05-27 14:00:23', 1, 1);
INSERT INTO `employee` VALUES (6, 'jesse', 'jesse', 'e10adc3949ba59abbe56e057f20f883e', '18888888888', '1', '500230199605140581', 1, '2024-05-03 19:38:35', '2024-05-04 20:05:53', 1, 1);
INSERT INTO `employee` VALUES (7, 'Walt', 'heisemburg', 'e10adc3949ba59abbe56e057f20f883e', '15232451232', '1', '422015200112086554', 1, '2024-05-04 14:20:23', '2024-05-04 14:20:23', 1, 1);
INSERT INTO `employee` VALUES (8, '汉克', 'hank', 'e10adc3949ba59abbe56e057f20f883e', '15555555555', '1', '500200200012011151', 1, '2024-05-04 14:22:32', '2024-05-04 20:07:33', 5, 1);
INSERT INTO `employee` VALUES (9, '索尔', 'saul', 'e10adc3949ba59abbe56e057f20f883e', '15121412526', '1', '200124199606146351', 1, '2024-05-06 15:24:02', '2024-05-06 15:24:57', 1, 1);

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '名字',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `dish_id` bigint(20) NULL DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint(20) NULL DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味',
  `number` int(11) NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 92 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (1, '王老吉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', 3, 46, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (2, '馒头', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/475cc599-8661-4899-8f9e-121dd8ef7d02.png', 4, 50, NULL, NULL, 1, 1.00);
INSERT INTO `order_detail` VALUES (3, '米饭', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/76752350-2121-44d2-b477-10791c23a8ec.png', 4, 49, NULL, NULL, 1, 2.00);
INSERT INTO `order_detail` VALUES (4, '王老吉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', 5, 46, NULL, NULL, 2, 6.00);
INSERT INTO `order_detail` VALUES (5, '草鱼2斤', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/b544d3ba-a1ae-4d20-a860-81cb5dec9e03.png', 5, 65, NULL, '重辣', 1, 68.00);
INSERT INTO `order_detail` VALUES (6, '清蒸鲈鱼', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c18b5c67-3b71-466c-a75a-e63c6449f21c.png', 5, 58, NULL, NULL, 1, 98.00);
INSERT INTO `order_detail` VALUES (7, '豆腐汤', 'http://127.0.0.1:8080/images/d7e7ef07-a3d4-46af-a62e-089cb61b653b豆腐汤~1.jpg', 5, 69, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (8, '王老吉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', 6, 46, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (9, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 7, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (10, '王老吉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', 8, 46, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (11, '王老吉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', 9, 46, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (12, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 10, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (13, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 11, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (14, '北冰洋', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', 12, 47, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (15, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 13, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (16, '豪华烤鱼', 'http://127.0.0.1:8080/images/d7d7325e-81df-4c48-8a98-f79cc860cc4e单链表递归遍历.PNG', 14, NULL, 20, NULL, 1, 122.00);
INSERT INTO `order_detail` VALUES (17, '清炒小油菜', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/3613d38e-5614-41c2-90ed-ff175bf50716.png', 15, 54, NULL, '不要香菜', 1, 18.00);
INSERT INTO `order_detail` VALUES (18, '老坛酸菜鱼', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4a9cefba-6a74-467e-9fde-6e687ea725d7.png', 15, 51, NULL, '不要葱,微辣', 1, 56.00);
INSERT INTO `order_detail` VALUES (19, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 16, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (20, '梅菜扣肉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/6080b118-e30a-4577-aab4-45042e3f88be.png', 17, 60, NULL, '不要葱', 1, 58.00);
INSERT INTO `order_detail` VALUES (21, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 18, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (22, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 19, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (23, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 20, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (24, '王老吉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', 21, 46, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (25, '北冰洋', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', 21, 47, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (26, '香锅牛蛙', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/f5ac8455-4793-450c-97ba-173795c34626.png', 22, 63, NULL, NULL, 1, 88.00);
INSERT INTO `order_detail` VALUES (27, '蜀味水煮草鱼', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a6953d5a-4c18-4b30-9319-4926ee77261f.png', 23, 53, NULL, '不要葱,中辣', 1, 38.00);
INSERT INTO `order_detail` VALUES (28, '江团鱼2斤', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a101a1e9-8f8b-47b2-afa4-1abd47ea0a87.png', 25, 66, NULL, '重辣', 1, 119.00);
INSERT INTO `order_detail` VALUES (29, '王老吉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', 25, 46, NULL, NULL, 2, 6.00);
INSERT INTO `order_detail` VALUES (30, '东坡肘子', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a80a4b8c-c93e-4f43-ac8a-856b0d5cc451.png', 28, 59, NULL, NULL, 1, 138.00);
INSERT INTO `order_detail` VALUES (31, '北冰洋', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', 28, 47, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (32, '东坡肘子', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a80a4b8c-c93e-4f43-ac8a-856b0d5cc451.png', 30, 59, NULL, NULL, 1, 138.00);
INSERT INTO `order_detail` VALUES (33, '北冰洋', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', 30, 47, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (34, '梅菜扣肉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/6080b118-e30a-4577-aab4-45042e3f88be.png', 30, 60, NULL, '不要香菜', 1, 58.00);
INSERT INTO `order_detail` VALUES (35, '东坡肘子', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a80a4b8c-c93e-4f43-ac8a-856b0d5cc451.png', 32, 59, NULL, NULL, 1, 138.00);
INSERT INTO `order_detail` VALUES (36, '北冰洋', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', 32, 47, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (37, '梅菜扣肉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/6080b118-e30a-4577-aab4-45042e3f88be.png', 32, 60, NULL, '不要香菜', 1, 58.00);
INSERT INTO `order_detail` VALUES (38, '北冰洋', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', 33, 47, NULL, NULL, 4, 4.00);
INSERT INTO `order_detail` VALUES (39, '江团鱼2斤', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a101a1e9-8f8b-47b2-afa4-1abd47ea0a87.png', 34, 66, NULL, '重辣', 1, 119.00);
INSERT INTO `order_detail` VALUES (40, '东坡肘子', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a80a4b8c-c93e-4f43-ac8a-856b0d5cc451.png', 34, 59, NULL, NULL, 1, 138.00);
INSERT INTO `order_detail` VALUES (41, '剁椒鱼头', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/13da832f-ef2c-484d-8370-5934a1045a06.png', 34, 61, NULL, NULL, 1, 66.00);
INSERT INTO `order_detail` VALUES (42, '豆腐汤', 'http://127.0.0.1:8080/images/d7e7ef07-a3d4-46af-a62e-089cb61b653b豆腐汤~1.jpg', 34, 69, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (43, '江团鱼2斤', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a101a1e9-8f8b-47b2-afa4-1abd47ea0a87.png', 36, 66, NULL, '重辣', 1, 119.00);
INSERT INTO `order_detail` VALUES (44, '东坡肘子', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a80a4b8c-c93e-4f43-ac8a-856b0d5cc451.png', 36, 59, NULL, NULL, 1, 138.00);
INSERT INTO `order_detail` VALUES (45, '剁椒鱼头', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/13da832f-ef2c-484d-8370-5934a1045a06.png', 36, 61, NULL, NULL, 1, 66.00);
INSERT INTO `order_detail` VALUES (46, '豆腐汤', 'http://127.0.0.1:8080/images/d7e7ef07-a3d4-46af-a62e-089cb61b653b豆腐汤~1.jpg', 36, 69, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (47, '江团鱼2斤', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a101a1e9-8f8b-47b2-afa4-1abd47ea0a87.png', 38, 66, NULL, '重辣', 1, 119.00);
INSERT INTO `order_detail` VALUES (48, '东坡肘子', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a80a4b8c-c93e-4f43-ac8a-856b0d5cc451.png', 38, 59, NULL, NULL, 1, 138.00);
INSERT INTO `order_detail` VALUES (49, '剁椒鱼头', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/13da832f-ef2c-484d-8370-5934a1045a06.png', 38, 61, NULL, NULL, 1, 66.00);
INSERT INTO `order_detail` VALUES (50, '豆腐汤', 'http://127.0.0.1:8080/images/d7e7ef07-a3d4-46af-a62e-089cb61b653b豆腐汤~1.jpg', 38, 69, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (51, '清蒸鲈鱼', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c18b5c67-3b71-466c-a75a-e63c6449f21c.png', 39, 58, NULL, NULL, 1, 98.00);
INSERT INTO `order_detail` VALUES (52, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 41, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (53, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 44, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (54, '北冰洋', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', 44, 47, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (55, '北冰洋', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', 45, 47, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (56, '王老吉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', 45, 46, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (57, '王老吉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', 46, 46, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (58, '北冰洋', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', 46, 47, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (59, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 46, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (60, '北冰洋', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', 47, 47, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (61, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 47, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (62, '王老吉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', 48, 46, NULL, NULL, 3, 6.00);
INSERT INTO `order_detail` VALUES (63, '金汤酸菜牛蛙', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7694a5d8-7938-4e9d-8b9e-2075983a2e38.png', 50, 62, NULL, NULL, 1, 88.00);
INSERT INTO `order_detail` VALUES (64, '金汤酸菜牛蛙', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7694a5d8-7938-4e9d-8b9e-2075983a2e38.png', 51, 62, NULL, NULL, 1, 88.00);
INSERT INTO `order_detail` VALUES (65, '草鱼2斤', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/b544d3ba-a1ae-4d20-a860-81cb5dec9e03.png', 52, 65, NULL, '不辣', 1, 68.00);
INSERT INTO `order_detail` VALUES (66, '北冰洋', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', 52, 47, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (67, '草鱼2斤', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/b544d3ba-a1ae-4d20-a860-81cb5dec9e03.png', 53, 65, NULL, '不辣', 1, 68.00);
INSERT INTO `order_detail` VALUES (68, '王老吉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', 54, 46, NULL, NULL, 1, 6.00);
INSERT INTO `order_detail` VALUES (69, '豪华烤鱼', 'http://127.0.0.1:8080/images/d7d7325e-81df-4c48-8a98-f79cc860cc4e单链表递归遍历.PNG', 55, NULL, 20, NULL, 2, 122.00);
INSERT INTO `order_detail` VALUES (70, '豪华烤鱼', 'http://127.0.0.1:8080/images/d7d7325e-81df-4c48-8a98-f79cc860cc4e单链表递归遍历.PNG', 56, NULL, 20, NULL, 3, 122.00);
INSERT INTO `order_detail` VALUES (71, '北冰洋', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', 57, 47, NULL, NULL, 2, 4.00);
INSERT INTO `order_detail` VALUES (72, '江团鱼2斤', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a101a1e9-8f8b-47b2-afa4-1abd47ea0a87.png', 58, 66, NULL, '不辣', 1, 119.00);
INSERT INTO `order_detail` VALUES (73, '清蒸鲈鱼', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c18b5c67-3b71-466c-a75a-e63c6449f21c.png', 58, 58, NULL, NULL, 1, 98.00);
INSERT INTO `order_detail` VALUES (74, '老坛酸菜鱼', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4a9cefba-6a74-467e-9fde-6e687ea725d7.png', 59, 51, NULL, '不要葱,不辣', 1, 56.00);
INSERT INTO `order_detail` VALUES (75, '王老吉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', 59, 46, NULL, NULL, 2, 6.00);
INSERT INTO `order_detail` VALUES (76, '北冰洋', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', 60, 47, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (77, '清蒸鲈鱼', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c18b5c67-3b71-466c-a75a-e63c6449f21c.png', 61, 58, NULL, NULL, 1, 98.00);
INSERT INTO `order_detail` VALUES (78, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 62, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (79, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 64, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (80, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 66, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (81, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 67, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (82, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 69, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (83, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 71, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (84, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 74, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (85, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 76, 48, NULL, NULL, 1, 4.00);
INSERT INTO `order_detail` VALUES (86, '豪华烤鱼', 'http://127.0.0.1:8080/images/d7d7325e-81df-4c48-8a98-f79cc860cc4e单链表递归遍历.PNG', 77, NULL, 20, NULL, 1, 122.00);
INSERT INTO `order_detail` VALUES (87, '香锅牛蛙', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/f5ac8455-4793-450c-97ba-173795c34626.png', 78, 63, NULL, NULL, 1, 88.00);
INSERT INTO `order_detail` VALUES (88, '江团鱼2斤', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a101a1e9-8f8b-47b2-afa4-1abd47ea0a87.png', 79, 66, NULL, '重辣', 1, 119.00);
INSERT INTO `order_detail` VALUES (89, '雪花啤酒', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', 80, 48, NULL, NULL, 2, 4.00);
INSERT INTO `order_detail` VALUES (90, '酸菜肉丝面', 'http://127.0.0.1:8080/images/20daeea6-3a1f-4180-a23f-49b1c8f9109c豆腐汤~1.jpg', 81, 87, NULL, NULL, 1, 12.00);
INSERT INTO `order_detail` VALUES (91, '红烧牛肉面', 'http://127.0.0.1:8080/images/c015e3b3-2d62-48ce-ae65-447ff758faea豆腐汤~1.jpg', 81, 86, NULL, NULL, 1, 12.00);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单号',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款',
  `user_id` bigint(20) NOT NULL COMMENT '下单用户',
  `address_book_id` bigint(20) NOT NULL COMMENT '地址id',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `checkout_time` datetime NULL DEFAULT NULL COMMENT '结账时间',
  `pay_method` int(11) NOT NULL DEFAULT 1 COMMENT '支付方式 1微信,2支付宝',
  `pay_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '支付状态 0未支付 1已支付 2退款',
  `amount` decimal(10, 2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '地址',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户名称',
  `consignee` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '收货人',
  `cancel_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单取消原因',
  `rejection_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单拒绝原因',
  `cancel_time` datetime NULL DEFAULT NULL COMMENT '订单取消时间',
  `estimated_delivery_time` datetime NULL DEFAULT NULL COMMENT '预计送达时间',
  `delivery_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '配送状态  1立即送出  0选择具体时间',
  `delivery_time` datetime NULL DEFAULT NULL COMMENT '送达时间',
  `pack_amount` int(11) NULL DEFAULT NULL COMMENT '打包费',
  `tableware_number` int(11) NULL DEFAULT NULL COMMENT '餐具数量',
  `tableware_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '餐具数量状态  1按餐量提供  0选择具体数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (13, '1716119518028', 6, 1, 5, '2024-05-19 19:51:58', '2024-05-19 19:52:16', 1, 1, 11.00, '', '15575123265', '重庆三峡学院', NULL, '张三', '拒单：订单量较多，暂时无法接单', '拒单：订单量较多，暂时无法接单', '2024-05-20 16:14:38', '2024-05-19 20:51:00', 0, NULL, 1, 0, 0);
INSERT INTO `orders` VALUES (14, '1716119699882', 6, 1, 5, '2024-05-19 19:55:00', '2024-05-19 19:55:21', 1, 1, 129.00, '', '15575123265', '重庆三峡学院', NULL, '张三', NULL, '拒单：餐厅已打烊，暂时无法接单', '2024-05-20 16:12:54', '2024-05-19 20:54:00', 0, NULL, 1, 0, 0);
INSERT INTO `orders` VALUES (15, '1716119823534', 5, 1, 5, '2024-05-19 19:57:04', '2024-05-19 19:57:04', 1, 1, 82.00, '', '15575123265', '重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-19 20:57:00', 0, '2024-05-22 21:48:35', 2, 0, 0);
INSERT INTO `orders` VALUES (16, '1716120093052', 2, 1, 5, '2024-05-19 20:01:33', '2024-05-19 20:01:34', 1, 1, 11.00, '', '15575123265', '重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-19 21:01:00', 0, NULL, 1, 0, 0);
INSERT INTO `orders` VALUES (17, '1716120532401', 5, 1, 5, '2024-05-19 20:08:52', '2024-05-19 20:08:58', 1, 1, 65.00, '', '15575123265', '重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-19 21:08:00', 0, '2024-05-22 21:48:34', 1, 0, 0);
INSERT INTO `orders` VALUES (18, '1716120602924', 2, 1, 5, '2024-05-19 20:10:03', '2024-05-19 20:10:05', 1, 1, 11.00, '', '15575123265', '重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-19 21:10:00', 0, NULL, 1, 0, 0);
INSERT INTO `orders` VALUES (19, '1716121382614', 6, 1, 5, '2024-05-19 20:23:03', '2024-05-19 20:23:03', 1, 1, 11.00, '', '15575123265', '重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-19 21:23:00', 0, NULL, 1, 0, 0);
INSERT INTO `orders` VALUES (20, '1716121553090', 6, 1, 5, '2024-05-19 20:25:53', '2024-05-19 20:25:54', 1, 1, 11.00, '', '15575123265', '重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-19 21:25:00', 0, NULL, 1, 0, 0);
INSERT INTO `orders` VALUES (21, '1716124510971', 6, 1, 5, '2024-05-19 21:15:11', NULL, 1, 0, 18.00, '', '15575123265', '重庆三峡学院', NULL, '张三', '订单超时:自动取消', NULL, '2024-05-20 21:11:00', '2024-05-19 22:15:00', 0, NULL, 2, 0, 0);
INSERT INTO `orders` VALUES (22, '1716124645162', 5, 1, 5, '2024-05-19 21:17:25', '2024-05-19 21:17:26', 1, 1, 95.00, '', '15575123265', '重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-19 22:17:00', 0, NULL, 1, 0, 0);
INSERT INTO `orders` VALUES (23, '1716124690990', 6, 1, 5, '2024-05-19 21:18:11', '2024-05-19 21:18:13', 1, 1, 45.00, '', '15575123265', '重庆三峡学院', NULL, '张三', '用户取消订单', NULL, '2024-05-20 15:55:01', '2024-05-19 22:18:00', 0, NULL, 1, 0, 0);
INSERT INTO `orders` VALUES (24, '1716124690990', 5, 1, 5, '2024-05-19 21:18:11', '2024-05-19 21:18:13', 1, 1, 45.00, '', '15575123265', '重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-19 22:18:00', 0, NULL, 1, 0, 0);
INSERT INTO `orders` VALUES (25, '1716126244226', 6, 1, 5, '2024-05-19 21:44:04', '2024-05-19 21:44:05', 1, 1, 140.00, '', '15575123265', '重庆三峡学院', NULL, '张三', '用户取消订单', NULL, '2024-05-20 15:42:25', '2024-05-19 22:44:00', 0, NULL, 3, 0, 0);
INSERT INTO `orders` VALUES (27, '1716126244226', 5, 1, 5, '2024-05-19 21:44:04', '2024-05-19 21:44:05', 1, 1, 140.00, '', '15575123265', '重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-19 22:44:00', 0, '2024-05-22 21:37:16', 3, 0, 0);
INSERT INTO `orders` VALUES (28, '1716126507685', 6, 1, 5, '2024-05-19 21:48:28', '2024-05-19 21:48:29', 1, 1, 150.00, '', '15575123265', '重庆三峡学院', NULL, '张三', NULL, NULL, '2024-05-20 14:45:55', '2024-05-19 22:48:00', 0, NULL, 2, 0, 0);
INSERT INTO `orders` VALUES (29, '1716126507685', 6, 1, 5, '2024-05-19 21:48:28', '2024-05-19 21:48:29', 1, 1, 150.00, '', '15575123265', '重庆三峡学院', NULL, '张三', '订单量较多，暂时无法接单', NULL, '2024-05-20 15:56:44', '2024-05-19 22:48:00', 0, NULL, 2, 0, 0);
INSERT INTO `orders` VALUES (30, '1716126537441', 6, 1, 5, '2024-05-19 21:48:57', '2024-05-19 21:48:59', 1, 1, 209.00, '', '15575123265', '重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-19 22:48:00', 0, NULL, 3, 0, 0);
INSERT INTO `orders` VALUES (31, '1716126537441', 6, 1, 5, '2024-05-19 21:48:57', '2024-05-19 21:48:59', 1, 1, 209.00, '', '15575123265', '重庆三峡学院', NULL, '张三', '骑手不足无法配送', NULL, '2024-05-20 15:57:07', '2024-05-19 22:48:00', 0, NULL, 3, 0, 0);
INSERT INTO `orders` VALUES (32, '1716127168217', 6, 1, 5, '2024-05-19 21:59:28', '2024-05-19 21:59:29', 1, 1, 209.00, '', '15575123265', '浙江省杭州市重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-19 22:59:00', 0, NULL, 3, 0, 0);
INSERT INTO `orders` VALUES (33, '1716127248206', 6, 1, 5, '2024-05-19 22:00:48', '2024-05-19 22:00:49', 1, 1, 26.00, '', '15575123265', '浙江省杭州市富阳区重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-19 23:00:00', 0, NULL, 4, 0, 0);
INSERT INTO `orders` VALUES (34, '1716193727460', 5, 1, 5, '2024-05-20 16:28:47', '2024-05-20 16:28:51', 1, 1, 339.00, '你好你好', '15575123265', '浙江省杭州市富阳区重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-20 17:28:00', 0, '2024-05-22 21:48:34', 4, 0, 0);
INSERT INTO `orders` VALUES (35, '1716193727460', 2, 1, 5, '2024-05-20 16:28:47', '2024-05-20 16:28:51', 1, 1, 339.00, '你好你好', '15575123265', '浙江省杭州市富阳区重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-20 17:28:00', 0, NULL, 4, 0, 0);
INSERT INTO `orders` VALUES (36, '1716193800030', 2, 1, 5, '2024-05-20 16:30:00', '2024-05-20 16:30:01', 1, 1, 339.00, '', '15575123265', '浙江省杭州市富阳区重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-20 17:29:00', 0, NULL, 4, 1, 0);
INSERT INTO `orders` VALUES (37, '1716193800030', 2, 1, 5, '2024-05-20 16:30:00', '2024-05-20 16:30:01', 1, 1, 339.00, '', '15575123265', '浙江省杭州市富阳区重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-20 17:29:00', 0, NULL, 4, 1, 0);
INSERT INTO `orders` VALUES (38, '1716193874084', 2, 1, 5, '2024-05-20 16:31:14', '2024-05-20 16:31:16', 1, 1, 339.00, '', '15575123265', '浙江省杭州市富阳区重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-20 17:30:00', 0, NULL, 4, 4, 0);
INSERT INTO `orders` VALUES (39, '1716194095087', 2, 1, 5, '2024-05-20 16:34:55', '2024-05-20 16:34:56', 1, 1, 105.00, '', '15575123265', '浙江省杭州市富阳区重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-20 17:34:00', 0, NULL, 1, 1, 0);
INSERT INTO `orders` VALUES (40, '1716194095087', 2, 1, 5, '2024-05-20 16:34:55', '2024-05-20 16:34:56', 1, 1, 105.00, '', '15575123265', '浙江省杭州市富阳区重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-20 17:34:00', 0, NULL, 1, 1, 0);
INSERT INTO `orders` VALUES (41, '1716196426890', 5, 4, 9, '2024-05-20 17:13:47', '2024-05-20 17:13:48', 1, 1, 11.00, '', ' ', ' 到店堂食 null', NULL, ' ', NULL, NULL, NULL, '2024-05-20 18:13:00', 0, '2024-05-20 17:18:58', 1, 1, 0);
INSERT INTO `orders` VALUES (42, '1716196426890', 5, 4, 9, '2024-05-20 17:13:47', '2024-05-20 17:13:48', 1, 1, 11.00, '', ' ', ' 到店堂食 null', NULL, ' ', NULL, NULL, NULL, '2024-05-20 18:13:00', 0, '2024-05-20 17:18:58', 1, 1, 0);
INSERT INTO `orders` VALUES (44, '1716196917640', 2, 5, 11, '2024-05-20 17:21:58', '2024-05-20 17:21:59', 1, 1, 16.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-20 18:21:00', 0, NULL, 2, 1, 0);
INSERT INTO `orders` VALUES (45, '1716197192661', 2, 8, 17, '2024-05-20 17:26:33', '2024-05-20 17:26:35', 1, 1, 18.00, '', ' ', ' 到店自取  ', NULL, ' ', NULL, NULL, NULL, '2024-05-20 18:26:00', 0, NULL, 2, 1, 0);
INSERT INTO `orders` VALUES (46, '1716197720667', 2, 8, 16, '2024-05-20 17:35:21', '2024-05-20 17:35:22', 1, 1, 23.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-20 18:35:00', 0, NULL, 3, 1, 0);
INSERT INTO `orders` VALUES (47, '1716197814603', 2, 8, 18, '2024-05-20 17:36:55', '2024-05-20 17:36:56', 1, 1, 16.00, '', '18875471523', '北京市市辖区西城区重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-20 18:36:00', 0, NULL, 2, 1, 0);
INSERT INTO `orders` VALUES (48, '1716197888158', 5, 8, 18, '2024-05-20 17:38:08', '2024-05-20 17:38:11', 1, 1, 27.00, '', '18875471523', '北京市市辖区西城区重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-20 18:38:00', 0, '2024-05-22 21:37:15', 3, 1, 0);
INSERT INTO `orders` VALUES (49, '1716197888158', 5, 8, 18, '2024-05-20 17:38:08', '2024-05-20 17:38:11', 1, 1, 27.00, '', '18875471523', '北京市市辖区西城区重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-20 18:38:00', 0, '2024-05-22 21:37:15', 3, 1, 0);
INSERT INTO `orders` VALUES (50, '1716293486356', 6, 8, 16, '2024-05-21 20:11:26', NULL, 1, 0, 95.00, '', ' ', ' 到店堂食  ', NULL, ' ', '订单超时:自动取消', NULL, '2024-05-21 20:27:00', '2024-05-21 21:11:00', 0, NULL, 1, 1, 0);
INSERT INTO `orders` VALUES (51, '1716293652642', 6, 8, 16, '2024-05-21 20:14:13', NULL, 1, 0, 95.00, '', ' ', ' 到店堂食  ', NULL, ' ', '订单超时:自动取消', NULL, '2024-05-21 20:30:00', '2024-05-21 21:11:00', 0, NULL, 1, 1, 0);
INSERT INTO `orders` VALUES (52, '1716293769204', 5, 8, 16, '2024-05-21 20:16:09', '2024-05-21 20:16:51', 1, 1, 80.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 21:16:00', 0, '2024-05-22 21:37:14', 2, 1, 0);
INSERT INTO `orders` VALUES (53, '1716293866094', 6, 8, 16, '2024-05-21 20:17:46', NULL, 1, 0, 75.00, '', ' ', ' 到店堂食  ', NULL, ' ', '订单超时:自动取消', NULL, '2024-05-21 20:33:00', '2024-05-21 21:17:00', 0, NULL, 1, 1, 0);
INSERT INTO `orders` VALUES (54, '1716294244250', 6, 8, 16, '2024-05-21 20:24:04', NULL, 1, 0, 13.00, '', ' ', ' 到店堂食  ', NULL, ' ', '订单超时:自动取消', NULL, '2024-05-21 20:40:00', '2024-05-21 21:22:00', 0, NULL, 1, 1, 0);
INSERT INTO `orders` VALUES (55, '1716294448229', 6, 8, 16, '2024-05-21 20:27:28', NULL, 1, 0, 252.00, '', ' ', ' 到店堂食  ', NULL, ' ', '订单超时:自动取消', NULL, '2024-05-21 20:43:10', '2024-05-21 21:27:00', 0, NULL, 2, 1, 0);
INSERT INTO `orders` VALUES (56, '1716294473306', 6, 8, 16, '2024-05-21 20:27:53', NULL, 1, 0, 375.00, '', ' ', ' 到店堂食  ', NULL, ' ', '订单超时:自动取消', NULL, '2024-05-21 20:43:10', '2024-05-21 21:27:00', 0, NULL, 3, 1, 0);
INSERT INTO `orders` VALUES (57, '1716294742445', 6, 8, 16, '2024-05-21 20:32:22', NULL, 1, 0, 16.00, '', ' ', ' 到店堂食  ', NULL, ' ', '订单超时:自动取消', NULL, '2024-05-21 20:48:00', '2024-05-21 21:29:00', 0, NULL, 2, 1, 0);
INSERT INTO `orders` VALUES (58, '1716294762246', 6, 8, 16, '2024-05-21 20:32:42', NULL, 1, 0, 225.00, '', ' ', ' 到店堂食  ', NULL, ' ', '订单超时:自动取消', NULL, '2024-05-21 20:48:00', '2024-05-21 21:32:00', 0, NULL, 2, 1, 0);
INSERT INTO `orders` VALUES (59, '1716294799389', 5, 8, 16, '2024-05-21 20:33:19', '2024-05-21 20:33:21', 1, 1, 77.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 21:33:00', 0, '2024-05-22 21:37:14', 3, 1, 0);
INSERT INTO `orders` VALUES (60, '1716294858386', 2, 8, 16, '2024-05-21 20:34:18', '2024-05-21 20:34:19', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 21:34:00', 0, NULL, 1, 1, 0);
INSERT INTO `orders` VALUES (61, '1716295329766', 5, 8, 16, '2024-05-21 20:42:10', '2024-05-21 20:42:18', 1, 1, 105.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 21:42:00', 0, '2024-05-22 21:37:48', 1, 1, 0);
INSERT INTO `orders` VALUES (62, '1716295439246', 5, 8, 16, '2024-05-21 20:43:59', '2024-05-21 20:44:03', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 21:43:00', 0, '2024-05-27 14:01:15', 1, 1, 0);
INSERT INTO `orders` VALUES (63, '1716295439246', 2, 8, 16, '2024-05-21 20:43:59', '2024-05-21 20:44:03', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 21:43:00', 0, NULL, 1, 1, 0);
INSERT INTO `orders` VALUES (64, '1716295535972', 2, 8, 16, '2024-05-21 20:45:36', '2024-05-21 20:45:38', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 21:45:00', 0, NULL, 1, 1, 0);
INSERT INTO `orders` VALUES (65, '1716295535972', 2, 8, 16, '2024-05-21 20:45:36', '2024-05-21 20:45:38', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 21:45:00', 0, NULL, 1, 1, 0);
INSERT INTO `orders` VALUES (66, '1716295569794', 5, 8, 16, '2024-05-21 20:46:10', '2024-05-21 20:46:11', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 21:46:00', 0, '2024-05-27 14:01:14', 1, 1, 0);
INSERT INTO `orders` VALUES (67, '1716295619540', 5, 8, 16, '2024-05-21 20:47:00', '2024-05-21 20:47:00', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 21:46:00', 0, '2024-05-22 21:38:24', 1, 1, 0);
INSERT INTO `orders` VALUES (68, '1716295619540', 2, 8, 16, '2024-05-21 20:47:00', '2024-05-21 20:47:00', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 21:46:00', 0, NULL, 1, 1, 0);
INSERT INTO `orders` VALUES (69, '1716295625140', 5, 8, 16, '2024-05-21 20:47:05', '2024-05-21 20:47:06', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 21:47:00', 0, '2024-05-22 21:09:24', 1, 1, 0);
INSERT INTO `orders` VALUES (70, '1716295625140', 5, 8, 16, '2024-05-21 20:47:05', '2024-05-21 20:47:06', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 21:47:00', 0, '2024-05-27 11:37:55', 1, 1, 0);
INSERT INTO `orders` VALUES (71, '1716296508029', 5, 8, 16, '2024-05-21 21:01:48', '2024-05-21 21:01:50', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 22:01:00', 0, '2024-05-22 21:09:19', 1, 1, 0);
INSERT INTO `orders` VALUES (72, '1716296508029', 5, 8, 16, '2024-05-21 21:01:48', '2024-05-21 21:01:50', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 22:01:00', 0, '2024-05-22 21:09:20', 1, 1, 0);
INSERT INTO `orders` VALUES (73, '1716296508029', 5, 8, 16, '2024-05-21 21:01:48', '2024-05-21 21:01:50', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 22:01:00', 0, '2024-05-22 21:09:21', 1, 1, 0);
INSERT INTO `orders` VALUES (74, '1716297462615', 5, 8, 16, '2024-05-21 21:17:43', '2024-05-21 21:17:46', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 22:17:00', 0, '2024-05-21 21:19:40', 1, 1, 0);
INSERT INTO `orders` VALUES (75, '1716297462615', 5, 8, 16, '2024-05-21 21:17:43', '2024-05-21 21:17:46', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 22:17:00', 0, '2024-05-22 21:09:20', 1, 1, 0);
INSERT INTO `orders` VALUES (76, '1716297496122', 5, 8, 16, '2024-05-21 21:18:16', '2024-05-21 21:18:17', 1, 1, 11.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-21 22:18:00', 0, '2024-05-21 21:19:34', 1, 1, 0);
INSERT INTO `orders` VALUES (77, '1716297669373', 6, 8, 18, '2024-05-21 21:21:09', '2024-05-21 21:21:11', 1, 1, 129.00, '', '18875471523', '重庆市市辖区万州区百安坝街道天星路666号重庆三峡学院', NULL, '张三', '订单量较多，暂时无法接单', NULL, '2024-05-21 21:29:40', '2024-05-21 22:21:00', 0, NULL, 1, 1, 0);
INSERT INTO `orders` VALUES (78, '1716445878287', 5, 8, 18, '2024-05-23 14:31:18', '2024-05-23 14:31:21', 1, 1, 95.00, '', '18875471523', '重庆市市辖区万州区百安坝街道天星路666号重庆三峡学院', NULL, '张三', NULL, NULL, NULL, '2024-05-23 15:31:00', 0, '2024-05-23 14:31:47', 1, 1, 0);
INSERT INTO `orders` VALUES (79, '1716782061659', 5, 8, 16, '2024-05-27 11:54:22', '2024-05-27 11:54:25', 1, 1, 126.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-27 12:53:00', 0, '2024-05-27 14:01:59', 1, 1, 0);
INSERT INTO `orders` VALUES (80, '1716789748263', 5, 8, 16, '2024-05-27 14:02:28', '2024-05-27 14:02:30', 1, 1, 16.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-27 15:02:00', 0, '2024-05-27 14:02:47', 2, 1, 0);
INSERT INTO `orders` VALUES (81, '1716794654976', 6, 8, 16, '2024-05-27 15:24:15', '2024-05-27 15:24:16', 1, 1, 32.00, '', ' ', ' 到店堂食  ', NULL, ' ', '用户取消订单', NULL, '2024-05-27 15:26:22', '2024-05-27 16:24:00', 0, NULL, 2, 1, 0);
INSERT INTO `orders` VALUES (82, '1716794654976', 2, 8, 16, '2024-05-27 15:24:15', '2024-05-27 15:24:16', 1, 1, 32.00, '', ' ', ' 到店堂食  ', NULL, ' ', NULL, NULL, NULL, '2024-05-27 16:24:00', 0, NULL, 2, 1, 0);

-- ----------------------------
-- Table structure for setmeal
-- ----------------------------
DROP TABLE IF EXISTS `setmeal`;
CREATE TABLE `setmeal`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` bigint(20) NOT NULL COMMENT '菜品分类id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '套餐名称',
  `price` decimal(10, 2) NOT NULL COMMENT '套餐价格',
  `status` int(11) NULL DEFAULT 1 COMMENT '售卖状态 0:停售 1:起售',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_setmeal_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '套餐' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setmeal
-- ----------------------------
INSERT INTO `setmeal` VALUES (21, 24, '素食套餐1', 15.00, 1, '', 'http://127.0.0.1:8080/images/515ec301-9862-4a7d-bf3f-d00a3a4f5917豆腐汤~1.jpg', '2024-05-27 15:09:27', '2024-05-27 15:09:27', 1, 1);
INSERT INTO `setmeal` VALUES (23, 24, '素食套餐2', 15.00, 1, '', 'http://127.0.0.1:8080/images/515ec301-9862-4a7d-bf3f-d00a3a4f5917豆腐汤~1.jpg', '2024-05-27 15:10:12', '2024-05-27 15:10:12', 1, 1);
INSERT INTO `setmeal` VALUES (24, 13, '人气套餐1', 16.00, 1, '', 'http://127.0.0.1:8080/images/615cf484-89e7-43c4-8550-47f4d9e106da豆腐汤~1.jpg', '2024-05-27 15:14:47', '2024-05-27 15:14:47', 1, 1);
INSERT INTO `setmeal` VALUES (26, 13, '人气套餐2', 16.00, 1, '', 'http://127.0.0.1:8080/images/615cf484-89e7-43c4-8550-47f4d9e106da豆腐汤~1.jpg', '2024-05-27 15:19:58', '2024-05-27 15:19:58', 1, 1);
INSERT INTO `setmeal` VALUES (27, 13, '人气套餐3', 16.00, 1, '', 'http://127.0.0.1:8080/images/615cf484-89e7-43c4-8550-47f4d9e106da豆腐汤~1.jpg', '2024-05-27 15:21:37', '2024-05-27 15:21:37', 1, 1);
INSERT INTO `setmeal` VALUES (28, 15, '商务套餐1', 50.00, 1, '', 'http://127.0.0.1:8080/images/69dc90d8-16c6-4959-8166-db1cb53efe4d豆腐汤~1.jpg', '2024-05-27 15:22:37', '2024-05-27 15:22:37', 1, 1);
INSERT INTO `setmeal` VALUES (29, 15, '商务套餐2', 2.00, 1, '', 'http://127.0.0.1:8080/images/b213cbff-bf9a-4907-8a25-c8e89ad73203豆腐汤~1.jpg', '2024-05-27 15:23:09', '2024-05-27 15:23:09', 1, 1);

-- ----------------------------
-- Table structure for setmeal_dish
-- ----------------------------
DROP TABLE IF EXISTS `setmeal_dish`;
CREATE TABLE `setmeal_dish`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `setmeal_id` bigint(20) NULL DEFAULT NULL COMMENT '套餐id',
  `dish_id` bigint(20) NULL DEFAULT NULL COMMENT '菜品id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜品名称 （冗余字段）',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '菜品单价（冗余字段）',
  `copies` int(11) NULL DEFAULT NULL COMMENT '菜品份数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '套餐菜品关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setmeal_dish
-- ----------------------------
INSERT INTO `setmeal_dish` VALUES (20, 21, 75, '紫菜蛋花汤', 16.00, 1);
INSERT INTO `setmeal_dish` VALUES (21, 21, 81, '扬州炒饭', 15.00, 1);
INSERT INTO `setmeal_dish` VALUES (22, 21, 80, '红烧茄子', 15.00, 1);
INSERT INTO `setmeal_dish` VALUES (23, 23, 75, '紫菜蛋花汤', 16.00, 1);
INSERT INTO `setmeal_dish` VALUES (24, 23, 81, '扬州炒饭', 15.00, 1);
INSERT INTO `setmeal_dish` VALUES (25, 23, 80, '红烧茄子', 15.00, 1);
INSERT INTO `setmeal_dish` VALUES (26, 24, 75, '紫菜蛋花汤', 16.00, 1);
INSERT INTO `setmeal_dish` VALUES (27, 24, 84, '鸡蛋肥牛炒饭', 15.00, 1);
INSERT INTO `setmeal_dish` VALUES (28, 26, 75, '紫菜蛋花汤', 16.00, 1);
INSERT INTO `setmeal_dish` VALUES (29, 26, 84, '鸡蛋肥牛炒饭', 15.00, 1);
INSERT INTO `setmeal_dish` VALUES (30, 27, 75, '紫菜蛋花汤', 16.00, 1);
INSERT INTO `setmeal_dish` VALUES (31, 27, 84, '鸡蛋肥牛炒饭', 15.00, 1);
INSERT INTO `setmeal_dish` VALUES (32, 28, 81, '扬州炒饭', 15.00, 1);
INSERT INTO `setmeal_dish` VALUES (33, 28, 72, '水煮鱼', 30.00, 1);
INSERT INTO `setmeal_dish` VALUES (34, 28, 90, '可乐', 3.50, 1);
INSERT INTO `setmeal_dish` VALUES (35, 29, 90, '可乐', 3.50, 1);
INSERT INTO `setmeal_dish` VALUES (36, 29, 72, '水煮鱼', 30.00, 1);
INSERT INTO `setmeal_dish` VALUES (37, 29, 81, '扬州炒饭', 15.00, 1);

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '商品名称',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `user_id` bigint(20) NOT NULL COMMENT '主键',
  `dish_id` bigint(20) NULL DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint(20) NULL DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味',
  `number` int(11) NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------
INSERT INTO `shopping_cart` VALUES (31, '酸菜肉丝面', 'http://127.0.0.1:8080/images/20daeea6-3a1f-4180-a23f-49b1c8f9109c豆腐汤~1.jpg', 8, 87, NULL, NULL, 1, 12.00, '2024-05-27 15:24:25');
INSERT INTO `shopping_cart` VALUES (32, '红烧牛肉面', 'http://127.0.0.1:8080/images/c015e3b3-2d62-48ce-ae65-447ff758faea豆腐汤~1.jpg', 8, 86, NULL, NULL, 1, 12.00, '2024-05-27 15:24:25');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `openid` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '微信用户唯一标识',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '头像',
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (8, 'oNqoR7YyaIZYs4vxC8UZ6veqmBeY', NULL, NULL, NULL, NULL, NULL, '2024-05-20 17:26:12');
INSERT INTO `user` VALUES (9, 'dsefsd', NULL, NULL, NULL, NULL, NULL, '2024-05-22 19:02:51');

SET FOREIGN_KEY_CHECKS = 1;
