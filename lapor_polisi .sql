-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: 22 Sep 2017 pada 09.02
-- Versi Server: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lapor_polisi`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `kasustb`
--

CREATE TABLE `kasustb` (
  `id` int(11) NOT NULL,
  `kd_kriteria` varchar(50) DEFAULT NULL,
  `kategori` varchar(225) DEFAULT NULL,
  `kd_polsek` varchar(225) DEFAULT NULL,
  `tanggal` varchar(25) DEFAULT NULL,
  `judul` varchar(225) DEFAULT NULL,
  `keterangan` longtext,
  `lokasi` varchar(225) DEFAULT NULL,
  `foto` varchar(225) DEFAULT NULL,
  `status` varchar(225) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `kasustb`
--

INSERT INTO `kasustb` (`id`, `kd_kriteria`, `kategori`, `kd_polsek`, `tanggal`, `judul`, `keterangan`, `lokasi`, `foto`, `status`) VALUES
(1, 'MK001', 'KK003', 'KP003', '19/09/2017', 'Pengeboman rumah warga', '-', 'Jl kenangan', '-', 'Aksi 1'),
(2, NULL, 'pembunuhan', NULL, '12/04/2017', 'judul', 'Tester', 'jl pattimura', 'http://10.42.0.1/polisi/images/8bqtd2d7z7r8izxd7mxf.png', 'Proses'),
(3, NULL, 'Pengeboman', NULL, '17/09/2018', 'judul 2', 'Tester', 'Jl Jawa', 'http://10.42.0.1/polisi/images/4uj2sawkfqbqf61ttgpi.png', 'Proses'),
(4, NULL, '', NULL, '', 'judul2an', '', '', 'http://10.42.0.1/polisi/images/b9u3sb0qjn5ba1vpg7w9.png', 'Proses'),
(5, NULL, 'iseng aja', NULL, '', 'judul2an', '', '', 'http://10.42.0.1/polisi/images/td6eu8kfpgdgb603cngk.png', 'Proses'),
(6, NULL, 'yossah', NULL, '', '', '', '', 'http://10.42.0.1/polisi/images/fb8n8h877099rn0jdd9a.png', 'Proses'),
(7, NULL, 'Pencurian Mouse Laptop', NULL, '', '', '', '', 'http://10.42.0.1/polisi/images/vsurh9ekxvm9qiiqjvay.png', 'Proses'),
(8, NULL, 'jkk', NULL, '29', 'judil', '', 'kk', 'http://10.42.0.1/polisi/images/ttwu4gxw2c6icmt4p6zx.png', 'Penyelidikan'),
(9, NULL, 'hhj', NULL, 'yuu', 'jh', '', 'hjn', 'http://10.42.0.1/polisi/images/zbqfprf1442e5d4j529m.png', 'Proses'),
(10, NULL, 'nnn', NULL, 'hjmmm', 'hilang', '', 'hb', 'http://10.42.0.1/polisi/images/rv1rg6sz95vq9x2v9jjh.png', 'Proses'),
(11, NULL, 'hilang', NULL, '', 'hilang', 'Nnnn', '', 'http://10.42.0.1/polisi/images/thzyqd7gk65whama4nn0.png', 'Proses'),
(12, NULL, '', NULL, 'tt', 'tt', 'Hh', '', 'http://10.42.0.1/polisi/images/4db0tcis5y5x5h0ethn9.png', 'Proses'),
(13, NULL, 'ttt', NULL, '', ']', '', 'hh', 'http://10.42.0.1/polisi/images/i9tm1hbp0jgfcnvspd1f.png', 'Proses'),
(14, NULL, 'cuc', NULL, '', 'guggu', '', '', 'http://10.42.0.1/polisi/images/08vksxqss66d1sf98j13.png', 'Penyelidikan'),
(15, NULL, 'dbdndb', NULL, '', 'rjrjr', '', '', 'http://10.42.0.1/polisi/images/r3ii773x6uk86di7z9c5.png', 'Proses');

-- --------------------------------------------------------

--
-- Struktur dari tabel `kategoritb`
--

CREATE TABLE `kategoritb` (
  `id` int(11) NOT NULL,
  `kd_kategori` varchar(50) NOT NULL,
  `nama_kategori` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `kategoritb`
--

INSERT INTO `kategoritb` (`id`, `kd_kategori`, `nama_kategori`) VALUES
(1, 'KK001', 'Pembunuhan'),
(2, 'KK002', 'Pencurian'),
(3, 'KK003', 'Pengeboman'),
(4, 'KK005', 'Pembegalan');

-- --------------------------------------------------------

--
-- Struktur dari tabel `kriteriatb`
--

CREATE TABLE `kriteriatb` (
  `id` int(11) NOT NULL,
  `kd_kriteria` varchar(25) NOT NULL,
  `nama_kriteria` varchar(225) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `kriteriatb`
--

INSERT INTO `kriteriatb` (`id`, `kd_kriteria`, `nama_kriteria`) VALUES
(1, 'MK001', 'Penanganan Kasus'),
(2, 'MK002', 'Ketertiban Polsek'),
(3, 'MK003', 'Prestasi Polsek'),
(4, 'MK004', 'Sosialisasi Kemasyarakatan'),
(5, 'MK005', 'Kamtibnas');

-- --------------------------------------------------------

--
-- Struktur dari tabel `polsektb`
--

CREATE TABLE `polsektb` (
  `id` int(11) NOT NULL,
  `kd_polsek` varchar(50) DEFAULT NULL,
  `kantor` varchar(50) DEFAULT NULL,
  `alamat` longtext,
  `foto` varchar(100) DEFAULT NULL,
  `no_hp` int(14) DEFAULT NULL,
  `email` mediumtext
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `polsektb`
--

INSERT INTO `polsektb` (`id`, `kd_polsek`, `kantor`, `alamat`, `foto`, `no_hp`, `email`) VALUES
(1, 'KP001', 'Polsek Sumbersari', 'Jl. null', 'null', 0, 'null'),
(2, 'KP002', 'Polsek Tanggul', NULL, NULL, NULL, NULL),
(3, 'KP003', 'Polsek Ambulu', NULL, NULL, NULL, NULL),
(4, 'KP004', 'Polsek Kencong', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `userlogin`
--

CREATE TABLE `userlogin` (
  `id` int(11) NOT NULL,
  `kd_polsek` varchar(225) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `level` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `userlogin`
--

INSERT INTO `userlogin` (`id`, `kd_polsek`, `username`, `password`, `level`) VALUES
(1, 'KP001', 'admin', 'password', 'admin'),
(2, 'KP002', 'polsek', 'password', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kasustb`
--
ALTER TABLE `kasustb`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kategoritb`
--
ALTER TABLE `kategoritb`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kriteriatb`
--
ALTER TABLE `kriteriatb`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `polsektb`
--
ALTER TABLE `polsektb`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `userlogin`
--
ALTER TABLE `userlogin`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kasustb`
--
ALTER TABLE `kasustb`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `kategoritb`
--
ALTER TABLE `kategoritb`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `kriteriatb`
--
ALTER TABLE `kriteriatb`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `polsektb`
--
ALTER TABLE `polsektb`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `userlogin`
--
ALTER TABLE `userlogin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
