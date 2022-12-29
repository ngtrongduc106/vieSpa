-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Dec 29, 2022 at 08:11 PM
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
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
