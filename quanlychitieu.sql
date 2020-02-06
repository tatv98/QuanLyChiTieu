-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th1 09, 2020 lúc 09:05 AM
-- Phiên bản máy phục vụ: 10.4.8-MariaDB
-- Phiên bản PHP: 7.1.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quanlychitieu`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_expense`
--

CREATE TABLE `tbl_expense` (
  `expense_id` int(11) NOT NULL,
  `expense_title` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `expense_content` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `expense_date` date DEFAULT NULL,
  `expense_amount` int(11) DEFAULT NULL,
  `user_name` text COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_expense`
--

INSERT INTO `tbl_expense` (`expense_id`, `expense_title`, `expense_content`, `expense_date`, `expense_amount`, `user_name`) VALUES
(1, 'Ăn uống', 'ăn trưa', '2019-12-20', 25000, 'admin'),
(2, 'Đi lại', 'đi học', '2019-12-20', 20000, 'admin'),
(3, 'Đi lại', 'đi chơi', '2019-12-22', 30000, 'admin');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_income`
--

CREATE TABLE `tbl_income` (
  `income_id` int(11) NOT NULL,
  `income_title` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `income_content` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `income_date` date DEFAULT NULL,
  `income_amount` int(11) DEFAULT NULL,
  `user_name` text COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_income`
--

INSERT INTO `tbl_income` (`income_id`, `income_title`, `income_content`, `income_date`, `income_amount`, `user_name`) VALUES
(1, 'Tiền lương', 'tháng 11', '2019-11-01', 5000000, 'admin'),
(3, 'Tiền thưởng', 'thưởng tháng 12', '2019-12-01', 1000000, 'admin'),
(4, 'Tiền lương', 'tháng 11', '2019-11-01', 5000000, 'admin'),
(5, 'Tiền lương', 'tháng 12', '2019-12-01', 5000000, 'admin'),
(6, 'Tiền thưởng', 'thưởng tháng 12', '2019-12-01', 1000000, 'admin'),
(7, 'Tiền lương', 'thưởng tháng 1', '2020-01-01', 5000000, 'admin'),
(10, 'Lương', 'aahhh2', '2020-01-09', 677, 'admin');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_user`
--

CREATE TABLE `tbl_user` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `user_pass` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `user_age` int(11) NOT NULL,
  `user_gender` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_user`
--

INSERT INTO `tbl_user` (`user_id`, `user_name`, `user_pass`, `user_age`, `user_gender`) VALUES
(1, 'admin', 'admin', 22, 'Nam'),
(2, 'locklinh', 'lio', 23, 'Nữ'),
(7, 'lio', '1234', 20, 'Nữ'),
(8, 'a', 'a', 1, 'Nam'),
(9, 'b', 'b', 1, 'Nam'),
(10, 'c', 'c', 12, 'Nam');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `tbl_expense`
--
ALTER TABLE `tbl_expense`
  ADD PRIMARY KEY (`expense_id`);

--
-- Chỉ mục cho bảng `tbl_income`
--
ALTER TABLE `tbl_income`
  ADD PRIMARY KEY (`income_id`);

--
-- Chỉ mục cho bảng `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `tbl_expense`
--
ALTER TABLE `tbl_expense`
  MODIFY `expense_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `tbl_income`
--
ALTER TABLE `tbl_income`
  MODIFY `income_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
