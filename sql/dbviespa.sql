SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `dbviespa` DEFAULT CHARACTER SET utf8;
USE `dbviespa`;

CREATE TABLE `course` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL DEFAULT b'1',
  `create_at` datetime DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `update_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `course` (`id`, `active`, `create_at`, `description`, `name`, `price`, `update_at`) VALUES
(1, b'1', '2022-12-24 21:57:42', 'Description for Course 1', 'Course 1', 499, '2022-12-24 21:57:42'),
(2, b'1', '2022-12-24 21:57:42', 'Description for Course 2', 'Course 2', 699, '2022-12-24 21:57:42'),
(3, b'1', '2022-12-24 21:57:42', 'Description for Course 3', 'Course 3', 899, '2022-12-24 21:57:42');

CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `is_female` bit(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `update_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `customer` (`id`, `address`, `create_at`, `email`, `is_female`, `name`, `phone`, `update_at`) VALUES
(1, '123 Paris - Phap - My Tho', '2022-12-24 21:57:42', 'nam@vippro.vn', b'0', 'Nam Kon 2K', '0124324345', '2022-12-24 21:57:42'),
(2, '35 Lao - Y - Tay Ninh', '2022-12-24 21:57:42', 'namk2kon@deptrai.vn', b'0', 'Nam K 2Kon', '0137897855', '2022-12-24 21:57:42'),
(3, '385 Doi Can - HN', '2022-12-24 21:57:42', 'yeuanhduc@germany.com', b'1', '= Ngan', '09235374534', '2022-12-24 21:57:42');

CREATE TABLE `spa_transaction` (
  `id` bigint(20) NOT NULL,
  `create_at` datetime DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `note` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `update_at` datetime DEFAULT NULL,
  `course_id` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `account` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `dob` date DEFAULT NULL,
  `phone` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `joindate` date DEFAULT NULL,
  `enddate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `users` (`id`, `account`, `password`, `fullname`, `dob`, `phone`, `email`, `address`, `role`, `joindate`, `enddate`) VALUES
(1, 'admin', '123123', 'Admin', '2000-06-10', 927023333, 'admin@gmail.com', '905 Đê La Thành', 1, '2022-12-30', NULL),
(2, 'user', '123123', 'User', '2000-06-10', 927023333, 'user@gmail.com', '905 Đê La Thành', 2, '2022-12-30', NULL),
(3, 'accountant', '123123', 'Accountant', '2000-06-10', 927023333, 'accountant@gmail.com', '905 Đê La Thành', 3, '2022-12-30', NULL),
(4, 'test1', '123123', 'test1', '2022-12-30', 123123, '123123', '123123', 2, '2022-12-30', NULL),
(5, 'test2', '123123', 'test2', '2022-12-30', 123123, '123123', '905 dd', 2, '2022-12-30', NULL),
(6, 'test3', '123123', 'test3', '2022-12-31', 123123, '123123', '123123', 2, '2022-12-30', NULL),
(7, 'test4', '123123', 'test4', '2022-12-29', 123123, '123123', '123123', 2, '2022-12-29', NULL);


ALTER TABLE `course`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `spa_transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfribea89esu01713kny23qbn1` (`course_id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `created_by` (`created_by`);

ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `course`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `customer`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `spa_transaction`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;


ALTER TABLE `spa_transaction`
  ADD CONSTRAINT `FKfribea89esu01713kny23qbn1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  ADD CONSTRAINT `spa_transaction_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  ADD CONSTRAINT `spa_transaction_ibfk_2` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
