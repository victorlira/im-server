DROP TABLE IF EXISTS `t_domain`;
CREATE TABLE `t_domain` (
  `_domain_id` varchar(64) NOT NULL PRIMARY KEY,
  `_name` varchar(64) NOT NULL,
  `_desc` varchar(256) DEFAULT '',
  `_email` varchar(64) DEFAULT '',
  `_tel` varchar(64) DEFAULT '',
  `_address` varchar(64) DEFAULT '',
  `_extra` varchar(1024) DEFAULT '',
  `_dt` bigint(20) NOT NULL
);

alter table `t_user` add column `_external` tinyint DEFAULT 0;
