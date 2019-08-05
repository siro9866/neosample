-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: 121.126.197.100    Database: banky_neo
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
-- Table structure for table `helpinfo`
--

DROP TABLE IF EXISTS `helpinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `helpinfo` (
  `id` varchar(160) NOT NULL COMMENT '화면주소ID',
  `title` varchar(80) NOT NULL COMMENT '제목',
  `sequence` int(11) NOT NULL COMMENT '순번',
  `content` text NOT NULL COMMENT '내용',
  `useFlg` char(1) DEFAULT 'Y' COMMENT '사용여부',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초등록일시',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '수정일시',
  `createUser` varchar(20) DEFAULT NULL COMMENT '최초등록자',
  `updateUser` varchar(20) DEFAULT NULL COMMENT '수정자',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='공통 도움말';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `helpinfo`
--

LOCK TABLES `helpinfo` WRITE;
/*!40000 ALTER TABLE `helpinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `helpinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infodata`
--

DROP TABLE IF EXISTS `infodata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infodata` (
  `info_id` int(11) NOT NULL AUTO_INCREMENT,
  `revisedDay` int(11) DEFAULT '0' COMMENT '보정일',
  `revisedPrice` bigint(11) DEFAULT '0' COMMENT '보정금액',
  `createDate` varchar(50) DEFAULT '0' COMMENT '작성일',
  `updateDate` varchar(50) DEFAULT '0' COMMENT '수정일',
  `createUser` varchar(50) DEFAULT '0' COMMENT '작성자',
  `updateUser` varchar(50) DEFAULT '0' COMMENT '수정자',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='보정일등 관리할 필요가 있는 공통 데이타 ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infodata`
--

LOCK TABLES `infodata` WRITE;
/*!40000 ALTER TABLE `infodata` DISABLE KEYS */;
INSERT INTO `infodata` VALUES (23,10,10005,'2019-04-04 10:42:27','2019-05-30 15:28:05','knoone','MASTER');
/*!40000 ALTER TABLE `infodata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_history`
--

DROP TABLE IF EXISTS `loan_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan_history` (
  `loan_history_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '대출histroy key',
  `loan_history_day` char(10) DEFAULT NULL COMMENT '거래일자',
  `transaction_type_code` varchar(20) DEFAULT NULL COMMENT '대출history 종류',
  `transaction_type_note` text COMMENT '대출history 비고',
  `cnum` varchar(11) DEFAULT NULL COMMENT '고객정보',
  `loan_num` varchar(11) DEFAULT NULL COMMENT '대출정보 key',
  `pled_num` varchar(11) DEFAULT NULL COMMENT '질권정보 key',
  `ov_id` int(11) DEFAULT NULL COMMENT '연체정보 key',
  `repay_count` varchar(11) DEFAULT NULL COMMENT '납입회차',
  `prev_loanstatus` varchar(20) DEFAULT NULL COMMENT '이전 대출상태',
  `prev_loan_div` varchar(20) DEFAULT NULL COMMENT '이전 대출구분',
  `prev_loan_cost` varchar(11) DEFAULT NULL COMMENT '이전 대출금액',
  `prev_loan_irt` varchar(9) DEFAULT NULL COMMENT '이전 금리',
  `prev_irt_day` varchar(2) DEFAULT NULL COMMENT '이전 이자일',
  `prev_loan_overdue_irt` varchar(9) DEFAULT NULL COMMENT '이전 연체금리',
  `prev_loan_repay` varchar(20) DEFAULT NULL COMMENT '이전 상환방식',
  `prev_trading` varchar(120) DEFAULT NULL COMMENT '이전 매매건',
  `prev_re_boolen` varchar(20) DEFAULT NULL COMMENT '이전 동시진행',
  `prev_manager` varchar(125) DEFAULT NULL COMMENT '이전 담당자',
  `prev_account` varchar(125) DEFAULT NULL COMMENT '이전 거래처',
  `prev_extra_pay` varchar(11) DEFAULT NULL COMMENT '이전 수당',
  `prev_loan_handday` char(10) DEFAULT NULL COMMENT '이전 자서일',
  `prev_loan_sday` char(10) DEFAULT NULL COMMENT '이전 기표일',
  `prev_loan_term` varchar(2) DEFAULT NULL COMMENT '이전 대출기간',
  `prev_loan_eday` char(10) DEFAULT NULL COMMENT '이전 만기일',
  `prev_loan_rday` char(10) DEFAULT NULL COMMENT '이전 상환일',
  `prev_commission_kbn` char(4) DEFAULT NULL COMMENT '이전 중도상환수수료',
  `prev_commission1_irt` varchar(8) DEFAULT NULL,
  `prev_commission2_irt` varchar(8) DEFAULT NULL,
  `prev_commission3_irt` varchar(8) DEFAULT NULL,
  `prev_commission1_day` varchar(3) DEFAULT NULL,
  `prev_commission2_day` varchar(3) DEFAULT NULL,
  `prev_commission3_day` varchar(3) DEFAULT NULL,
  `prev_loan_memo` varchar(255) DEFAULT NULL COMMENT '이전 메모',
  `prev_sub_loan_num` varchar(11) DEFAULT NULL COMMENT '이전 연결대출번호',
  `prev_pledge_cost` varchar(11) DEFAULT NULL COMMENT '이전 질권대출금액',
  `prev_pledge_irt` varchar(8) DEFAULT NULL COMMENT '이전 질권금리',
  `prev_pledge_irt_day` varchar(2) DEFAULT NULL COMMENT '이전 질권이자일',
  `prev_pledge_finance_inst` varchar(120) DEFAULT NULL COMMENT '이전 질권금융기관',
  `prev_pledge_sday` char(10) DEFAULT NULL COMMENT '이전 질권기표일',
  `prev_pledge_term` varchar(3) DEFAULT NULL COMMENT '이전 질권대출기간',
  `prev_pledge_eday` char(10) DEFAULT NULL COMMENT '이전 질권만기일',
  `prev_pledge_rday` char(10) DEFAULT NULL COMMENT '이전 질권상환일',
  `prev_pledge_commission_charge` varchar(11) DEFAULT NULL COMMENT '이전 질권중도상환수수료',
  `prev_pledge_ltv` varchar(5) DEFAULT NULL COMMENT '이전 질권 LTV',
  `prev_pledge_unpaid_cost` varchar(11) DEFAULT NULL COMMENT '이전 질권미회수금액',
  `prev_pledge_memo` varchar(255) DEFAULT NULL COMMENT '이전 질권메모',
  `prev_re_calterm_sday` char(10) DEFAULT NULL COMMENT '이전 납입회차이자시작일',
  `prev_re_calterm_eday` char(10) DEFAULT NULL COMMENT '이전 납입회자이자종료일',
  `prev_re_cal_day` varchar(5) DEFAULT NULL COMMENT '이전 계산일수',
  `prev_re_pay_day` varchar(2) DEFAULT NULL COMMENT '이전 매월 납입일',
  `prev_re_irt_day` char(10) DEFAULT NULL COMMENT '이전 납부스케줄용 납입일',
  `prev_re_normal_irt` varchar(8) DEFAULT NULL COMMENT '이전 정상이자율',
  `prev_re_normal_cost` varchar(11) DEFAULT NULL COMMENT '이전 정상납부할 이자금액',
  `prev_re_overdue_irt` varchar(8) DEFAULT NULL COMMENT '이전 연체이자율',
  `prev_re_pay_cost` varchar(11) DEFAULT NULL COMMENT '이전 납부 할 원금',
  `prev_re_pay_sum_cost` varchar(11) DEFAULT NULL COMMENT '이전 납부 할 이자원금 합 금액',
  `prev_re_balance_cost` varchar(11) DEFAULT NULL COMMENT '이전 대출잔액',
  `prev_re_grade` char(6) DEFAULT NULL COMMENT '이전 상태',
  `prev_mangi_flg` char(1) DEFAULT NULL COMMENT '이전 만기플래그',
  `prev_ov_principal_cost` varchar(11) DEFAULT NULL COMMENT '이전 대출금액',
  `prev_ov_overdue_sday` char(10) DEFAULT NULL COMMENT '이전 연체시작일',
  `prev_ov_normal_cost` varchar(11) DEFAULT NULL COMMENT '이전 정상이자금액',
  `prev_ov_exem_irt` varchar(8) DEFAULT NULL COMMENT '이전 연체이율',
  `prev_ov_exem_cost` varchar(11) DEFAULT NULL COMMENT '이전 연체금액',
  `prev_ov_sum_cost` varchar(11) DEFAULT NULL COMMENT '이전 정상이자+연체금액합계',
  `next_loanstatus` varchar(20) DEFAULT NULL COMMENT '다음 대출상태',
  `next_loan_div` varchar(20) DEFAULT NULL COMMENT '다음 대출구분',
  `next_loan_cost` varchar(11) DEFAULT NULL COMMENT '다음 대출금액',
  `next_loan_irt` varchar(8) DEFAULT NULL COMMENT '다음 금리',
  `next_irt_day` varchar(20) DEFAULT NULL COMMENT '다음 이자일',
  `next_loan_overdue_irt` varchar(8) DEFAULT NULL COMMENT '다음 연체금리',
  `next_loan_repay` varchar(20) DEFAULT NULL COMMENT '다음 상환방식',
  `next_trading` varchar(120) DEFAULT NULL COMMENT '다음 매매건',
  `next_re_boolen` varchar(20) DEFAULT NULL COMMENT '다음 동시진행',
  `next_manager` varchar(125) DEFAULT NULL COMMENT '다음 담당자',
  `next_account` varchar(125) DEFAULT NULL COMMENT '다음 거래처',
  `next_extra_pay` varchar(11) DEFAULT NULL COMMENT '다음 수당',
  `next_loan_handday` char(10) DEFAULT NULL COMMENT '다음 자서일',
  `next_loan_sday` char(10) DEFAULT NULL COMMENT '다음 기표일',
  `next_loan_term` varchar(2) DEFAULT NULL COMMENT '다음 대출기간',
  `next_loan_eday` char(10) DEFAULT NULL COMMENT '다음 만기일',
  `next_loan_rday` char(10) DEFAULT NULL COMMENT '다음 상환일',
  `next_commission_kbn` varchar(20) DEFAULT NULL COMMENT '다음 중도상환수수료',
  `next_commission1_irt` varchar(8) DEFAULT NULL,
  `next_commission2_irt` varchar(8) DEFAULT NULL,
  `next_commission3_irt` varchar(8) DEFAULT NULL,
  `next_commission1_day` varchar(3) DEFAULT NULL,
  `next_commission2_day` varchar(3) DEFAULT NULL,
  `next_commission3_day` varchar(3) DEFAULT NULL,
  `next_loan_memo` varchar(255) DEFAULT NULL COMMENT '다음 메모',
  `next_sub_loan_num` varchar(11) DEFAULT NULL COMMENT '다음 연결대출번호',
  `next_pledge_cost` varchar(11) DEFAULT NULL COMMENT '다음 질권대출금액',
  `next_pledge_irt` varchar(8) DEFAULT NULL COMMENT '다음 질권금리',
  `next_pledge_irt_day` varchar(2) DEFAULT NULL COMMENT '다음 질권이자일',
  `next_pledge_finance_inst` varchar(120) DEFAULT NULL COMMENT '다음 질권금융기관',
  `next_pledge_sday` char(10) DEFAULT NULL COMMENT '다음 질권기표일',
  `next_pledge_term` varchar(3) DEFAULT NULL COMMENT '다음 질권대출기간',
  `next_pledge_eday` char(10) DEFAULT NULL COMMENT '다음 질권만기일',
  `next_pledge_rday` char(10) DEFAULT NULL COMMENT '다음 질권상환일',
  `next_pledge_commission_charge` varchar(11) DEFAULT NULL COMMENT '다음 질권중도상환수수료',
  `next_pledge_ltv` varchar(5) DEFAULT NULL COMMENT '다음 질권 LTV',
  `next_pledge_unpaid_cost` varchar(11) DEFAULT NULL COMMENT '다음 질권미회수금액',
  `next_pledge_memo` varchar(255) DEFAULT NULL COMMENT '다음 질권메모',
  `next_re_calterm_sday` char(10) DEFAULT NULL COMMENT '다음 납입회차이자시작일',
  `next_re_calterm_eday` char(10) DEFAULT NULL COMMENT '다음 납입회자이자종료일',
  `next_re_cal_day` varchar(5) DEFAULT NULL COMMENT '다음 계산일수',
  `next_re_pay_day` varchar(2) DEFAULT NULL COMMENT '다음 매월 납입일',
  `next_re_irt_day` char(10) DEFAULT NULL COMMENT '다음 납부스케줄용 납입일',
  `next_re_normal_irt` varchar(8) DEFAULT NULL COMMENT '다음 정상이자율',
  `next_re_normal_cost` varchar(11) DEFAULT NULL COMMENT '다음 정상납부할 이자금액',
  `next_re_overdue_irt` varchar(8) DEFAULT NULL COMMENT '다음 연체이자율',
  `next_re_pay_cost` varchar(11) DEFAULT NULL COMMENT '다음 납부 할 원금',
  `next_re_pay_sum_cost` varchar(11) DEFAULT NULL COMMENT '다음 납부 할 이자원금 합 금액',
  `next_re_balance_cost` varchar(11) DEFAULT NULL COMMENT '다음 대출잔액',
  `next_re_grade` varchar(20) DEFAULT NULL COMMENT '다음 상태',
  `next_mangi_flg` char(1) DEFAULT NULL COMMENT '다음 만기플래그',
  `next_ov_principal_cost` varchar(11) DEFAULT NULL COMMENT '다음 연체 대출금액',
  `next_ov_overdue_sday` varchar(10) DEFAULT NULL COMMENT '다음 연체시작일',
  `next_ov_normal_cost` varchar(11) DEFAULT NULL COMMENT '다음 정상이자금액',
  `next_ov_exem_irt` varchar(8) DEFAULT NULL COMMENT '다음 연체이율',
  `next_ov_exem_cost` varchar(11) DEFAULT NULL COMMENT '다음 연체금액',
  `next_ov_sum_cost` varchar(11) DEFAULT NULL COMMENT '다음 정상이자+연체금액합계',
  `del_fig` char(1) DEFAULT 'N' COMMENT '삭제여부',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초등록일시',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '수정일시',
  `createUser` varchar(20) NOT NULL COMMENT '최초등록자',
  `updateUser` varchar(20) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`loan_history_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='대출histroy';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan_history`
--

LOCK TABLES `loan_history` WRITE;
/*!40000 ALTER TABLE `loan_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_history_pledsett`
--

DROP TABLE IF EXISTS `loan_history_pledsett`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan_history_pledsett` (
  `loan_history_id` int(11) NOT NULL DEFAULT '0' COMMENT '질권정보 key',
  `pled_num` int(11) NOT NULL COMMENT '질권정보 key',
  `loan_num` int(11) NOT NULL COMMENT '대출정보 key',
  `pledge_cost` bigint(11) DEFAULT NULL COMMENT '질권대출금액',
  `pledge_irt` double(5,2) DEFAULT NULL COMMENT '질권금리',
  `pledge_irt_day` int(2) DEFAULT NULL COMMENT '질권이자일',
  `pledge_finance_inst` varchar(120) DEFAULT NULL COMMENT '질권금융기관',
  `pledge_sday` char(10) DEFAULT NULL COMMENT '질권기표일',
  `pledge_term` int(3) DEFAULT NULL COMMENT '질권대출기간',
  `pledge_eday` char(10) DEFAULT NULL COMMENT '질권만기일',
  `pledge_rday` char(10) DEFAULT NULL COMMENT '질권상환일',
  `pledge_commission_charge` bigint(11) DEFAULT NULL COMMENT '질권중도상환수수료',
  `pledge_ltv` varchar(5) DEFAULT NULL COMMENT '질권 LTV',
  `pledge_unpaid_cost` bigint(11) DEFAULT NULL COMMENT '질권미회수금액',
  `pledge_memo` varchar(255) DEFAULT NULL COMMENT '질권메모',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초등록일시',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '수정일시',
  `createUser` varchar(20) NOT NULL COMMENT '최초등록자',
  `updateUser` varchar(20) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`loan_history_id`,`pled_num`,`loan_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='질권설정테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan_history_pledsett`
--

LOCK TABLES `loan_history_pledsett` WRITE;
/*!40000 ALTER TABLE `loan_history_pledsett` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_history_pledsett` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_history_receiveinfo`
--

DROP TABLE IF EXISTS `loan_history_receiveinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan_history_receiveinfo` (
  `loan_history_id` bigint(11) NOT NULL COMMENT '대출histroy key',
  `rec_id` int(11) NOT NULL COMMENT '수납정보 key',
  `cnum` int(11) NOT NULL COMMENT '고객정보 key',
  `loan_num` int(11) NOT NULL COMMENT '대출정보 key',
  `repay_count` int(11) NOT NULL COMMENT '납입회차',
  `rec_info` char(7) DEFAULT NULL COMMENT '수납상태',
  `rec_overdue_sday` char(10) DEFAULT NULL COMMENT '연체계산 시작',
  `rec_overdue_eday` char(10) DEFAULT NULL COMMENT '연체계산 종료',
  `rec_exem_cost` bigint(11) DEFAULT NULL COMMENT '이자면제금액',
  `rec_normal_cost` bigint(11) DEFAULT '0' COMMENT '납부 원금 금액',
  `rec_normal_irt_cost` bigint(11) DEFAULT '0' COMMENT '정상 이자 금액',
  `rec_overdue_cost` bigint(11) DEFAULT NULL COMMENT '연체 이자 금액',
  `rec_deposit_cost` bigint(11) DEFAULT NULL COMMENT '입금한 이자 금액',
  `rec_pay_day` char(10) DEFAULT NULL COMMENT '이자 수납한 일자',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최초등록일시',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '수정일시',
  `createUser` varchar(20) NOT NULL COMMENT '최초등록자',
  `updateUser` varchar(20) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`loan_history_id`,`cnum`,`loan_num`,`repay_count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='대출histroy';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan_history_receiveinfo`
--

LOCK TABLES `loan_history_receiveinfo` WRITE;
/*!40000 ALTER TABLE `loan_history_receiveinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_history_receiveinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan_history_repaymentinfo`
--

DROP TABLE IF EXISTS `loan_history_repaymentinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan_history_repaymentinfo` (
  `loan_history_id` bigint(11) NOT NULL COMMENT '대출histroy key',
  `re_id` int(11) NOT NULL COMMENT '상환정보 key',
  `cnum` int(11) NOT NULL COMMENT '고객정보key',
  `loan_num` int(11) NOT NULL COMMENT '대출정보key',
  `repay_count` int(11) NOT NULL COMMENT '납입회차',
  `re_calterm_sday` char(10) NOT NULL COMMENT '납입회차이자시작일',
  `re_calterm_eday` char(10) NOT NULL COMMENT '납입회자이자종료일',
  `re_cal_day` int(5) NOT NULL COMMENT '계산일수',
  `re_pay_day` int(2) NOT NULL COMMENT '매월 납입일',
  `re_irt_day` char(10) NOT NULL COMMENT '납부스케줄용 납입일',
  `re_normal_irt` double(5,2) NOT NULL COMMENT '정상이자율',
  `re_normal_cost` bigint(11) NOT NULL COMMENT '정상납부할 이자금액',
  `re_overdue_irt` double(5,2) NOT NULL COMMENT '연체이자율',
  `re_pay_cost` bigint(11) NOT NULL COMMENT '납부 할 원금',
  `re_pay_sum_cost` bigint(11) NOT NULL COMMENT '납부 할 이자원금 합 금액',
  `re_balance_cost` bigint(11) NOT NULL COMMENT '대출잔액',
  `re_grade` char(6) NOT NULL COMMENT '상태',
  `mangi_flg` char(1) NOT NULL COMMENT '만기플래그',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초등록일시',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '수정일시',
  `createUser` varchar(20) NOT NULL COMMENT '최초등록자',
  `updateUser` varchar(20) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`loan_history_id`,`re_id`,`cnum`,`loan_num`,`repay_count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='대출histroy';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan_history_repaymentinfo`
--

LOCK TABLES `loan_history_repaymentinfo` WRITE;
/*!40000 ALTER TABLE `loan_history_repaymentinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan_history_repaymentinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loancust`
--

DROP TABLE IF EXISTS `loancust`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loancust` (
  `cnum` int(11) NOT NULL AUTO_INCREMENT COMMENT '고객정보 key',
  `cname` varchar(125) NOT NULL COMMENT '성명',
  `cstatus` varchar(20) NOT NULL COMMENT '고객상태',
  `crrn` char(6) NOT NULL COMMENT '주민번호 앞자리',
  `crrn2` varchar(55) DEFAULT NULL COMMENT '주민번호 뒷자리',
  `sex` varchar(20) NOT NULL COMMENT '성별',
  `marry` varchar(20) DEFAULT NULL COMMENT '결혼여부',
  `grade` char(6) DEFAULT NULL COMMENT '등급',
  `grade_status` varchar(20) DEFAULT NULL COMMENT '등급상태',
  `hpost` varchar(7) DEFAULT NULL COMMENT '우편번호',
  `haddr1` char(20) DEFAULT NULL COMMENT '주소1(시,도)',
  `haddr1_1` char(20) DEFAULT NULL COMMENT '주소2(구군)',
  `haddr2` varchar(625) DEFAULT NULL COMMENT '주소상세',
  `ctel_div` varchar(20) NOT NULL COMMENT '통신사',
  `ctel` varchar(70) NOT NULL COMMENT '휴대폰번호',
  `htel` varchar(70) DEFAULT NULL COMMENT '일반전화번호',
  `email` varchar(120) DEFAULT NULL COMMENT '이메일',
  `company_occup` varchar(50) DEFAULT NULL COMMENT '직업구분',
  `company_name` varchar(50) DEFAULT NULL COMMENT '직장명',
  `company_position` varchar(50) DEFAULT NULL COMMENT '직위',
  `company_tel` varchar(70) DEFAULT NULL COMMENT '직장연락처',
  `company_post` varchar(35) DEFAULT NULL COMMENT '직장우편번호',
  `company_addr1` char(20) DEFAULT NULL COMMENT '직장주소1',
  `company_addr1_1` char(20) DEFAULT NULL COMMENT '직장주소2',
  `company_addr2` varchar(625) DEFAULT NULL COMMENT '직장주소상세',
  `cdname` varchar(20) DEFAULT NULL COMMENT '대리인성명',
  `cdtel` varchar(70) DEFAULT NULL COMMENT '대리인연락처',
  `cdrel` varchar(20) DEFAULT NULL COMMENT '대리인관계',
  `memo` varchar(255) DEFAULT NULL COMMENT '메모',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초등록일시',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '수정일시',
  `createUser` varchar(20) NOT NULL COMMENT '최초등록자',
  `updateUser` varchar(20) NOT NULL COMMENT '수정자',
  `salary` varchar(13) DEFAULT NULL COMMENT '연봉',
  PRIMARY KEY (`cnum`),
  UNIQUE KEY `cnum` (`cnum`)
) ENGINE=InnoDB AUTO_INCREMENT=1133 DEFAULT CHARSET=utf8 COMMENT='대출고객정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loancust`
--

LOCK TABLES `loancust` WRITE;
/*!40000 ALTER TABLE `loancust` DISABLE KEYS */;
/*!40000 ALTER TABLE `loancust` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loaninfo`
--

DROP TABLE IF EXISTS `loaninfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loaninfo` (
  `loan_num` int(11) NOT NULL AUTO_INCREMENT COMMENT '대출정보 key',
  `cnum` int(11) NOT NULL COMMENT '고객정보 key',
  `loanstatus` varchar(20) DEFAULT NULL COMMENT '대출상태',
  `loan_div` varchar(20) DEFAULT NULL COMMENT '대출구분',
  `loan_cost` bigint(11) DEFAULT NULL COMMENT '대출금액',
  `loan_irt` double(5,2) DEFAULT NULL COMMENT '금리',
  `irt_day` varchar(20) DEFAULT NULL COMMENT '이자일',
  `loan_overdue_irt` double(5,2) DEFAULT NULL COMMENT '연체금리',
  `loan_repay` varchar(20) DEFAULT NULL COMMENT '상환방식',
  `trading` varchar(120) DEFAULT NULL COMMENT '매매건',
  `re_boolen` varchar(20) DEFAULT NULL COMMENT '동시진행',
  `manager` varchar(125) DEFAULT NULL COMMENT '담당자',
  `account` varchar(125) DEFAULT NULL COMMENT '거래처',
  `extra_pay` bigint(11) DEFAULT NULL COMMENT '수당',
  `loan_handday` char(10) DEFAULT NULL COMMENT '자서일',
  `loan_sday` char(10) DEFAULT NULL COMMENT '기표일',
  `loan_term` int(2) DEFAULT NULL COMMENT '대출기간',
  `loan_eday` char(10) DEFAULT NULL COMMENT '만기일',
  `loan_rday` char(10) DEFAULT NULL COMMENT '상환일',
  `commission_kbn` varchar(20) DEFAULT NULL COMMENT '중도상환수수료',
  `commission1_irt` double(5,2) DEFAULT NULL,
  `commission2_irt` double(5,2) DEFAULT NULL,
  `commission3_irt` double(5,2) DEFAULT NULL,
  `commission1_day` int(3) DEFAULT NULL,
  `commission2_day` int(3) DEFAULT NULL,
  `commission3_day` int(3) DEFAULT NULL,
  `loan_memo` varchar(255) DEFAULT NULL COMMENT '메모',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초등록일시',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '수정일시',
  `createUser` varchar(20) NOT NULL COMMENT '최초등록자',
  `updateUser` varchar(20) NOT NULL COMMENT '수정자',
  `sub_loan_num` varchar(11) DEFAULT NULL COMMENT '연결대출번호',
  PRIMARY KEY (`loan_num`),
  UNIQUE KEY `loan_num` (`loan_num`),
  KEY `loanInfo2_ibfk_1` (`cnum`),
  CONSTRAINT `loanInfo_ibfk_1` FOREIGN KEY (`cnum`) REFERENCES `loancust` (`cnum`)
) ENGINE=InnoDB AUTO_INCREMENT=5196 DEFAULT CHARSET=utf8 COMMENT='대출정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loaninfo`
--

LOCK TABLES `loaninfo` WRITE;
/*!40000 ALTER TABLE `loaninfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `loaninfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanmember`
--

DROP TABLE IF EXISTS `loanmember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loanmember` (
  `empl_num` int(11) NOT NULL AUTO_INCREMENT COMMENT '사원 key',
  `company_num` int(11) DEFAULT NULL COMMENT '회사명',
  `empl_name` varchar(125) NOT NULL COMMENT '성명',
  `empl_id` varchar(20) NOT NULL COMMENT '아이디',
  `empl_pwd` varchar(125) NOT NULL COMMENT '패스워드',
  `empl_pwd_key` varchar(30) NOT NULL COMMENT '패스워드 키',
  `empl_email` varchar(120) DEFAULT NULL COMMENT '이메일',
  `empl_phone` varchar(80) DEFAULT NULL COMMENT '연락처',
  `empl_use` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `empl_role` char(1) NOT NULL DEFAULT 'P' COMMENT '권한',
  `join_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일시',
  `last_login_date` timestamp NULL DEFAULT NULL COMMENT '최종로그인일시',
  PRIMARY KEY (`empl_num`,`empl_id`),
  UNIQUE KEY `empl_num_UNIQUE` (`empl_num`),
  KEY `loanMember_ibfk_1` (`company_num`),
  CONSTRAINT `loanMember_ibfk_1` FOREIGN KEY (`company_num`) REFERENCES `banky`.`loancompany` (`company_num`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='대출회사 사원정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanmember`
--

LOCK TABLES `loanmember` WRITE;
/*!40000 ALTER TABLE `loanmember` DISABLE KEYS */;
INSERT INTO `loanmember` VALUES (27,1,'neo','neo','4Xaj6/1armtHK1Y08+u69Q==','11','rosy@neoforth.com','010-8898-3093','Y','P','2019-06-27 07:01:20','2019-07-29 07:43:34');
/*!40000 ALTER TABLE `loanmember` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanoffer`
--

DROP TABLE IF EXISTS `loanoffer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loanoffer` (
  `gu_num` int(11) NOT NULL AUTO_INCREMENT COMMENT '담보제공자key',
  `cnum` int(11) NOT NULL COMMENT '고객정보 key',
  `loan_num` int(11) NOT NULL COMMENT '대출정보 key',
  `gu_cname` varchar(125) NOT NULL COMMENT '담보제공자성명',
  `name_kinds` varchar(20) NOT NULL COMMENT '담보명의',
  `gu_crrn` varchar(20) NOT NULL COMMENT '주민번호 앞자리',
  `gu_crrn2` varchar(55) DEFAULT NULL COMMENT '주민번호 뒷자리',
  `gu_sex` varchar(20) DEFAULT NULL COMMENT '성별',
  `gu_rel` varchar(20) DEFAULT NULL COMMENT '채무자와의관계',
  `gu_grade` varchar(10) DEFAULT NULL COMMENT '등급',
  `gu_grade_status` varchar(20) DEFAULT NULL COMMENT '등급상태',
  `gu_hpost` varchar(7) DEFAULT NULL COMMENT '우편번호',
  `gu_addr1` char(20) DEFAULT NULL COMMENT '주소1(시,도)',
  `gu_addr1_1` char(20) DEFAULT NULL COMMENT '주소2(구군)',
  `gu_addr2` varchar(625) DEFAULT NULL COMMENT '주소상세',
  `gu_ctel_div` varchar(20) DEFAULT NULL COMMENT '통신사',
  `gu_ctel` varchar(70) DEFAULT NULL COMMENT '휴대폰번호',
  `gu_htel` varchar(70) DEFAULT NULL COMMENT '일반전화번호',
  `gu_email` varchar(120) DEFAULT NULL COMMENT '이메일',
  `gu_company_occup` varchar(50) DEFAULT NULL COMMENT '직업구분',
  `gu_company_name` varchar(50) DEFAULT NULL COMMENT '직장명',
  `gu_company_position` varchar(50) DEFAULT NULL COMMENT '직위',
  `gu_company_tel` varchar(70) DEFAULT NULL COMMENT '직장연락처',
  `gu_company_post` varchar(35) DEFAULT NULL COMMENT '직장우편번호',
  `gu_company_addr1` char(20) DEFAULT NULL COMMENT '직장주소1',
  `gu_company_addr1_1` char(20) DEFAULT NULL COMMENT '직장주소2',
  `gu_company_addr2` varchar(625) DEFAULT NULL COMMENT '직장주소상세',
  `gu_memo` varchar(255) DEFAULT NULL COMMENT '메모',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초등록일시',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '수정일시',
  `createUser` varchar(20) NOT NULL COMMENT '최초등록자',
  `updateUser` varchar(20) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`gu_num`,`cnum`,`loan_num`),
  KEY `loanOffer_ibfk_1` (`cnum`),
  KEY `loanOffer_ibfk_2` (`loan_num`),
  CONSTRAINT `loanOffer_ibfk_1` FOREIGN KEY (`cnum`) REFERENCES `loancust` (`cnum`),
  CONSTRAINT `loanOffer_ibfk_2` FOREIGN KEY (`loan_num`) REFERENCES `loaninfo` (`loan_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='대출담보제공자';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanoffer`
--

LOCK TABLES `loanoffer` WRITE;
/*!40000 ALTER TABLE `loanoffer` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanoffer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `overdueinfo`
--

DROP TABLE IF EXISTS `overdueinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `overdueinfo` (
  `ov_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '연체정보 key',
  `cnum` int(11) NOT NULL COMMENT '고객정보key',
  `loan_num` int(11) NOT NULL COMMENT '대출정보key',
  `ov_principal_cost` bigint(11) NOT NULL COMMENT '대출금액',
  `ov_overdue_sday` char(10) NOT NULL COMMENT '연체시작일',
  `ov_normal_cost` bigint(11) NOT NULL COMMENT '정상이자금액',
  `ov_exem_irt` double(5,2) NOT NULL COMMENT '연체이율',
  `ov_exem_cost` bigint(11) NOT NULL COMMENT '연체금액',
  `ov_sum_cost` bigint(11) NOT NULL COMMENT '정상이자+연체금액합계',
  `ov_memo` varchar(255) DEFAULT NULL COMMENT '메모',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초등록일시',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '수정일시',
  `createUser` varchar(20) NOT NULL COMMENT '최초등록자',
  `updateUser` varchar(20) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`cnum`,`loan_num`),
  UNIQUE KEY `ov_id_UNIQUE` (`ov_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='연체정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `overdueinfo`
--

LOCK TABLES `overdueinfo` WRITE;
/*!40000 ALTER TABLE `overdueinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `overdueinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pledsett`
--

DROP TABLE IF EXISTS `pledsett`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pledsett` (
  `pled_num` int(11) NOT NULL AUTO_INCREMENT COMMENT '질권정보 key',
  `loan_num` int(11) NOT NULL COMMENT '대출정보 key',
  `pledge_cost` bigint(11) DEFAULT NULL COMMENT '질권대출금액',
  `pledge_irt` double(5,2) DEFAULT NULL COMMENT '질권금리',
  `pledge_irt_day` varchar(20) DEFAULT NULL COMMENT '질권이자일',
  `pledge_finance_inst` varchar(120) DEFAULT NULL COMMENT '질권금융기관',
  `pledge_sday` char(10) DEFAULT NULL COMMENT '질권기표일',
  `pledge_term` int(3) DEFAULT NULL COMMENT '질권대출기간',
  `pledge_eday` char(10) DEFAULT NULL COMMENT '질권만기일',
  `pledge_rday` char(10) DEFAULT NULL COMMENT '질권상환일',
  `pledge_commission_charge` bigint(11) DEFAULT NULL COMMENT '질권중도상환수수료',
  `pledge_ltv` varchar(5) DEFAULT NULL COMMENT '질권 LTV',
  `pledge_unpaid_cost` bigint(11) DEFAULT NULL COMMENT '질권미회수금액',
  `pledge_memo` varchar(255) DEFAULT NULL COMMENT '질권메모',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초등록일시',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '수정일시',
  `createUser` varchar(20) NOT NULL COMMENT '최초등록자',
  `updateUser` varchar(20) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`pled_num`,`loan_num`),
  KEY `pledSett_ibfk_2` (`loan_num`),
  KEY `pledSett_ibfk_1` (`pledge_cost`),
  CONSTRAINT `pledSett_ibfk_1` FOREIGN KEY (`loan_num`) REFERENCES `loaninfo` (`loan_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='질권설정테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pledsett`
--

LOCK TABLES `pledsett` WRITE;
/*!40000 ALTER TABLE `pledsett` DISABLE KEYS */;
/*!40000 ALTER TABLE `pledsett` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receiveinfo`
--

DROP TABLE IF EXISTS `receiveinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receiveinfo` (
  `rec_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '수납정보 key',
  `cnum` int(11) NOT NULL COMMENT '고객정보 key',
  `loan_num` int(11) NOT NULL COMMENT '대출정보 key',
  `repay_count` int(11) NOT NULL COMMENT '납입회차',
  `rec_info` varchar(20) DEFAULT NULL COMMENT '수납상태',
  `rec_overdue_sday` char(10) DEFAULT NULL COMMENT '연체계산 시작',
  `rec_overdue_eday` char(10) DEFAULT NULL COMMENT '연체계산 종료',
  `rec_exem_cost` bigint(11) DEFAULT NULL COMMENT '이자면제금액',
  `rec_normal_cost` bigint(11) DEFAULT '0' COMMENT '납부 원금 금액',
  `rec_normal_irt_cost` bigint(11) DEFAULT '0' COMMENT '정상 이자 금액',
  `rec_overdue_cost` bigint(11) DEFAULT NULL COMMENT '연체 이자 금액',
  `rec_deposit_cost` bigint(11) DEFAULT NULL COMMENT '입금한 이자 금액',
  `rec_pay_day` char(10) DEFAULT NULL COMMENT '이자 수납한 일자',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최초등록일시',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '수정일시',
  `createUser` varchar(20) NOT NULL COMMENT '최초등록자',
  `updateUser` varchar(20) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`cnum`,`loan_num`,`repay_count`),
  UNIQUE KEY `rec_id_UNIQUE` (`rec_id`),
  KEY `receiveInfo_ibfk_1_idx` (`cnum`,`loan_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='수납정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receiveinfo`
--

LOCK TABLES `receiveinfo` WRITE;
/*!40000 ALTER TABLE `receiveinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `receiveinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repaymentinfo`
--

DROP TABLE IF EXISTS `repaymentinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repaymentinfo` (
  `re_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '상환정보 key',
  `cnum` int(11) NOT NULL COMMENT '고객정보key',
  `loan_num` int(11) NOT NULL COMMENT '대출정보key',
  `repay_count` int(11) NOT NULL COMMENT '납입회차',
  `re_calterm_sday` char(10) NOT NULL COMMENT '납입회차이자시작일',
  `re_calterm_eday` char(10) NOT NULL COMMENT '납입회자이자종료일',
  `re_cal_day` int(5) NOT NULL COMMENT '계산일수',
  `re_pay_day` int(2) NOT NULL COMMENT '매월 납입일',
  `re_irt_day` char(10) NOT NULL COMMENT '납부스케줄용 납입일',
  `re_normal_irt` double(5,2) NOT NULL COMMENT '정상이자율',
  `re_normal_cost` bigint(11) NOT NULL COMMENT '정상납부할 이자금액',
  `re_overdue_irt` double(5,2) NOT NULL COMMENT '연체이자율',
  `re_pay_cost` bigint(11) NOT NULL COMMENT '납부 할 원금',
  `re_pay_sum_cost` bigint(11) NOT NULL COMMENT '납부 할 이자원금 합 금액',
  `re_balance_cost` bigint(11) NOT NULL COMMENT '대출잔액',
  `re_grade` char(6) NOT NULL COMMENT '상태',
  `mangi_flg` char(1) NOT NULL COMMENT '만기플래그',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초등록일시',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '수정일시',
  `createUser` varchar(20) NOT NULL COMMENT '최초등록자',
  `updateUser` varchar(20) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`cnum`,`loan_num`,`repay_count`),
  UNIQUE KEY `re_id_UNIQUE` (`re_id`),
  KEY `repaymentInfo_ibfk_1_idx` (`cnum`,`loan_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상환정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repaymentinfo`
--

LOCK TABLES `repaymentinfo` WRITE;
/*!40000 ALTER TABLE `repaymentinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `repaymentinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `secuthin`
--

DROP TABLE IF EXISTS `secuthin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `secuthin` (
  `loan_num` int(11) NOT NULL COMMENT '대출정보 key',
  `secu_num` int(11) NOT NULL AUTO_INCREMENT COMMENT '물건정보 key',
  `building` varchar(20) DEFAULT NULL COMMENT '물건지종류',
  `regi_num` varchar(120) DEFAULT NULL COMMENT '등기번호',
  `rubb_post` varchar(35) DEFAULT NULL COMMENT '물건지우편번호',
  `rubb_addr1` varchar(20) DEFAULT NULL COMMENT '물건지주소1',
  `rubb_addr1_1` varchar(20) DEFAULT NULL COMMENT '물건지주소2',
  `rubb_addr2` varchar(625) DEFAULT NULL COMMENT '물건지상세주소',
  `ex_area` varchar(7) DEFAULT NULL COMMENT '물건지전용면적',
  `market_price` varchar(20) DEFAULT NULL COMMENT '물건지시세종류',
  `deal` varchar(20) DEFAULT NULL COMMENT '물건지거래가종류',
  `deal_cost` bigint(11) DEFAULT NULL COMMENT '물건지거래가종류의 값',
  `judge_cost` bigint(11) DEFAULT NULL COMMENT '물건지심사기준가',
  `contra_cost` bigint(11) DEFAULT NULL COMMENT '물건지매매가',
  `finance_inst` varchar(120) DEFAULT NULL COMMENT '물건지 선순위금융기관',
  `bondmax_cost` bigint(11) DEFAULT NULL COMMENT '물건지채권죄고액',
  `principal_cost` bigint(11) DEFAULT NULL COMMENT '물건지등기부원금',
  `comprincipal_cost` bigint(11) DEFAULT NULL COMMENT '물건지전산원금',
  `market_price_ltv` varchar(20) DEFAULT NULL COMMENT '물건지기준LTV종류',
  `ltv_bondmax` double(5,2) DEFAULT NULL COMMENT 'LTV채권최고액대비',
  `rubb_ltv_principal` double(5,2) DEFAULT NULL COMMENT '물건지LTV등기부원금대비',
  `rubb_ltv_comprincipal` double(5,2) DEFAULT NULL COMMENT '물건지LTV전산원금대비',
  `st_ltv_bondmax` double(5,2) DEFAULT NULL COMMENT '심사기준LTV채권최고액대비',
  `st_ltv_principal` double(5,2) DEFAULT NULL COMMENT '심사기준물건지LTV등기부원금대비',
  `st_ltv_comprincipal` double(5,2) DEFAULT NULL COMMENT '심사기준물건지LTV전산원금대비',
  `rubb_memo` varchar(255) DEFAULT NULL COMMENT '물건지메모',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초등록일시',
  `updateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '수정일시',
  `createUser` varchar(20) NOT NULL COMMENT '최초등록자',
  `updateUser` varchar(20) NOT NULL COMMENT '수정자',
  `su_area` varchar(7) DEFAULT NULL COMMENT '공급면적',
  PRIMARY KEY (`secu_num`,`loan_num`),
  KEY `secuThin_ibfk_2` (`loan_num`),
  KEY `secuThin_ibfk_1` (`loan_num`),
  CONSTRAINT `secuThin_ibfk_1` FOREIGN KEY (`loan_num`) REFERENCES `loaninfo` (`loan_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='담보물건테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `secuthin`
--

LOCK TABLES `secuthin` WRITE;
/*!40000 ALTER TABLE `secuthin` DISABLE KEYS */;
/*!40000 ALTER TABLE `secuthin` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-05 15:15:41
