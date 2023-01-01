-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Jan 01, 2023 at 05:32 PM
-- Server version: 5.7.34
-- PHP Version: 8.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbviespa`
--

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL DEFAULT b'1',
  `create_at` datetime DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `update_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`id`, `active`, `create_at`, `description`, `name`, `price`, `update_at`) VALUES
(1, b'1', '2022-12-24 21:57:42', 'Description for Course 1', 'Course 1', 499, '2022-12-24 21:57:42'),
(2, b'1', '2022-12-24 21:57:42', 'Description for Course 2', 'Course 2', 699, '2022-12-24 21:57:42'),
(3, b'1', '2022-12-24 21:57:42', 'Description for Course 3', 'Course 3', 899, '2022-12-24 21:57:42');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `id` bigint(20) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `is_female` int(11) NOT NULL,
  `dob` date NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `create_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `fullname`, `is_female`, `dob`, `address`, `phone`, `email`, `create_at`, `update_at`) VALUES
(1, 'Duy Anh', 0, '1994-01-04', 'Thiên Hùng - Khâm Thiên - Đống Đa - Hà Nội', '0912345678', 'duyanh@gmail.com', '2023-01-01 23:10:33', '2023-01-01 23:10:33'),
(2, 'Hoàng Nam', 0, '1999-01-01', 'Giải Phóng - Hai Bà Trưng - Hà Nội', '0912345678', '0912345678', '2023-01-01 23:23:52', '2023-01-01 23:23:52');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `id` bigint(20) NOT NULL,
  `customer_id` bigint(20) NOT NULL,
  `course_id` bigint(20) NOT NULL,
  `staff_id` bigint(20) NOT NULL,
  `pay` varchar(255) NOT NULL,
  `note` varchar(255) NOT NULL,
  `booking` date NOT NULL,
  `created_by` bigint(20) NOT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`id`, `customer_id`, `course_id`, `staff_id`, `pay`, `note`, `booking`, `created_by`, `create_at`, `update_at`) VALUES
(1, 1, 1, 4, '499', 'abc1', '2023-01-01', 1, '2023-01-01 16:37:46', '2023-01-01 16:37:46'),
(2, 2, 3, 2, '899.0', 'abc', '2023-01-02', 1, NULL, NULL),
(3, 1, 2, 2, '699.0', 'abc2', '2023-01-04', 1, NULL, NULL),
(4, 2, 1, 2, '699.0', 'abc', '2023-01-04', 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

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

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `account`, `password`, `fullname`, `dob`, `phone`, `email`, `address`, `role`, `joindate`, `enddate`) VALUES
(1, 'admin', '123123', 'Admin', '2000-06-10', 927023333, 'admin@gmail.com', '905 Đê La Thành', 1, '2022-12-30', NULL),
(2, 'user', '123123', 'User', '2000-06-10', 927023333, 'user@gmail.com', '905 Đê La Thành', 2, '2022-12-30', NULL),
(3, 'accountant', '123123', 'Accountant', '2000-06-10', 927023333, 'accountant@gmail.com', '905 Đê La Thành', 3, '2022-12-30', NULL),
(4, 'test1', '123123', 'test1', '2022-12-30', 123123, '123123', '123123', 2, '2022-12-30', NULL),
(5, 'test2', '123123', 'test2', '2022-12-30', 123123, '123123', '905 dd', 2, '2022-12-30', NULL),
(6, 'test3', '123123', 'test3', '2022-12-31', 123123, '123123', '123123', 2, '2022-12-30', NULL),
(7, 'test4', '123123', 'test4', '2022-12-29', 123123, '123123', '123123', 2, '2022-12-29', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `course_id` (`course_id`),
  ADD KEY `staff_id` (`staff_id`),
  ADD KEY `created_by` (`created_by`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  ADD CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  ADD CONSTRAINT `transactions_ibfk_3` FOREIGN KEY (`staff_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `transactions_ibfk_4` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
