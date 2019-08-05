-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: 121.126.197.100    Database: neoforthdb
-- ------------------------------------------------------
-- Server version	5.7.26-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_file`
--

DROP TABLE IF EXISTS `tb_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_file` (
  `file_id` int(10) NOT NULL AUTO_INCREMENT,
  `upload_group` varchar(20) DEFAULT NULL COMMENT '업로드그룹',
  `upload_group_sub` varchar(20) DEFAULT NULL COMMENT '업로드그룹 서브',
  `master_table` varchar(20) DEFAULT NULL COMMENT '게시글table',
  `master_id` varchar(20) DEFAULT NULL COMMENT '게시글아이디',
  `org_file_name` varchar(100) DEFAULT NULL COMMENT '파일명',
  `sys_file_name` varchar(100) DEFAULT NULL COMMENT '시스템파일명',
  `file_path` varchar(20) DEFAULT NULL COMMENT '경로',
  `file_size` varchar(20) DEFAULT NULL COMMENT '사이즈',
  `file_ext` varchar(10) DEFAULT NULL COMMENT '확장자',
  `del_yn` char(1) DEFAULT 'N',
  `in_date` datetime DEFAULT NULL,
  `in_user` varchar(50) DEFAULT NULL,
  `in_ip` varchar(20) DEFAULT NULL,
  `up_date` datetime DEFAULT NULL,
  `up_user` varchar(50) DEFAULT NULL,
  `up_ip` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_file`
--

LOCK TABLES `tb_file` WRITE;
/*!40000 ALTER TABLE `tb_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sample`
--

DROP TABLE IF EXISTS `tb_sample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_sample` (
  `sample_id` int(10) NOT NULL AUTO_INCREMENT,
  `sample_name` varchar(300) DEFAULT NULL COMMENT '이름',
  `sample_subject` varchar(800) DEFAULT NULL COMMENT '제목',
  `sample_contents` text COMMENT '내용',
  `sample_order` int(10) DEFAULT '1' COMMENT '정렬순서',
  `hot_yn` char(1) DEFAULT 'N' COMMENT '중요표시',
  `del_yn` char(1) DEFAULT 'N',
  `in_date` datetime DEFAULT NULL,
  `in_user` varchar(50) DEFAULT NULL,
  `in_ip` varchar(20) DEFAULT NULL,
  `up_date` datetime DEFAULT NULL,
  `up_user` varchar(50) DEFAULT NULL,
  `up_ip` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`sample_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sample`
--

LOCK TABLES `tb_sample` WRITE;
/*!40000 ALTER TABLE `tb_sample` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sample` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-05 15:17:02
