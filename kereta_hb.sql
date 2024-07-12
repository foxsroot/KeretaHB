-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 12, 2024 at 06:18 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kereta_hb`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `user_id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `cellphone` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`user_id`, `email`, `password`, `name`, `cellphone`) VALUES
(1, 'admin1@example.com', 'password1', 'Admin One', '123-456-7890'),
(2, 'admin2@example.com', 'password2', 'Admin Two', '987-654-3210'),
(3, 'admin3@example.com', 'password3', 'Admin Three', '555-123-4567');

-- --------------------------------------------------------

--
-- Table structure for table `carriage`
--

CREATE TABLE `carriage` (
  `carriage_id` int(11) NOT NULL,
  `train_id` int(11) DEFAULT NULL,
  `type` enum('EATERY','TOILET','SEATING') DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `class` enum('ECONOMY','BUSINESS','EXECUTIVE') DEFAULT NULL,
  `baggage_allowance` double UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `carriage`
--

INSERT INTO `carriage` (`carriage_id`, `train_id`, `type`, `capacity`, `class`, `baggage_allowance`) VALUES
(1, 1, 'TOILET', 0, 'ECONOMY', 0),
(2, 1, 'EATERY', 50, 'ECONOMY', 10),
(3, 1, 'SEATING', 100, 'ECONOMY', 20),
(4, 1, 'SEATING', 80, 'BUSINESS', 30),
(5, 1, 'SEATING', 70, 'EXECUTIVE', 40),
(6, 2, 'TOILET', 0, 'ECONOMY', 0),
(7, 2, 'EATERY', 50, 'BUSINESS', 10),
(8, 2, 'SEATING', 100, 'BUSINESS', 20),
(9, 2, 'SEATING', 80, 'EXECUTIVE', 30),
(10, 2, 'SEATING', 70, 'ECONOMY', 40),
(11, 3, 'TOILET', 0, 'ECONOMY', 0),
(12, 3, 'EATERY', 50, 'EXECUTIVE', 10),
(13, 3, 'SEATING', 100, 'EXECUTIVE', 20),
(14, 3, 'SEATING', 80, 'ECONOMY', 30),
(15, 3, 'SEATING', 70, 'BUSINESS', 40),
(16, 4, 'TOILET', 0, 'ECONOMY', 0),
(17, 4, 'EATERY', 50, 'ECONOMY', 10),
(18, 4, 'SEATING', 100, 'ECONOMY', 20),
(19, 4, 'SEATING', 80, 'BUSINESS', 30),
(20, 4, 'SEATING', 70, 'EXECUTIVE', 40),
(21, 5, 'TOILET', 0, 'ECONOMY', 0),
(22, 5, 'EATERY', 50, 'BUSINESS', 10),
(23, 5, 'SEATING', 100, 'BUSINESS', 20),
(24, 5, 'SEATING', 80, 'EXECUTIVE', 30),
(25, 5, 'SEATING', 70, 'ECONOMY', 40),
(26, 6, 'TOILET', 0, 'ECONOMY', 0),
(27, 6, 'EATERY', 50, 'ECONOMY', 10),
(28, 6, 'SEATING', 100, 'ECONOMY', 20),
(29, 6, 'SEATING', 80, 'BUSINESS', 30),
(30, 6, 'SEATING', 70, 'EXECUTIVE', 40),
(31, 7, 'TOILET', 0, 'ECONOMY', 0),
(32, 7, 'EATERY', 50, 'ECONOMY', 10),
(33, 7, 'SEATING', 100, 'ECONOMY', 20),
(34, 7, 'SEATING', 80, 'BUSINESS', 30),
(35, 7, 'SEATING', 70, 'EXECUTIVE', 40),
(36, 8, 'TOILET', 0, 'ECONOMY', 0),
(37, 8, 'EATERY', 50, 'ECONOMY', 10),
(38, 8, 'SEATING', 100, 'ECONOMY', 20),
(39, 8, 'SEATING', 80, 'BUSINESS', 30),
(40, 8, 'SEATING', 70, 'EXECUTIVE', 40),
(41, 9, 'TOILET', 0, 'ECONOMY', 0),
(42, 9, 'EATERY', 50, 'ECONOMY', 10),
(43, 9, 'SEATING', 100, 'ECONOMY', 20),
(44, 9, 'SEATING', 80, 'BUSINESS', 30),
(45, 9, 'SEATING', 70, 'EXECUTIVE', 40),
(46, 10, 'TOILET', 0, 'ECONOMY', 0),
(47, 10, 'EATERY', 50, 'ECONOMY', 10),
(48, 10, 'SEATING', 100, 'ECONOMY', 20),
(49, 10, 'SEATING', 80, 'BUSINESS', 30),
(50, 10, 'SEATING', 70, 'EXECUTIVE', 40),
(51, 11, 'TOILET', 0, 'ECONOMY', 0),
(52, 11, 'EATERY', 50, 'ECONOMY', 10),
(53, 11, 'SEATING', 100, 'ECONOMY', 20),
(54, 11, 'SEATING', 80, 'BUSINESS', 30),
(55, 11, 'SEATING', 70, 'EXECUTIVE', 40),
(56, 12, 'TOILET', 0, 'ECONOMY', 0),
(57, 12, 'EATERY', 50, 'ECONOMY', 10),
(58, 12, 'SEATING', 100, 'ECONOMY', 20),
(59, 12, 'SEATING', 80, 'BUSINESS', 30),
(60, 12, 'SEATING', 70, 'EXECUTIVE', 40);

-- --------------------------------------------------------

--
-- Table structure for table `cart_item`
--

CREATE TABLE `cart_item` (
  `item_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `victual_id` int(11) NOT NULL,
  `station_id` int(11) NOT NULL,
  `amount` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `loyalty`
--

CREATE TABLE `loyalty` (
  `loyalty_id` int(11) NOT NULL,
  `loyalty_type` enum('CLASSIC','ELITE','EXECUTIVE','VVIP') NOT NULL,
  `discount` double DEFAULT NULL,
  `minimum_transaction` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `loyalty`
--

INSERT INTO `loyalty` (`loyalty_id`, `loyalty_type`, `discount`, `minimum_transaction`) VALUES
(1, 'CLASSIC', 0, 0),
(2, 'ELITE', 0.15, 1000000),
(3, 'EXECUTIVE', 0.2, 10000000),
(4, 'VVIP', 0.25, 100000000);

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `notification_id` int(11) NOT NULL,
  `recipient_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `message` longtext NOT NULL,
  `received_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`notification_id`, `recipient_id`, `title`, `message`, `received_date`) VALUES
(1, 2, 'test', 'teasasadasd', '2024-07-04 17:00:00'),
(2, 2, 'test', 'ehehehehhehehehe', '2024-07-04 17:00:00'),
(3, 2, 'Cobain pertama', '111111111111111111111111111111111111111111111111111', '2024-07-04 17:00:00'),
(4, 2, 'Cobain 2', 'ohohohohohohoo', '2024-07-04 17:00:00'),
(5, 2, 'hehe cobain dulu gasi', 'ini nyoba nyoba ehe', '2024-07-05 10:40:19'),
(6, 2, 'New Notification', 'This is a new notification', '2024-07-05 10:40:19'),
(7, 2, 'Hello World', 'Hello from the other side', '2024-07-05 11:00:00'),
(8, 2, 'Test Message', 'This is a test message', '2024-07-05 11:20:00'),
(9, 2, 'Cobain Again', 'Cobain lagi dan lagi', '2024-07-05 11:40:00'),
(10, 2, 'Random Title', 'Random message with random content', '2024-07-05 12:00:00'),
(11, 2, 'Notification 5', 'This is the fifth notification', '2024-07-05 12:20:00'),
(12, 2, 'Long Message', 'This is a very long message that will not fit in one line', '2024-07-05 12:40:00'),
(13, 2, 'Short Title', 'Short message with short content', '2024-07-05 13:00:00'),
(14, 2, 'Urgent Notification', 'This is an urgent notification', '2024-07-05 13:20:00'),
(15, 2, 'Info Message', 'This is an informational message', '2024-07-05 13:40:00'),
(16, 2, 'Warning Notification', 'This is a warning notification', '2024-07-05 14:00:00'),
(17, 2, 'Success Message', 'This is a success message', '2024-07-05 14:20:00'),
(18, 2, 'Error Notification', 'This is an error notification', '2024-07-05 14:40:00'),
(19, 2, 'New Update', 'There is a new update available', '2024-07-05 15:00:00'),
(20, 2, 'Reminder', 'This is a reminder notification', '2024-07-05 15:20:00'),
(21, 2, 'Alert Message', 'This is an alert message', '2024-07-05 15:40:00'),
(22, 2, 'Notification 16', 'This is the 16th notification', '2024-07-05 16:00:00'),
(23, 2, 'Notification 17', 'This is the 17th notification', '2024-07-05 16:20:00'),
(24, 2, 'Notification 18', 'This is the 18th notification', '2024-07-05 16:40:00'),
(25, 2, 'Notification 19', 'This is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notification', '2024-07-05 17:00:00'),
(26, 2, 'Notification 20', 'This is the 20th notification', '2024-07-05 17:20:00'),
(27, 2, 'New Notification 21', 'This is a new notification 21', '2024-07-05 17:40:00'),
(28, 2, 'Hello Again 22', 'Hello from the other side 22', '2024-07-05 18:00:00'),
(29, 2, 'Test Message 23', 'This is a test message 23', '2024-07-05 18:20:00'),
(30, 2, 'Cobain Again 24', 'Cobain lagi dan lagi 24', '2024-07-05 18:40:00'),
(31, 2, 'Random Title 25', 'Random message with random content 25', '2024-07-05 19:00:00'),
(32, 2, 'Notification 26', 'This is the 26th notification', '2024-07-05 19:20:00'),
(33, 2, 'Long Message 27', 'This is a very long message that will not fit in one line 27', '2024-07-05 19:40:00'),
(34, 2, 'Short Title 28', 'Short message with short content 28', '2024-07-05 20:00:00'),
(35, 2, 'Urgent Notification 29', 'This is an urgent notification 29', '2024-07-05 20:20:00'),
(36, 2, 'Info Message 30', 'This is an informational message 30', '2024-07-05 20:40:00'),
(37, 2, 'Warning Notification 31', 'This is a warning notification 31', '2024-07-05 21:00:00'),
(38, 2, 'Success Message 32', 'This is a success message 32', '2024-07-05 21:20:00'),
(39, 2, 'Error Notification 33', 'This is an error notification 33', '2024-07-05 21:40:00'),
(40, 2, 'New Update 34', 'There is a new update available 34', '2024-07-05 22:00:00'),
(41, 2, 'Reminder 35', 'This is a reminder notification 35', '2024-07-05 22:20:00'),
(42, 2, 'Alert Message 36', 'This is an alert message 36', '2024-07-05 22:40:00'),
(43, 2, 'Notification 37', 'This is the 37th notification', '2024-07-05 23:00:00'),
(44, 2, 'Notification 38', 'This is the 38th notification', '2024-07-05 23:20:00'),
(45, 2, 'Notification 39', 'This is the 39th notification', '2024-07-05 23:40:00'),
(46, 2, 'Notification 40', 'This is the 40th notification', '2024-07-06 00:00:00'),
(47, 2, 'Notification 41', 'This is the 41st notification', '2024-07-06 00:20:00'),
(48, 2, 'Notification 42', 'This is the 42nd notification', '2024-07-06 00:40:00'),
(49, 2, 'Notification 43', 'This is the 43rd notification', '2024-07-06 01:00:00'),
(50, 2, 'Notification 44', 'This is the 44th notification', '2024-07-06 01:20:00'),
(51, 2, 'Notification 45', 'This is the 45th notification', '2024-07-06 01:40:00'),
(52, 2, 'Notification 46', 'This is the 46th notification', '2024-07-06 02:00:00'),
(53, 2, 'Notification 47', 'This is the 47th notification', '2024-07-06 02:20:00'),
(54, 2, 'Notification 48', 'This is the 48th notification', '2024-07-06 02:40:00'),
(55, 2, 'Notification 49', 'This is the 49th notification', '2024-07-06 03:00:00'),
(56, 2, 'Notification 50', 'This is the 50th notification', '2024-07-06 03:20:00'),
(57, 2, 'Notification 51', 'This is the 51st notification', '2024-07-06 03:40:00'),
(58, 2, 'Victuals Cancelation', 'Hi ---, we have successfully canceled your victual transaction and returned Rp 86000.0 to your wallet.\n\nThank you!', '2024-07-09 05:57:52'),
(59, 2, 'Reschedule Request Approved', 'Hi there, We are pleased to inform you that your request to reschedule your appointment has been approved', '2024-07-10 18:05:52'),
(60, 2, 'Victuals Cancelation', 'Hi ---, we have successfully canceled your victual transaction and returned Rp 20000.0 to your wallet.\n\nThank you!', '2024-07-11 16:36:39'),
(61, 2, 'Victuals Cancelation', 'Hi ---, we have successfully canceled your victual transaction and returned Rp 91000.0 to your wallet.\n\nThank you!', '2024-07-11 16:37:17');

-- --------------------------------------------------------

--
-- Table structure for table `passenger`
--

CREATE TABLE `passenger` (
  `user_id` int(11) NOT NULL,
  `loyalty` int(11) DEFAULT 1,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `cellphone` varchar(20) DEFAULT NULL,
  `total_paid` double DEFAULT 0,
  `pfp` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `passenger`
--

INSERT INTO `passenger` (`user_id`, `loyalty`, `email`, `password`, `name`, `cellphone`, `total_paid`, `pfp`) VALUES
(2, 2, 'john.doe@example.com', 'password123', 'John Doe', '1234567890', 4231275, 'john_pfp.png');

-- --------------------------------------------------------

--
-- Table structure for table `reschedule_request`
--

CREATE TABLE `reschedule_request` (
  `reschedule_id` int(11) NOT NULL,
  `transaction_id` int(11) NOT NULL,
  `admin_id` int(11) DEFAULT NULL,
  `requested_schedule_id` int(11) NOT NULL,
  `status` enum('SUCCESS','DENIED','PENDING') DEFAULT 'PENDING'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `schedule`
--

CREATE TABLE `schedule` (
  `schedule_id` int(11) NOT NULL,
  `train_id` int(11) DEFAULT NULL,
  `departure_station_id` int(11) DEFAULT NULL,
  `arrival_station_id` int(11) DEFAULT NULL,
  `departure_date` date DEFAULT NULL,
  `fee` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `schedule`
--

INSERT INTO `schedule` (`schedule_id`, `train_id`, `departure_station_id`, `arrival_station_id`, `departure_date`, `fee`) VALUES
(22, 1, 1, 2, '2024-07-20', 500000);

-- --------------------------------------------------------

--
-- Table structure for table `schedule_capacity`
--

CREATE TABLE `schedule_capacity` (
  `capacity_id` int(11) NOT NULL,
  `carriage_id` int(11) NOT NULL,
  `schedule_id` int(11) NOT NULL,
  `occupied` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `schedule_capacity`
--

INSERT INTO `schedule_capacity` (`capacity_id`, `carriage_id`, `schedule_id`, `occupied`) VALUES
(75, 3, 22, 0),
(76, 4, 22, 0),
(77, 5, 22, 0);

-- --------------------------------------------------------

--
-- Table structure for table `station`
--

CREATE TABLE `station` (
  `station_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `income` int(10) UNSIGNED DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `station`
--

INSERT INTO `station` (`station_id`, `name`, `location`, `income`, `picture`) VALUES
(1, 'Bandung Station', 'Bandung, West Java', 800000000, 'bandung_station.jpg'),
(2, 'Bekasi Station', 'Bekasi, West Java', 731000000, 'bekasi_station.jpg'),
(3, 'Bogor Station', 'Bogor, West Java', 763000000, 'bogor_station.jpg'),
(4, 'Cirebon Station', 'Cirebon, West Java', 779000000, 'cirebon_station.jpg'),
(5, 'Depok Station', 'Depok, West Java', 747000000, 'depok_station.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `stock_id` int(11) NOT NULL,
  `victual_id` int(11) DEFAULT NULL,
  `station_id` int(11) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`stock_id`, `victual_id`, `station_id`, `stock`) VALUES
(13, 9, 1, 89),
(14, 8, 1, 65),
(15, 14, 1, 61),
(16, 11, 2, 400),
(20, 10, 5, 50),
(21, 12, 5, 99),
(22, 9, 5, 500),
(24, 9, 2, 250),
(25, 10, 2, 300),
(26, 12, 2, 200),
(27, 14, 2, 50),
(28, 13, 4, 100),
(29, 9, 4, 500),
(30, 8, 4, 250),
(31, 9, 3, 500),
(32, 8, 3, 150),
(33, 11, 3, 200),
(34, 10, 3, 200),
(35, 12, 3, 100),
(36, 13, 3, 100),
(37, 14, 3, 150);

-- --------------------------------------------------------

--
-- Table structure for table `ticket_transaction`
--

CREATE TABLE `ticket_transaction` (
  `transaction_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `schedule_id` int(11) DEFAULT NULL,
  `purchase_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `passengers` int(11) NOT NULL,
  `type` enum('ECONOMY','BUSINESS','EXECUTIVE') NOT NULL,
  `commute` tinyint(1) NOT NULL,
  `rescheduled` tinyint(1) NOT NULL DEFAULT 0,
  `total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `train`
--

CREATE TABLE `train` (
  `train_id` int(11) NOT NULL,
  `station_id` int(11) NOT NULL,
  `speed` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `train`
--

INSERT INTO `train` (`train_id`, `station_id`, `speed`) VALUES
(1, 1, 100),
(2, 2, 105),
(3, 3, 110),
(4, 4, 115),
(5, 5, 120),
(6, 1, 95),
(7, 2, 100),
(8, 3, 105),
(9, 4, 110),
(10, 5, 115),
(11, 1, 120),
(12, 2, 125),
(13, 3, 130),
(14, 4, 135),
(15, 5, 140);

-- --------------------------------------------------------

--
-- Table structure for table `transaction_item`
--

CREATE TABLE `transaction_item` (
  `transaction_item_id` int(11) NOT NULL,
  `transaction_id` int(11) NOT NULL,
  `victual_id` int(11) DEFAULT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaction_item`
--

INSERT INTO `transaction_item` (`transaction_item_id`, `transaction_id`, `victual_id`, `quantity`) VALUES
(14, 9, 8, 2),
(15, 9, 9, 1),
(16, 9, 14, 3),
(18, 11, 14, 5),
(19, 12, 9, 7),
(20, 13, NULL, 5),
(21, 14, 8, 1),
(22, 14, 9, 3),
(23, 14, 14, 1),
(24, 14, NULL, 3);

-- --------------------------------------------------------

--
-- Table structure for table `victual`
--

CREATE TABLE `victual` (
  `victual_id` int(11) NOT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int(10) UNSIGNED DEFAULT NULL,
  `description` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `victual`
--

INSERT INTO `victual` (`victual_id`, `picture`, `name`, `price`, `description`) VALUES
(8, '86ed4429-8323-488d-a660-47b693617f25.jpg', 'ICHI OCHA', 5000, 'Ichi Ocha is a tea from Indonesia'),
(9, '7cd287f1-6856-4a20-bc27-ba8fac24e413.jpg', 'AQUA', 10000, 'AQUA comes from selected spring water sources with all the purity and natural mineral content that is maintained through good processes, ensuring that hygiene is maintained and AQUA is packaged using a hygienic process in several sizes.\n'),
(10, '9bd248d0-a9d2-45b4-a3af-e7f88ae1e3e6.jpeg', 'MILO', 12000, 'In a box of Milo served every day, there are:  \nMalt Extract: Good source of energy \nCocoa Powder: Enriches the taste of chocolate that children like\nMilk: Source of Protein and Calcium \nBoxed Milo is a source of Vitamins B1, B2, B6, as well as Calcium and Phosphorus'),
(11, 'e6286cd6-cc31-4307-ac34-ea8fc8131b3d.jpg', 'CHEETOS', 20000, 'Bring a cheesy, delicious crunch to snack time with a bag of CHEETOS® Crunchy Cheese-Flavored Snacks. Made with real cheese for maximum flavor.'),
(12, 'aebe687a-f3ed-4115-9842-5c7003b6c72e.jpg', 'NESCAFE ICE BLACK', 6000, 'Nescafe Ice Black is ready-to-drink coffee made from a mixture of selected Arabica and Robusta coffee beans with a strong coffee taste. This coffee is ground and processed using the best techniques and technology to produce a distinctive taste. Nescafe Ice Black is available in practical can packaging and you can take it anywhere.\n'),
(13, '40dff1cd-c713-42b0-9a5b-745706ccc896.jpg', 'CHICKEN GEPREK', 20000, 'Chicken geprek is a typical Indonesian fried chicken dish with flour that is smashed with chili sauce.'),
(14, 'f66ec1d1-50c1-451d-ac39-690751e3642e.jpg', 'FRIED RICE', 17000, 'Fried rice is a dish of cooked rice that has been stir-fried in a wok or a frying pan and is usually mixed with other ingredients such as eggs, vegetables, seafood, or meat. It is often eaten by itself or as an accompaniment to another dish.');

-- --------------------------------------------------------

--
-- Table structure for table `victuals_transaction`
--

CREATE TABLE `victuals_transaction` (
  `transaction_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `station_id` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  `total` double NOT NULL,
  `status` enum('EXPIRED','CLAIMED','PENDING') NOT NULL DEFAULT 'PENDING'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `victuals_transaction`
--

INSERT INTO `victuals_transaction` (`transaction_id`, `user_id`, `station_id`, `date`, `total`, `status`) VALUES
(9, 2, 1, '2024-07-09 09:08:39', 71000, 'CLAIMED'),
(11, 2, 1, '2024-07-09 09:10:45', 85000, 'PENDING'),
(12, 2, 1, '2024-07-09 09:11:04', 70000, 'PENDING'),
(13, 2, 1, '2024-07-09 09:16:56', 25000, 'PENDING'),
(14, 2, 1, '2024-07-09 09:17:30', 67000, 'PENDING');

-- --------------------------------------------------------

--
-- Table structure for table `wallet`
--

CREATE TABLE `wallet` (
  `wallet_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `pin` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `wallet`
--

INSERT INTO `wallet` (`wallet_id`, `user_id`, `balance`, `pin`) VALUES
(1, 2, 72398725, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `carriage`
--
ALTER TABLE `carriage`
  ADD PRIMARY KEY (`carriage_id`),
  ADD KEY `train_id` (`train_id`);

--
-- Indexes for table `cart_item`
--
ALTER TABLE `cart_item`
  ADD PRIMARY KEY (`item_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `victual_id` (`victual_id`),
  ADD KEY `fk_cart_item_station_id` (`station_id`);

--
-- Indexes for table `loyalty`
--
ALTER TABLE `loyalty`
  ADD PRIMARY KEY (`loyalty_id`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`notification_id`),
  ADD KEY `recipient_id` (`recipient_id`);

--
-- Indexes for table `passenger`
--
ALTER TABLE `passenger`
  ADD PRIMARY KEY (`user_id`),
  ADD KEY `loyalty` (`loyalty`);

--
-- Indexes for table `reschedule_request`
--
ALTER TABLE `reschedule_request`
  ADD PRIMARY KEY (`reschedule_id`),
  ADD KEY `transaction_id` (`transaction_id`),
  ADD KEY `fk_admin` (`admin_id`),
  ADD KEY `fk_requested_schedule_id` (`requested_schedule_id`);

--
-- Indexes for table `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`schedule_id`),
  ADD KEY `train_id` (`train_id`),
  ADD KEY `departure_station_id` (`departure_station_id`),
  ADD KEY `arrival_station_id` (`arrival_station_id`);

--
-- Indexes for table `schedule_capacity`
--
ALTER TABLE `schedule_capacity`
  ADD PRIMARY KEY (`capacity_id`),
  ADD KEY `carriage_id` (`carriage_id`),
  ADD KEY `schedule_id` (`schedule_id`);

--
-- Indexes for table `station`
--
ALTER TABLE `station`
  ADD PRIMARY KEY (`station_id`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`stock_id`),
  ADD KEY `victual_id` (`victual_id`),
  ADD KEY `station_id` (`station_id`);

--
-- Indexes for table `ticket_transaction`
--
ALTER TABLE `ticket_transaction`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `schedule_id` (`schedule_id`);

--
-- Indexes for table `train`
--
ALTER TABLE `train`
  ADD PRIMARY KEY (`train_id`),
  ADD KEY `station_id` (`station_id`);

--
-- Indexes for table `transaction_item`
--
ALTER TABLE `transaction_item`
  ADD PRIMARY KEY (`transaction_item_id`),
  ADD KEY `transaction_id` (`transaction_id`),
  ADD KEY `victual_id` (`victual_id`);

--
-- Indexes for table `victual`
--
ALTER TABLE `victual`
  ADD PRIMARY KEY (`victual_id`);

--
-- Indexes for table `victuals_transaction`
--
ALTER TABLE `victuals_transaction`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `fk_station_id` (`station_id`);

--
-- Indexes for table `wallet`
--
ALTER TABLE `wallet`
  ADD PRIMARY KEY (`wallet_id`),
  ADD KEY `user_id` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `carriage`
--
ALTER TABLE `carriage`
  MODIFY `carriage_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT for table `cart_item`
--
ALTER TABLE `cart_item`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;

--
-- AUTO_INCREMENT for table `loyalty`
--
ALTER TABLE `loyalty`
  MODIFY `loyalty_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `notification_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;

--
-- AUTO_INCREMENT for table `passenger`
--
ALTER TABLE `passenger`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `reschedule_request`
--
ALTER TABLE `reschedule_request`
  MODIFY `reschedule_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `schedule`
--
ALTER TABLE `schedule`
  MODIFY `schedule_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `schedule_capacity`
--
ALTER TABLE `schedule_capacity`
  MODIFY `capacity_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=78;

--
-- AUTO_INCREMENT for table `station`
--
ALTER TABLE `station`
  MODIFY `station_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `stock`
--
ALTER TABLE `stock`
  MODIFY `stock_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `ticket_transaction`
--
ALTER TABLE `ticket_transaction`
  MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `train`
--
ALTER TABLE `train`
  MODIFY `train_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `transaction_item`
--
ALTER TABLE `transaction_item`
  MODIFY `transaction_item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `victual`
--
ALTER TABLE `victual`
  MODIFY `victual_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `victuals_transaction`
--
ALTER TABLE `victuals_transaction`
  MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `wallet`
--
ALTER TABLE `wallet`
  MODIFY `wallet_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `carriage`
--
ALTER TABLE `carriage`
  ADD CONSTRAINT `carriage_ibfk_1` FOREIGN KEY (`train_id`) REFERENCES `train` (`train_id`);

--
-- Constraints for table `cart_item`
--
ALTER TABLE `cart_item`
  ADD CONSTRAINT `cart_item_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `passenger` (`user_id`),
  ADD CONSTRAINT `cart_item_ibfk_2` FOREIGN KEY (`victual_id`) REFERENCES `victual` (`victual_id`),
  ADD CONSTRAINT `fk_cart_item_station_id` FOREIGN KEY (`station_id`) REFERENCES `station` (`station_id`);

--
-- Constraints for table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`recipient_id`) REFERENCES `passenger` (`user_id`);

--
-- Constraints for table `passenger`
--
ALTER TABLE `passenger`
  ADD CONSTRAINT `passenger_ibfk_2` FOREIGN KEY (`loyalty`) REFERENCES `loyalty` (`loyalty_id`);

--
-- Constraints for table `reschedule_request`
--
ALTER TABLE `reschedule_request`
  ADD CONSTRAINT `fk_admin` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`user_id`),
  ADD CONSTRAINT `fk_requested_schedule_id` FOREIGN KEY (`requested_schedule_id`) REFERENCES `schedule` (`schedule_id`),
  ADD CONSTRAINT `reschedule_request_ibfk_1` FOREIGN KEY (`transaction_id`) REFERENCES `ticket_transaction` (`transaction_id`);

--
-- Constraints for table `schedule`
--
ALTER TABLE `schedule`
  ADD CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`train_id`) REFERENCES `train` (`train_id`),
  ADD CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`departure_station_id`) REFERENCES `station` (`station_id`),
  ADD CONSTRAINT `schedule_ibfk_3` FOREIGN KEY (`arrival_station_id`) REFERENCES `station` (`station_id`);

--
-- Constraints for table `schedule_capacity`
--
ALTER TABLE `schedule_capacity`
  ADD CONSTRAINT `schedule_capacity_ibfk_1` FOREIGN KEY (`carriage_id`) REFERENCES `carriage` (`carriage_id`),
  ADD CONSTRAINT `schedule_capacity_ibfk_2` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`schedule_id`);

--
-- Constraints for table `stock`
--
ALTER TABLE `stock`
  ADD CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`victual_id`) REFERENCES `victual` (`victual_id`),
  ADD CONSTRAINT `stock_ibfk_2` FOREIGN KEY (`station_id`) REFERENCES `station` (`station_id`);

--
-- Constraints for table `ticket_transaction`
--
ALTER TABLE `ticket_transaction`
  ADD CONSTRAINT `ticket_transaction_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `passenger` (`user_id`),
  ADD CONSTRAINT `ticket_transaction_ibfk_2` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`schedule_id`);

--
-- Constraints for table `train`
--
ALTER TABLE `train`
  ADD CONSTRAINT `train_ibfk_1` FOREIGN KEY (`station_id`) REFERENCES `station` (`station_id`);

--
-- Constraints for table `transaction_item`
--
ALTER TABLE `transaction_item`
  ADD CONSTRAINT `transaction_item_ibfk_1` FOREIGN KEY (`transaction_id`) REFERENCES `victuals_transaction` (`transaction_id`),
  ADD CONSTRAINT `transaction_item_ibfk_2` FOREIGN KEY (`victual_id`) REFERENCES `victual` (`victual_id`);

--
-- Constraints for table `victuals_transaction`
--
ALTER TABLE `victuals_transaction`
  ADD CONSTRAINT `fk_station_id` FOREIGN KEY (`station_id`) REFERENCES `station` (`station_id`),
  ADD CONSTRAINT `victuals_transaction_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `passenger` (`user_id`);

--
-- Constraints for table `wallet`
--
ALTER TABLE `wallet`
  ADD CONSTRAINT `wallet_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `passenger` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
