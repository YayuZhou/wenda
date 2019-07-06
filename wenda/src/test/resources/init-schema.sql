Drop TABLE IF EXISTS question;
CREATE TABLE `question` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` text,
  `user_id` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `comment_count` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

Drop TABLE IF EXISTS user;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `password` varchar(128) NOT NULL,
  `salt` varchar(32) NOT NULL,
  `head_url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

Drop TABLE IF EXISTS login_ticket;
CREATE TABLE `login_ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket` varchar(45) NOT NULL,
  `expired` datetime NOT NULL,
  `status` int null default 0,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY ticket_UNIQUE (ticket ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;