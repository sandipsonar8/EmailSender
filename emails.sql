-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 17, 2024 at 01:06 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `emails`
--

-- --------------------------------------------------------

--
-- Table structure for table `cctable`
--

CREATE TABLE `cctable` (
  `CCID` int(11) NOT NULL,
  `Email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Table structure for table `fromtable`
--

CREATE TABLE `fromtable` (
  `FromID` int(11) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `pass` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `totable`
--

CREATE TABLE `totable` (
  `ToID` int(11) NOT NULL,
  `Email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `totable`
--

-- Indexes for dumped tables
--

--
-- Indexes for table `cctable`
--
ALTER TABLE `cctable`
  ADD PRIMARY KEY (`CCID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- Indexes for table `fromtable`
--
ALTER TABLE `fromtable`
  ADD PRIMARY KEY (`FromID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- Indexes for table `totable`
--
ALTER TABLE `totable`
  ADD PRIMARY KEY (`ToID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cctable`
--
ALTER TABLE `cctable`
  MODIFY `CCID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `fromtable`
--
ALTER TABLE `fromtable`
  MODIFY `FromID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `totable`
--
ALTER TABLE `totable`
  MODIFY `ToID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
