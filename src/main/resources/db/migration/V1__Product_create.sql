drop table IF exists `product`;
create table `product` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `short_description` varchar(200) NOT NULL,
  `detail_description` text NOT NULL,
  `price` double NOT NULL,
  `special_price` double NULL,
  `img_url` varchar(200) NOT NULL,
  `created_at` datetime NOT NULL
);
