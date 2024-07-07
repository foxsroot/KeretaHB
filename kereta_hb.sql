-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 07, 2024 at 11:41 AM
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
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `cellphone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

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
  `user_id` int(11) DEFAULT NULL,
  `victual_id` int(11) DEFAULT NULL,
  `station_id` int(11) DEFAULT NULL,
  `amount` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `loyalty`
--

CREATE TABLE `loyalty` (
  `loyalty_id` int(11) NOT NULL,
  `loyalty_type` enum('CLASSIC','ELITE','EXECUTIVE','VVIP') DEFAULT NULL,
  `discount` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `notification_id` int(11) NOT NULL,
  `recipient_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `message` longtext NOT NULL,
  `received_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`notification_id`, `recipient_id`, `title`, `message`, `received_date`) VALUES
(1, 2, 'test', 'teasasadasd', '2024-07-05 00:00:00'),
(2, 2, 'test', 'ehehehehhehehehe', '2024-07-05 00:00:00'),
(3, 2, 'Cobain pertama', '111111111111111111111111111111111111111111111111111', '2024-07-05 00:00:00'),
(4, 2, 'Cobain 2', 'ohohohohohohoo', '2024-07-05 00:00:00'),
(5, 2, 'hehe cobain dulu gasi', 'ini nyoba nyoba ehe', '2024-07-05 17:40:19'),
(6, 2, 'New Notification', 'This is a new notification', '2024-07-05 17:40:19'),
(7, 2, 'Hello World', 'Hello from the other side', '2024-07-05 18:00:00'),
(8, 2, 'Test Message', 'This is a test message', '2024-07-05 18:20:00'),
(9, 2, 'Cobain Again', 'Cobain lagi dan lagi', '2024-07-05 18:40:00'),
(10, 2, 'Random Title', 'Random message with random content', '2024-07-05 19:00:00'),
(11, 2, 'Notification 5', 'This is the fifth notification', '2024-07-05 19:20:00'),
(12, 2, 'Long Message', 'This is a very long message that will not fit in one line', '2024-07-05 19:40:00'),
(13, 2, 'Short Title', 'Short message with short content', '2024-07-05 20:00:00'),
(14, 2, 'Urgent Notification', 'This is an urgent notification', '2024-07-05 20:20:00'),
(15, 2, 'Info Message', 'This is an informational message', '2024-07-05 20:40:00'),
(16, 2, 'Warning Notification', 'This is a warning notification', '2024-07-05 21:00:00'),
(17, 2, 'Success Message', 'This is a success message', '2024-07-05 21:20:00'),
(18, 2, 'Error Notification', 'This is an error notification', '2024-07-05 21:40:00'),
(19, 2, 'New Update', 'There is a new update available', '2024-07-05 22:00:00'),
(20, 2, 'Reminder', 'This is a reminder notification', '2024-07-05 22:20:00'),
(21, 2, 'Alert Message', 'This is an alert message', '2024-07-05 22:40:00'),
(22, 2, 'Notification 16', 'This is the 16th notification', '2024-07-05 23:00:00'),
(23, 2, 'Notification 17', 'This is the 17th notification', '2024-07-05 23:20:00'),
(24, 2, 'Notification 18', 'This is the 18th notification', '2024-07-05 23:40:00'),
(25, 2, 'Notification 19', 'This is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notificationThis is the 19th notification', '2024-07-06 00:00:00'),
(26, 2, 'Notification 20', 'This is the 20th notification', '2024-07-06 00:20:00'),
(27, 2, 'New Notification 21', 'This is a new notification 21', '2024-07-06 00:40:00'),
(28, 2, 'Hello Again 22', 'Hello from the other side 22', '2024-07-06 01:00:00'),
(29, 2, 'Test Message 23', 'This is a test message 23', '2024-07-06 01:20:00'),
(30, 2, 'Cobain Again 24', 'Cobain lagi dan lagi 24', '2024-07-06 01:40:00'),
(31, 2, 'Random Title 25', 'Random message with random content 25', '2024-07-06 02:00:00'),
(32, 2, 'Notification 26', 'This is the 26th notification', '2024-07-06 02:20:00'),
(33, 2, 'Long Message 27', 'This is a very long message that will not fit in one line 27', '2024-07-06 02:40:00'),
(34, 2, 'Short Title 28', 'Short message with short content 28', '2024-07-06 03:00:00'),
(35, 2, 'Urgent Notification 29', 'This is an urgent notification 29', '2024-07-06 03:20:00'),
(36, 2, 'Info Message 30', 'This is an informational message 30', '2024-07-06 03:40:00'),
(37, 2, 'Warning Notification 31', 'This is a warning notification 31', '2024-07-06 04:00:00'),
(38, 2, 'Success Message 32', 'This is a success message 32', '2024-07-06 04:20:00'),
(39, 2, 'Error Notification 33', 'This is an error notification 33', '2024-07-06 04:40:00'),
(40, 2, 'New Update 34', 'There is a new update available 34', '2024-07-06 05:00:00'),
(41, 2, 'Reminder 35', 'This is a reminder notification 35', '2024-07-06 05:20:00'),
(42, 2, 'Alert Message 36', 'This is an alert message 36', '2024-07-06 05:40:00'),
(43, 2, 'Notification 37', 'This is the 37th notification', '2024-07-06 06:00:00'),
(44, 2, 'Notification 38', 'This is the 38th notification', '2024-07-06 06:20:00'),
(45, 2, 'Notification 39', 'This is the 39th notification', '2024-07-06 06:40:00'),
(46, 2, 'Notification 40', 'This is the 40th notification', '2024-07-06 07:00:00'),
(47, 2, 'Notification 41', 'This is the 41st notification', '2024-07-06 07:20:00'),
(48, 2, 'Notification 42', 'This is the 42nd notification', '2024-07-06 07:40:00'),
(49, 2, 'Notification 43', 'This is the 43rd notification', '2024-07-06 08:00:00'),
(50, 2, 'Notification 44', 'This is the 44th notification', '2024-07-06 08:20:00'),
(51, 2, 'Notification 45', 'This is the 45th notification', '2024-07-06 08:40:00'),
(52, 2, 'Notification 46', 'This is the 46th notification', '2024-07-06 09:00:00'),
(53, 2, 'Notification 47', 'This is the 47th notification', '2024-07-06 09:20:00'),
(54, 2, 'Notification 48', 'This is the 48th notification', '2024-07-06 09:40:00'),
(55, 2, 'Notification 49', 'This is the 49th notification', '2024-07-06 10:00:00'),
(56, 2, 'Notification 50', 'This is the 50th notification', '2024-07-06 10:20:00'),
(57, 2, 'Notification 51', 'This is the 51st notification', '2024-07-06 10:40:00');

-- --------------------------------------------------------

--
-- Table structure for table `passenger`
--

CREATE TABLE `passenger` (
  `user_id` int(11) NOT NULL,
  `wallet_id` int(11) DEFAULT NULL,
  `loyalty` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `cellphone` varchar(20) DEFAULT NULL,
  `total_paid` double DEFAULT NULL,
  `pfp` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `passenger`
--

INSERT INTO `passenger` (`user_id`, `wallet_id`, `loyalty`, `email`, `password`, `name`, `cellphone`, `total_paid`, `pfp`) VALUES
(2, NULL, NULL, 'john.doe@example.com', 'password123', 'John Doe', '1234567890', 1500, 'john_pfp.png');

-- --------------------------------------------------------

--
-- Table structure for table `reschedule_request`
--

CREATE TABLE `reschedule_request` (
  `reschedule_id` int(11) NOT NULL,
  `transaction_id` int(11) DEFAULT NULL,
  `admin_id` int(11) NOT NULL,
  `status` enum('SUCCESS','DENIED','PENDING') DEFAULT NULL
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
(1, 1, 1, 2, '2024-07-10', 50),
(2, 2, 1, 3, '2024-07-11', 55),
(3, 3, 1, 4, '2024-07-12', 60),
(4, 4, 2, 1, '2024-07-10', 45),
(5, 5, 2, 3, '2024-07-11', 55),
(6, 6, 2, 4, '2024-07-12', 65),
(7, 7, 3, 1, '2024-07-10', 55),
(8, 8, 3, 2, '2024-07-11', 50),
(9, 9, 3, 4, '2024-07-12', 70),
(10, 10, 4, 1, '2024-07-10', 60),
(11, 11, 4, 2, '2024-07-11', 65),
(12, 12, 4, 3, '2024-07-12', 75),
(13, 13, 5, 1, '2024-07-10', 70),
(14, 14, 5, 2, '2024-07-11', 75),
(15, 15, 5, 3, '2024-07-12', 80);

-- --------------------------------------------------------

--
-- Table structure for table `station`
--

CREATE TABLE `station` (
  `station_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `income` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `station`
--

INSERT INTO `station` (`station_id`, `name`, `location`, `income`) VALUES
(1, 'Bandung Station', 'Bandung, West Java', 50000),
(2, 'Bekasi Station', 'Bekasi, West Java', 45000),
(3, 'Bogor Station', 'Bogor, West Java', 47000),
(4, 'Cirebon Station', 'Cirebon, West Java', 48000),
(5, 'Depok Station', 'Depok, West Java', 46000);

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

-- --------------------------------------------------------

--
-- Table structure for table `ticket_transaction`
--

CREATE TABLE `ticket_transaction` (
  `transaction_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `schedule_id` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `passengers` int(11) DEFAULT NULL,
  `commute` tinyint(1) DEFAULT NULL,
  `rescheduled` tinyint(1) DEFAULT NULL
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
  `victual_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `user_id` int(11) DEFAULT NULL,
  `station_id` int(11) NOT NULL,
  `date` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

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
(1, 2, 8000, 0);

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
  ADD KEY `wallet_id` (`wallet_id`),
  ADD KEY `loyalty` (`loyalty`);

--
-- Indexes for table `reschedule_request`
--
ALTER TABLE `reschedule_request`
  ADD PRIMARY KEY (`reschedule_id`),
  ADD KEY `transaction_id` (`transaction_id`),
  ADD KEY `fk_admin` (`admin_id`);

--
-- Indexes for table `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`schedule_id`),
  ADD KEY `train_id` (`train_id`),
  ADD KEY `departure_station_id` (`departure_station_id`),
  ADD KEY `arrival_station_id` (`arrival_station_id`);

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
-- AUTO_INCREMENT for table `cart_item`
--
ALTER TABLE `cart_item`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `loyalty`
--
ALTER TABLE `loyalty`
  MODIFY `loyalty_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `notification_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT for table `passenger`
--
ALTER TABLE `passenger`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `schedule`
--
ALTER TABLE `schedule`
  MODIFY `schedule_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `station`
--
ALTER TABLE `station`
  MODIFY `station_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `stock`
--
ALTER TABLE `stock`
  MODIFY `stock_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `transaction_item`
--
ALTER TABLE `transaction_item`
  MODIFY `transaction_item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `victual`
--
ALTER TABLE `victual`
  MODIFY `victual_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `victuals_transaction`
--
ALTER TABLE `victuals_transaction`
  MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

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
  ADD CONSTRAINT `passenger_ibfk_1` FOREIGN KEY (`wallet_id`) REFERENCES `wallet` (`wallet_id`),
  ADD CONSTRAINT `passenger_ibfk_2` FOREIGN KEY (`loyalty`) REFERENCES `loyalty` (`loyalty_id`);

--
-- Constraints for table `reschedule_request`
--
ALTER TABLE `reschedule_request`
  ADD CONSTRAINT `fk_admin` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`user_id`),
  ADD CONSTRAINT `reschedule_request_ibfk_1` FOREIGN KEY (`transaction_id`) REFERENCES `ticket_transaction` (`transaction_id`);

--
-- Constraints for table `schedule`
--
ALTER TABLE `schedule`
  ADD CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`train_id`) REFERENCES `train` (`train_id`),
  ADD CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`departure_station_id`) REFERENCES `station` (`station_id`),
  ADD CONSTRAINT `schedule_ibfk_3` FOREIGN KEY (`arrival_station_id`) REFERENCES `station` (`station_id`);

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
