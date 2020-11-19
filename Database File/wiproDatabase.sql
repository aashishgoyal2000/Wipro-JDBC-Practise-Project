-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 19, 2020 at 07:29 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `wipro`
--

-- --------------------------------------------------------

--
-- Table structure for table `seq_product_id`
--

CREATE TABLE `seq_product_id` (
  `next_not_cached_value` bigint(21) NOT NULL,
  `minimum_value` bigint(21) NOT NULL,
  `maximum_value` bigint(21) NOT NULL,
  `start_value` bigint(21) NOT NULL COMMENT 'start value when sequences is created or value if RESTART is used',
  `increment` bigint(21) NOT NULL COMMENT 'increment value',
  `cache_size` bigint(21) UNSIGNED NOT NULL,
  `cycle_option` tinyint(1) UNSIGNED NOT NULL COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
  `cycle_count` bigint(21) NOT NULL COMMENT 'How many cycles have been done'
) ENGINE=InnoDB;

--
-- Dumping data for table `seq_product_id`
--

INSERT INTO `seq_product_id` (`next_not_cached_value`, `minimum_value`, `maximum_value`, `start_value`, `increment`, `cache_size`, `cycle_option`, `cycle_count`) VALUES
(2004, 1004, 2004, 1004, 1, 1000, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `seq_sales_id`
--

CREATE TABLE `seq_sales_id` (
  `next_not_cached_value` bigint(21) NOT NULL,
  `minimum_value` bigint(21) NOT NULL,
  `maximum_value` bigint(21) NOT NULL,
  `start_value` bigint(21) NOT NULL COMMENT 'start value when sequences is created or value if RESTART is used',
  `increment` bigint(21) NOT NULL COMMENT 'increment value',
  `cache_size` bigint(21) UNSIGNED NOT NULL,
  `cycle_option` tinyint(1) UNSIGNED NOT NULL COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
  `cycle_count` bigint(21) NOT NULL COMMENT 'How many cycles have been done'
) ENGINE=InnoDB;

--
-- Dumping data for table `seq_sales_id`
--

INSERT INTO `seq_sales_id` (`next_not_cached_value`, `minimum_value`, `maximum_value`, `start_value`, `increment`, `cache_size`, `cycle_option`, `cycle_count`) VALUES
(1000, 1000, 2000, 1000, 1, 1000, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_sales`
--

CREATE TABLE `tbl_sales` (
  `SALES_ID` varchar(6) NOT NULL,
  `SALES_DATE` date NOT NULL,
  `PRODUCT_ID` varchar(6) NOT NULL,
  `QUANTITY_SOLD` int(11) NOT NULL,
  `SALES_PRICE_PER_UNIT` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_stock`
--

CREATE TABLE `tbl_stock` (
  `PRODUCT_ID` varchar(6) NOT NULL,
  `PRODUCT_NAME` varchar(20) NOT NULL,
  `QUANTITY_ON_HAND` int(11) NOT NULL,
  `PRODUCT_UNIT_PRICE` int(11) NOT NULL,
  `REORDER_LEVEL` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_stock`
--

INSERT INTO `tbl_stock` (`PRODUCT_ID`, `PRODUCT_NAME`, `QUANTITY_ON_HAND`, `PRODUCT_UNIT_PRICE`, `REORDER_LEVEL`) VALUES
('', 'redmi', 12, 12222, 12),
('1233', 'mughalel', 12, 122121, 12),
('IP1002', 'Iphone 5S', 10, 21000, 2),
('PA1003', 'Panasonic P55', 50, 5500, 5),
('RE1001', 'REDMI NOTE 3', 20, 12000, 5);

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_sales_report`
-- (See below for the actual view)
--
CREATE TABLE `v_sales_report` (
`SALES_ID` varchar(6)
,`SALES_DATE` date
,`PRODUCT_ID` varchar(6)
,`PRODUCT_NAME` varchar(20)
,`QUANTITY_ON_HAND` int(11)
,`PRODUCT_UNIT_PRICE` int(11)
,`SALES_PRICE_PER_UNIT` int(11)
,`profit_amount` bigint(12)
);

-- --------------------------------------------------------

--
-- Structure for view `v_sales_report`
--
DROP TABLE IF EXISTS `v_sales_report`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_sales_report`  AS SELECT `tbl_sales`.`SALES_ID` AS `SALES_ID`, `tbl_sales`.`SALES_DATE` AS `SALES_DATE`, `tbl_stock`.`PRODUCT_ID` AS `PRODUCT_ID`, `tbl_stock`.`PRODUCT_NAME` AS `PRODUCT_NAME`, `tbl_stock`.`QUANTITY_ON_HAND` AS `QUANTITY_ON_HAND`, `tbl_stock`.`PRODUCT_UNIT_PRICE` AS `PRODUCT_UNIT_PRICE`, `tbl_sales`.`SALES_PRICE_PER_UNIT` AS `SALES_PRICE_PER_UNIT`, `tbl_sales`.`SALES_PRICE_PER_UNIT`- `tbl_stock`.`PRODUCT_UNIT_PRICE` AS `profit_amount` FROM (`tbl_stock` join `tbl_sales`) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_sales`
--
ALTER TABLE `tbl_sales`
  ADD PRIMARY KEY (`SALES_ID`),
  ADD KEY `PRODUCT_ID` (`PRODUCT_ID`);

--
-- Indexes for table `tbl_stock`
--
ALTER TABLE `tbl_stock`
  ADD PRIMARY KEY (`PRODUCT_ID`),
  ADD UNIQUE KEY `PRODUCT_NAME` (`PRODUCT_NAME`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_sales`
--
ALTER TABLE `tbl_sales`
  ADD CONSTRAINT `tbl_sales_ibfk_1` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `tbl_stock` (`PRODUCT_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
