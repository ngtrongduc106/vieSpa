-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Dec 30, 2022 at 02:29 PM
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
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`id`, `name`, `price`, `description`, `create_by`) VALUES
(1, 'Body massage', '200', 'body massage', 2),
(2, 'Làm đẹp', '100', '12345', 1);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `id` int(11) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `fullname`, `phone`, `address`, `email`, `dob`) VALUES
(1, 'Trần Duy Anh', '0912345678', 'Thiên Hùng - Khâm Thiên - Hà Nội', 'duyanh@gmail.com', '2022-12-30'),
(2, 'Vũ Mạnh Nam', '09123456789', 'Kim Mã - Ba Đình - Hà Nội', '0912345678', '2022-12-30');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `staff_id` int(11) NOT NULL,
  `booking_at` datetime DEFAULT NULL,
  `transaction_note` varchar(255) DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`id`, `customer_id`, `course_id`, `staff_id`, `booking_at`, `transaction_note`, `create_by`) VALUES
(2, 1, 1, 3, '2022-12-30 12:56:50', '123', 2);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
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
(2, 'manager', '123123', 'Manager', '2000-06-10', 912345678, 'manager@gmail.com', 'Đê La Thành - Ba Đình - Hà Nội', 2, '2022-12-30', NULL),
(3, 'accountant', '123123', 'Accountant', '2000-06-10', 912345678, 'accountant@gmail.com', 'Đê La Thành - Ba Đình - Hà Nội', 3, '2022-12-30', NULL),
(4, 'user1', '123123', 'User1', '2000-06-10', 912345678, 'user1@gmail.com', 'Đê La Thành - Ba Đình - Hà Nội', 4, '2022-12-30', NULL),
(5, 'user2', '123123', 'User2', '2000-06-10', 912345678, 'user2@gmail.com', 'Đê La Thành - Ba Đình - Hà Nội', 4, '2022-12-30', NULL),
(6, 'user3', '123123', 'User3', '2000-06-10', 912345678, 'user3@gmail.com', 'Đê La Thành - Ba Đình - Hà Nội', 4, '2022-12-30', NULL),
(7, 'user4', '123123', 'User4', '2000-06-10', 912345678, 'user4@gmail.com', 'Đê La Thành - Ba Đình - Hà Nội', 4, '2022-12-30', NULL),
(8, 'user5', '123123', 'User5', '2000-06-10', 912345678, 'user5@gmail.com', 'Đê La Thành - Ba Đình - Hà Nội', 4, '2022-12-30', NULL),
(9, 'user6', '123123', 'User6', '2000-06-10', 912345678, 'user6@gmail.com', 'Đê La Thành - Ba Đình - Hà Nội', 4, '2022-12-30', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `create_by` (`create_by`);

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
  ADD KEY `course_id` (`course_id`),
  ADD KEY `staff_id` (`staff_id`),
  ADD KEY `create_by` (`create_by`),
  ADD KEY `transactions_ibfk_4` (`customer_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `courses`
--
ALTER TABLE `courses`
  ADD CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`create_by`) REFERENCES `users` (`id`);

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  ADD CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`staff_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `transactions_ibfk_3` FOREIGN KEY (`create_by`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `transactions_ibfk_4` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
