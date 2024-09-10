-- MySQL dump 10.13  Distrib 8.4.2, for Win64 (x86_64)
--
-- Host: localhost    Database: qltv
-- ------------------------------------------------------
-- Server version	8.4.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chucnang`
--

DROP TABLE IF EXISTS `chucnang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chucnang` (
  `MA_CNANG` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TEN_CNANG` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`MA_CNANG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chucnang`
--

/*!40000 ALTER TABLE `chucnang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ct_pmuon`
--

DROP TABLE IF EXISTS `ct_pmuon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ct_pmuon` (
  `MA_PMUON` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MA_SACH` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SO_LUONG` int DEFAULT NULL,
  `TRANG_THAI` int DEFAULT NULL,
  KEY `MA_PMUON` (`MA_PMUON`),
  KEY `MA_SACH` (`MA_SACH`),
  CONSTRAINT `ct_pmuon_ibfk_1` FOREIGN KEY (`MA_PMUON`) REFERENCES `phieu_muon` (`MA_PMUON`),
  CONSTRAINT `ct_pmuon_ibfk_2` FOREIGN KEY (`MA_SACH`) REFERENCES `sach` (`MA_SACH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ct_pmuon`
--

LOCK TABLES `ct_pmuon` WRITE;
/*!40000 ALTER TABLE `ct_pmuon` DISABLE KEYS */;
INSERT INTO `ct_pmuon` VALUES ('78046','0006',1,1),('45385','0014',1,1),('18149','0005',1,1),('93542','0006',2,1);
/*!40000 ALTER TABLE `ct_pmuon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ct_pnhap`
--

DROP TABLE IF EXISTS `ct_pnhap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ct_pnhap` (
  `MA_PNHAP` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MA_SACH` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SO_LUONG` int DEFAULT NULL,
  `THANH_TIEN` int DEFAULT NULL,
  KEY `MA_PNHAP` (`MA_PNHAP`),
  KEY `MA_SACH` (`MA_SACH`),
  CONSTRAINT `ct_pnhap_ibfk_1` FOREIGN KEY (`MA_PNHAP`) REFERENCES `phieu_nhap` (`MA_PNHAP`),
  CONSTRAINT `ct_pnhap_ibfk_2` FOREIGN KEY (`MA_SACH`) REFERENCES `sach` (`MA_SACH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ct_pnhap`
--

/*!40000 ALTER TABLE `ct_pnhap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ct_ptra`
--

DROP TABLE IF EXISTS `ct_ptra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ct_ptra` (
  `MA_PTRA` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MA_SACH` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SO_LUONG` int DEFAULT NULL,
  `TRANG_THAI` int DEFAULT NULL,
  KEY `MA_PTRA` (`MA_PTRA`),
  KEY `MA_SACH` (`MA_SACH`),
  CONSTRAINT `ct_ptra_ibfk_1` FOREIGN KEY (`MA_PTRA`) REFERENCES `phieu_tra` (`MA_PTRA`),
  CONSTRAINT `ct_ptra_ibfk_2` FOREIGN KEY (`MA_SACH`) REFERENCES `sach` (`MA_SACH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ct_ptra`
--

LOCK TABLES `ct_ptra` WRITE;
/*!40000 ALTER TABLE `ct_ptra` DISABLE KEYS */;
LOCK TABLES `ctiet_cnang` WRITE;
/*!40000 ALTER TABLE `ctiet_cnang` DISABLE KEYS */;
INSERT INTO `ctiet_cnang` VALUES ('0001','Them Nhan Vien',NULL,'0001'),('0002','Sua Thong Tin Nhan Vien',NULL,'0001'),('0003','Xoa Thong Tin Nhan Vien',NULL,'0001'),('0004','Tim Kiem Nhan Vien',NULL,'0001'),('0005','Sua Thong Tin Cuon Sach',NULL,'0002'),('0006','Xoa Thong Tin Cuon Sach',NULL,'0002'),('0007','Tim Kiem Sach',NULL,'0002'),('0008','Them Doc Gia',NULL,'0003'),('0009','Xoa Thong Tin Doc Gia',NULL,'0003'),('0010','Sua Thong Tin Doc Gia',NULL,'0003'),('0011','Tim Kiem Thong Tin Doc Gia',NULL,'0003'),('0012','Tao Phieu Muon',NULL,'0004'),('0013','Sua Phieu Muon',NULL,'0004'),('0014','Xoa Phieu Muon',NULL,'0004'),('0015','Tim Kiem Phieu Muon',NULL,'0004'),('0016','Tao Phieu Phat',NULL,'0005'),('0017','Sua Phieu Phat',NULL,'0005'),('0018','Xoa Phieu Phat',NULL,'0005'),('0019','Tim Kiem Phieu Phat',NULL,'0005'),('0020','Tao Phieu Tra',NULL,'0006'),('0021','Xoa Phieu Tra',NULL,'0006'),('0022','Sua Phieu Tra',NULL,'0006'),('0023','Tim Kiem Phieu Tra',NULL,'0006'),('0024','Tao Phieu Nhap',NULL,'0007'),('0025','Xoa Phieu Nhap',NULL,'0007'),('0026','Sua Phieu Nhap',NULL,'0007'),('0027','Tim Kiem Phieu Nhap',NULL,'0007'),('0028','Them Tac Gia',NULL,'0008'),('0029','Xoa Thong Tin Tac Gia',NULL,'0008'),('0030','Sua Thong Tin Tac Gia',NULL,'0008'),('0031','Tim Kiem Tac Gia',NULL,'0008'),('0032','Them Nha Xuat Ban',NULL,'0009'),('0033','Xoa Thong Tin Nha Xuat Ban',NULL,'0009'),('0034','Sua Thong Tin Nha Xuat Ban',NULL,'0009'),('0035','Tim Kiem Nha Xuat Ban',NULL,'0009');
/*!40000 ALTER TABLE `ctiet_cnang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doc_gia`
--

DROP TABLE IF EXISTS `doc_gia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doc_gia` (
  `MA_DG` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `HO_TEN` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDT` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TRANG_TAI` tinyint DEFAULT NULL,
  PRIMARY KEY (`MA_DG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doc_gia`
--

LOCK TABLES `doc_gia` WRITE;
/*!40000 ALTER TABLE `doc_gia` DISABLE KEYS */;
INSERT INTO `doc_gia` VALUES ('0001','Anh Mai','0923456736','anhmai@gmail.com',1),('0002','Phuong Yen','0923456730','phuongyen@gmail.com',1),('0003','Thai Minh','0923456735','thaiminh@gmail.com',1),('0004','Nguyen Quan','0923456738','ngyenquan@gmail.com',1),('0005','Nguyen Quan','0923456739','ngyenquan@gmail.com',1);
LOCK TABLES `loai_sach` WRITE;
/*!40000 ALTER TABLE `loai_sach` DISABLE KEYS */;
INSERT INTO `loai_sach` VALUES ('0001','Tiểu Thuyết',1),('0002','Tâm Lý',1),('0003','Truyện Tranh',1),('0004','Cổ Tích',1),('0005','Tâm Linh',1);
/*!40000 ALTER TABLE `loai_sach` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nha_xuat_ban`
--

DROP TABLE IF EXISTS `nha_xuat_ban`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nha_xuat_ban` (
  `MA_NXB` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TEN_NXB` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TRANG_THAI` tinyint DEFAULT NULL,
  PRIMARY KEY (`MA_NXB`),
  CONSTRAINT `TonTai` CHECK ((`TRANG_THAI` in (0,1)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nha_xuat_ban`
--

/*!40000 ALTER TABLE `nha_xuat_ban` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhan_vien`
--

DROP TABLE IF EXISTS `nhan_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhan_vien` (
  `MA_NV` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `HO_TEN` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDT` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TRANG_THAI` tinyint DEFAULT NULL,
  PRIMARY KEY (`MA_NV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhan_vien`
--

/*!40000 ALTER TABLE `nhan_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieu_muon`
--

DROP TABLE IF EXISTS `phieu_muon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieu_muon` (
  `MA_PMUON` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `NG_MUON` date DEFAULT NULL,
  `MA_NV` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MA_DG` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `NG_TRA` date DEFAULT NULL,
  `SO_LUONG` int DEFAULT NULL,
  `TRANG_THAI` tinyint DEFAULT NULL,
  PRIMARY KEY (`MA_PMUON`),
  KEY `MA_DG` (`MA_DG`),
  KEY `MA_NV` (`MA_NV`),
  CONSTRAINT `phieu_muon_ibfk_1` FOREIGN KEY (`MA_DG`) REFERENCES `doc_gia` (`MA_DG`),
  CONSTRAINT `phieu_muon_ibfk_2` FOREIGN KEY (`MA_NV`) REFERENCES `nhan_vien` (`MA_NV`),
  CONSTRAINT `TonTai8` CHECK ((`TRANG_THAI` in (0,1)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieu_muon`
--

/*!40000 ALTER TABLE `phieu_muon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieu_nhap`
--

DROP TABLE IF EXISTS `phieu_nhap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieu_nhap` (
  `MA_PNHAP` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `NG_NHAP` date DEFAULT NULL,
  `MA_NXB` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MA_NV` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TONG_TIEN` float DEFAULT NULL,
  `TRANG_THAI` tinyint DEFAULT NULL,
  PRIMARY KEY (`MA_PNHAP`),
  KEY `MA_NXB` (`MA_NXB`),
  KEY `MA_NV` (`MA_NV`),
  CONSTRAINT `phieu_nhap_ibfk_1` FOREIGN KEY (`MA_NXB`) REFERENCES `nha_xuat_ban` (`MA_NXB`),
  CONSTRAINT `phieu_nhap_ibfk_2` FOREIGN KEY (`MA_NV`) REFERENCES `nhan_vien` (`MA_NV`),
  CONSTRAINT `TonTai7` CHECK ((`TRANG_THAI` in (0,1)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieu_nhap`
--

LOCK TABLES `phieu_nhap` WRITE;
/*!40000 ALTER TABLE `phieu_nhap` DISABLE KEYS */;
INSERT INTO `phieu_nhap` VALUES ('00001','2024-03-23','0001','0001',400000,1),('00002','2024-03-22','0001','0002',400000,1);
LOCK TABLES `phieu_phat` WRITE;
/*!40000 ALTER TABLE `phieu_phat` DISABLE KEYS */;
/*!40000 ALTER TABLE `phieu_phat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieu_tra`
--

DROP TABLE IF EXISTS `phieu_tra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieu_tra` (
  `MA_PTRA` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `MA_PMUON` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MA_DG` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MA_NV` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `NG_TRA` date DEFAULT NULL,
  `TINH_TRANG` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `SO_LUONG` int DEFAULT NULL,
  `TRANG_THAI` tinyint DEFAULT NULL,
  PRIMARY KEY (`MA_PTRA`),
  KEY `MA_PMUON` (`MA_PMUON`),
  KEY `MA_DG` (`MA_DG`),
  KEY `MA_NV` (`MA_NV`),
  CONSTRAINT `phieu_tra_ibfk_1` FOREIGN KEY (`MA_PMUON`) REFERENCES `phieu_muon` (`MA_PMUON`),
  CONSTRAINT `phieu_tra_ibfk_2` FOREIGN KEY (`MA_DG`) REFERENCES `doc_gia` (`MA_DG`),
  CONSTRAINT `phieu_tra_ibfk_3` FOREIGN KEY (`MA_NV`) REFERENCES `nhan_vien` (`MA_NV`),
  CONSTRAINT `TonTai9` CHECK ((`TRANG_THAI` in (0,1)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieu_tra`
--

/*!40000 ALTER TABLE `phieu_tra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quyen`
--

DROP TABLE IF EXISTS `quyen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quyen` (
  `MA_QUYEN` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TEN_QUYEN` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`MA_QUYEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quyen`
--

LOCK TABLES `quyen` WRITE;
/*!40000 ALTER TABLE `quyen` DISABLE KEYS */;
INSERT INTO `quyen` VALUES ('0001','Quan Li'),('0002','Nhan Vien');
LOCK TABLES `sach` WRITE;
/*!40000 ALTER TABLE `sach` DISABLE KEYS */;
INSERT INTO `sach` VALUES ('0001','Ma Đạo Tổ Sư',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0001','0001',50000,1,1,'0001'),('0002','Thiên Y Truyền Kì',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0001','0001',50000,4,1,'0001'),('0003','Nghiêu Luyện Thần Quái',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0001','0001',50000,3,1,'0001'),('0004','Tiên Nghịch',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0002','0001',50000,4,1,'0001'),('0005','Một Lời Nói',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0002','0001',50000,4,1,'0001'),('0006','Tội Phạm',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0002','0001',50000,3,1,'0002'),('0007','Thám Tử Tội Phạm',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0003','0001',50000,4,1,'0002'),('0008','Thủ Đọan',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0003','0001',50000,4,1,'0002'),('0009','Trò Chơi Số',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0003','0001',50000,4,1,'0002'),('0010','Liêu Trai Chí Di',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0004','0001',50000,4,1,'0002'),('0011','Tây Du Kí',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0004','0001',50000,4,1,'0003'),('0012','Bạch Phát Ma Nữ',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0004','0001',50000,4,1,'0003'),('0013','Cửu Tình Kì Án',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0005','0001',50000,4,1,'0003'),('0014','Nguyệt Thương Trọng Hoa',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0005','0001',50000,3,1,'0003'),('0015','Bến Thượng Hải',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0005','0002',50000,4,1,'0003'),('0016','Dinh Thiên Tai',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0006','0002',50000,4,1,'0004'),('0017','Bất Tận Đường Đời',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0006','0002',50000,4,1,'0004'),('0018','Hoa Băng Vô Hình',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0006','0002',50000,4,1,'0004'),('0019','Dị Thường Trọng Sinh',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0007','0002',50000,4,1,'0004'),('0020','Hoàng Hậu Ki',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0007','0002',50000,4,1,'0004'),('0021','Thái Cực Quyền',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0007','0002',50000,4,1,'0005'),('0022','Trường An Phủ',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0008','0002',50000,4,1,'0005'),('0023','Hồng Lâu Mộng',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0008','0002',50000,4,1,'0005'),('0024','Cạnh Lâu Mộng',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0008','0002',50000,4,1,'0005'),('0025','Truy Tìm Sự Thật',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0009','0002',50000,4,1,'0005'),('0026','Sóng',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0009','0002',50000,4,1,'0001'),('0027','Tuyên Sơn Phi Hổ',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0009','0002',50000,4,1,'0002'),('0028','Long Phụng Sum Vầy',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0010','0002',50000,4,1,'0003'),('0029','Thủy Hử',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0010','0003',50000,4,1,'0004'),('0030','Đá Phách Bao Điền',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0014','0003',50000,4,1,'0002'),('0031','Vô Thường Chấp Niệm',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0010','0003',50000,4,1,'0005'),('0032','Nhất Luyện Nhi Niệm',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0011','0003',50000,4,1,'0001'),('0033','Hoán Đổi',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0011','0003',50000,4,1,'0002'),('0034','Yêu Không Lối Thóat',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0011','0003',50000,4,1,'0003'),('0035','Cho Dến Khi Nào Hoa Nở',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0012','0003',50000,4,1,'0004'),('0036','Mãi Mãi Là Bao Xa',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0012','0003',50000,4,1,'0005'),('0037','Vòng Xoáy Tình Tiền',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0012','0003',50000,4,1,'0001'),('0038','Kim Bình Mai',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0013','0003',50000,4,1,'0002'),('0039','Hồng Trần Lạc Đạo',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0013','0003',50000,4,1,'0003'),('0040','Tam Quốc Chí',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0013','0003',50000,4,1,'0004'),('0041','Hoài Ngọc Truyền Kì',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0014','0003',50000,4,1,'0005'),('0042','Thần Điêu Đại Hiệp',' These stories blend mystery and suspense, offering readers a thrilling journey into the unknown realms of spirituality and the afterlife.','2020','0014','0003',50000,4,0,'0001');
/*!40000 ALTER TABLE `sach` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tac_gia`
--

DROP TABLE IF EXISTS `tac_gia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tac_gia` (
  `MA_TGIA` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TEN_TGIA` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `GIOI_TINH` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `NAM_SINH` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TRANG_THAI` tinyint DEFAULT NULL,
  PRIMARY KEY (`MA_TGIA`),
  CONSTRAINT `GioiTinh` CHECK ((`GIOI_TINH` in (_utf8mb4'Nữ',_utf8mb4'Nam'))),
  CONSTRAINT `TonTai1` CHECK ((`TRANG_THAI` in (0,1)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tac_gia`
--

LOCK TABLES `tac_gia` WRITE;
/*!40000 ALTER TABLE `tac_gia` DISABLE KEYS */;
INSERT INTO `tac_gia` VALUES ('0001','Chân Trí','Nam','1976',1),('0002','Diệp Lạc Vô Tâm','Nữ','1976',1),('0003','Cố Mạn','Nữ','1976',1),('0004','Lạc Tử Thiên','Nam','1975',1),('0005','Tô Bạch Thố','Nam','1975',1),('0006','Liu Yongbiao','Nam','1970',1),('0007','Zhou Haohui:','Nữ','1970',1),('0008','Mai Jia','Nam','1970',1),('0009','Lạc Dương','Nam','1964',1),('0010','Tưởng Văn Hoa ','Nữ','1970',1),('0011','Ni Kuang','Nam','1978',1),('0012','Duong Quang Minh','Nữ','1980',1),('0013','Đong Phuong Gia ','Nam','1982',1),('0014','Đang Duong Gia','Nữ','1982',1);
LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('0001','maianh0001','123456','0002',1),('0002','yenphuong0002','123456','0002',1),('ad','admin','123','0001',1),('0003','khacquan0003','123456','0002',1),('0004','minhthai0004','123456','0002',1);
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-10 22:06:31
