CREATE TABLE `user` (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `name` varchar(255) DEFAULT NULL,
        `money` int(11) DEFAULT NULL,
        `money_freeze` int(11) DEFAULT NULL,
        PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

INSERT `user`(name,money) VALUES('张一',100000);
INSERT `user`(name,money) VALUES('张二',100000);
INSERT `user`(name,money) VALUES('张三',100000);
INSERT `user`(name,money) VALUES('张四',100000);
INSERT `user`(name,money) VALUES('张五',100000);
INSERT `user`(name,money) VALUES('张六',100000);

CREATE TABLE `t_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;