# im stat版本控制
DROP TABLE IF EXISTS `im_stat`;

CREATE TABLE `im_stat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `avatar_version` bigint(20) NOT NULL DEFAULT '0',
  `created_time` bigint(20) NOT NULL,
  `updated_time` bigint(20) NOT NULL,
   PRIMARY KEY (`id`),
   KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
