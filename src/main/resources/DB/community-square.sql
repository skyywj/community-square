# ************************************************************
# Sequel Pro SQL dump
# Version 4541
# Host: 127.0.0.1 (MySQL 5.7.24)
# Database: community-square
# Generation Time: 2018-12-06 03:09:53 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table admin_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `admin_user`;

CREATE TABLE `admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(255) NOT NULL DEFAULT '',
  `in_time` datetime NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `admin_user` WRITE;
/*!40000 ALTER TABLE `admin_user` DISABLE KEYS */;

INSERT INTO `admin_user` (`id`, `username`, `password`, `in_time`, `role_id`)
VALUES
	(1,'admin','$2a$10$0F6RXnrQDF8SsOudYk7uhuWlqq3kjPuPm4UGeDCj0gvO8xj2pbZ4y','2018-11-11 11:11:11',1);

/*!40000 ALTER TABLE `admin_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table code
# ------------------------------------------------------------

DROP TABLE IF EXISTS `code`;

CREATE TABLE `code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL,
  `code` varchar(255) NOT NULL DEFAULT '',
  `in_time` datetime NOT NULL,
  `expire_time` datetime NOT NULL,
  `email` varchar(255) NOT NULL DEFAULT '',
  `used` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table collect
# ------------------------------------------------------------

DROP TABLE IF EXISTS `collect`;

CREATE TABLE `collect` (
  `topic_id` int(11) NOT NULL,
  `user_id` bigint(32) NOT NULL,
  `in_time` datetime NOT NULL,
  KEY `topic_id` (`topic_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table comment
# ------------------------------------------------------------

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext NOT NULL,
  `topic_id` int(11) NOT NULL,
  `topic_user_name` varchar(255) NOT NULL,
  `user_id` bigint(32) NOT NULL,
  `user_name` varchar(255) default NULL,
  `user_avatar` varchar(1000) default NULL,
  `comment_id` int(11) DEFAULT NULL,
  `up_ids` text,
  `in_time` datetime NOT NULL,
  `created_time` bigint(20) NOT NULL,
  `updated_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `topic_id` (`topic_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table notification
# ------------------------------------------------------------

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `user_id` bigint(32) NOT NULL,
  `user_name` varchar (255) NOT NULL,
  `user_avatar` varchar (1024) NOT NULL,
  `target_user_id` bigint(32) NOT NULL,
  `action` varchar(255) NOT NULL DEFAULT '',
  `read` bit(1) NOT NULL DEFAULT b'0',
  `content` longtext,
  `created_time` bigint(20) NOT NULL,
  `updated_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `topic_id` (`topic_id`),
  KEY `user_id` (`user_id`),
  KEY `target_user_id` (`target_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `value` varchar(255) NOT NULL DEFAULT '',
  `pid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `value` (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `permission` WRITE;

INSERT INTO `permission` (`id`, `name`, `value`, `pid`)
VALUES
	(1,'首页','index',0),
	(2,'话题','topic',0),
	(3,'评论','comment',0),
	(4,'通知','notification',0),
	(5,'用户','user',0),
	(6,'验证码','code',0),
	(7,'标签','tag',0),
	(8,'权限','permission',0),
	(9,'系统','system',0),
	(10,'后台用户','admin_user',0),
	(11,'仪表盘','index:index',1),
	(12,'话题列表','topic:list',2),
	(13,'话题编辑','topic:edit',2),
	(14,'话题删除','topic:delete',2),
	(15,'话题加精','topic:good',2),
	(16,'话题置顶','topic:top',2),
	(17,'评论列表','comment:list',3),
	(18,'评论编辑','comment:edit',3),
	(19,'评论删除','comment:delete',3),
	(20,'通知列表','notification:list',4),
	(21,'通知删除','notification:delete',4),
	(22,'用户列表','user:list',5),
	(23,'用户编辑','user:edit',5),
	(24,'用户删除','user:delete',5),
	(25,'验证码列表','code:list',6),
	(26,'标签列表','tag:list',7),
	(27,'标签编辑','tag:edit',7),
	(28,'标签删除','tag:delete',7),
	(29,'标签同步','tag:async',7),
	(30,'权限列表','permission:list',8),
	(31,'权限编辑','permission:edit',8),
	(32,'权限删除','permission:delete',8),
	(33,'角色','role',0),
	(34,'日志','log',0),
	(35,'角色列表','role:list',33),
	(36,'角色编辑','role:edit',33),
	(37,'角色删除','role:delete',33),
	(38,'系统设置','system:edit',9),
	(39,'后台用户列表','admin_user:list',10),
	(40,'后台用户编辑','admin_user:edit',10),
	(41,'后台用户创建','admin_user:add',10),
	(42,'日志列表','log:list',34),
	(43,'用户刷新Token','user:refresh_token',5),
	(44,'权限添加','permission:add',8);

/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;

INSERT INTO `role` (`id`, `name`)
VALUES
	(2,'审核员'),
	(1,'超级管理员');

/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role_permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  KEY `role_id` (`role_id`),
  KEY `permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;

INSERT INTO `role_permission` (`role_id`, `permission_id`)
VALUES
	(1,11),
	(1,12),
	(1,13),
	(1,14),
	(1,15),
	(1,16),
	(1,17),
	(1,18),
	(1,19),
	(1,20),
	(1,21),
	(1,22),
	(1,23),
	(1,24),
	(1,43),
	(1,25),
	(1,26),
	(1,27),
	(1,28),
	(1,29),
	(1,30),
	(1,31),
	(1,32),
	(1,44),
	(1,38),
	(1,39),
	(1,40),
	(1,41),
	(1,35),
	(1,36),
	(1,37),
	(1,42),
	(2,11),
	(2,12),
	(2,13),
	(2,14),
	(2,15),
	(2,16),
	(2,17),
	(2,18),
	(2,19),
	(2,26),
	(2,27),
	(2,28),
	(2,29);

/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table system_config
# ------------------------------------------------------------

DROP TABLE IF EXISTS `system_config`;

CREATE TABLE `system_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `description` varchar(1000) NOT NULL,
  `pid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

LOCK TABLES `system_config` WRITE;
/*!40000 ALTER TABLE `system_config` DISABLE KEYS */;

INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`)
VALUES
	(1, 'adminRememberMeMaxAge', '1', '登录后台记住我功能记住时间，单位：天', 23),
	(2, 'baseUrl', 'http://localhost:8080', '网站部署后访问的域名，注意这个后面没有 \"/\"', 23),
	(3, 'commentLayer', '0', '评论盖楼形式显示，1：是，0：否', 23),
	(4, 'cookie.domain', 'localhost', '存cookie时用到的域名，要与网站部署后访问的域名一致', 23),
	(5, 'cookie.maxAge', '604800', 'cookie有效期，单位秒，默认1周', 23),
	(6, 'cookie.name', 'user_token', '存cookie时用到的名称', 23),
	(7, 'createCommentScore', '5', '发布评论奖励的积分', 26),
	(8, 'createTopicScore', '10', '创建话题奖励的积分', 26),
	(9, 'deleteCommentScore', '5', '删除评论要被扣除的积分', 26),
	(10, 'deleteTopicScore', '10', '删除话题要被扣除的积分', 26),
	(11, 'intro', '<h5>属于山科大的BBS</h5><p>在这里，您可以提问，回答，分享，诉说，这是个属于山东科技大学的社区，欢迎您的加入！</p>', '站点介绍', 23),
	(12, 'mail.host', 'smtp.163.com', '邮箱的smtp服务器地址', 24),
	(13, 'mail.auth.code', '***', '发送邮件的邮箱授权码', 24),
	(14, 'mail.username', 'CarryJey@163.com', '发送邮件的邮箱地址', 24),
	(15, 'name', '山科微社交广场平台', '站点名称', 23),
	(16, 'pageSize', '20', '分页每页条数', 23),
	(17, 'socketNotification', '0', '是否开启websocket长连接获取通知数量，1：开启，0：关闭', 23),
	(18, 'staticUrl', 'http://localhost:8080/static/upload/', '静态文件访问地址，主要用于上传图片的访问，注意最后有个\"/\"', 23),
	(19, 'upCommentScore', '3', '点赞评论奖励评论作者的积分', 26),
	(20, 'uploadAvatarSizeLimit', '2', '上传头像文件大小，单位MB，默认2MB', 25),
	(21, 'uploadPath', '/Users/a/Desktop/pics/community-square/static/upload/', '上传文件的路径，注意最后有个\"/\"', 25),
	(22, 'upTopicScore', '3', '点赞话题奖励话题作者的积分', 26),
	(23, NULL, NULL, '基础配置', 0),
	(24, NULL, NULL, '邮箱配置', 0),
	(25, NULL, NULL, '上传配置', 0),
	(26, NULL, NULL, '积分配置', 0),
	(27, NULL, NULL, 'Redis配置', 0),
	(29, 'redis.host', '127.0.0.1', 'redis服务host地址', 27),
	(30, 'redis.port', '6379', 'redis服务端口', 27),
	(31, 'redis.password', '123456', 'redis服务密码', 27),
	(32, 'redis.timeout', '3000', '网站连接redis服务超时时间，单位毫秒', 27),
	(33, 'redis.database', '0', '网站连接redis服务的哪个数据库，默认0号数据库，取值范围0-15', 27),
	(34, 'redis.ssl', '0', 'redis服务是否开启认证连接，开启: 1，关闭: 0', 27);

/*!40000 ALTER TABLE `system_config` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tag
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(1000) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `topic_count` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table topic
# ------------------------------------------------------------

DROP TABLE IF EXISTS `topic`;

CREATE TABLE `topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL DEFAULT '',
  `content` longtext,
  `in_time` datetime NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  `user_id` bigint(32) NOT NULL,
  `user_name`varchar(255) default NULL,
  `user_avatar`varchar(1000) default NULL,
  `comment_count` int(11) NOT NULL DEFAULT '0',
  `collect_count` int(11) NOT NULL DEFAULT '0',
  `view` int(11) NOT NULL DEFAULT '0',
  `top` bit(1) NOT NULL DEFAULT b'0',
  `good` bit(1) NOT NULL DEFAULT b'0',
  `up_ids` text,
  `created_time` bigint(20) NOT NULL,
  `updated_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table topic_tag
# ------------------------------------------------------------

DROP TABLE IF EXISTS `topic_tag`;

CREATE TABLE `topic_tag` (
  `tag_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL,
  KEY `tag_id` (`tag_id`),
  KEY `topic_id` (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL,
  `username` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(255) DEFAULT '',
  `avatar` varchar(1000) DEFAULT NULL,
  `email` varchar(255) DEFAULT '',
  `website` varchar(255) DEFAULT NULL,
  `bio` varchar(1000) DEFAULT NULL,
  `score` int(11) NOT NULL DEFAULT '0',
  `token` varchar(255) NOT NULL DEFAULT '',
  `github_name` varchar(255) DEFAULT NULL,
  `telegram_name` varchar(255) DEFAULT NULL,
  `email_notification` bit(1) NOT NULL DEFAULT b'0',
  `created_time` bigint(20) NOT NULL,
  `updated_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `token` (`token`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 订阅表
# ------------------------------------------------------------

DROP TABLE IF EXISTS `subscribe_record`;

CREATE TABLE `subscribe_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL,
  `from_user_id` bigint(32) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1：关注，0：不关注',
  `created_time` bigint(20) NOT NULL,
  `updated_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`user_id`),
  KEY `fromUserId` (`from_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

