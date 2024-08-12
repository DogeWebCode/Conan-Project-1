-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: stylish
-- ------------------------------------------------------
-- Server version	8.0.38

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
-- Table structure for table `campaign`
--

DROP TABLE IF EXISTS `campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `campaign` (
  `campaign_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `product_id` bigint unsigned NOT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `story` text,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`campaign_id`),
  UNIQUE KEY `product_id` (`product_id`),
  CONSTRAINT `campaign_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign`
--

LOCK TABLES `campaign` WRITE;
/*!40000 ALTER TABLE `campaign` DISABLE KEYS */;
INSERT INTO `campaign` VALUES (1,13,'http://3.113.167.117/uploads/201807242222_keyvisual.jpg','永遠\r\n展現自信和專業\r\n無法抵擋的男人魅力。\r\n復古《再一次經典》','2024-08-01 09:29:55','2024-08-01 09:29:55');
/*!40000 ALTER TABLE `campaign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `color`
--

DROP TABLE IF EXISTS `color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `color` (
  `color_id` int NOT NULL AUTO_INCREMENT,
  `color_code` varchar(255) NOT NULL,
  `color_name` varchar(255) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`color_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `color`
--

LOCK TABLES `color` WRITE;
/*!40000 ALTER TABLE `color` DISABLE KEYS */;
INSERT INTO `color` VALUES (1,'#ffffff','白色','2024-08-01 08:01:03','2024-08-01 08:01:03'),(2,'#ffffff','白色','2024-08-01 08:01:03','2024-08-01 08:01:03'),(3,'#ffffff','白色','2024-08-01 08:01:03','2024-08-01 08:01:03'),(4,'#ddffbb','淡綠色','2024-08-01 08:01:03','2024-08-01 08:01:03'),(5,'#ddffbb','淡綠色','2024-08-01 08:01:03','2024-08-01 08:01:03'),(6,'#cccccc','灰色','2024-08-01 08:01:03','2024-08-01 08:01:03'),(7,'#cccccc','灰色','2024-08-01 08:01:03','2024-08-01 08:01:03'),(8,'#cccccc','灰色','2024-08-01 08:01:03','2024-08-01 08:01:03'),(15,'#cccccc','灰色','2024-08-01 08:08:33','2024-08-01 08:08:33'),(16,'#ddffbb','淡綠色','2024-08-01 08:08:33','2024-08-01 08:08:33'),(17,'#ddffbb','淡綠色','2024-08-01 08:08:33','2024-08-01 08:08:33'),(18,'#cccccc','灰色','2024-08-01 08:08:33','2024-08-01 08:08:33'),(19,'#cccccc','灰色','2024-08-01 08:08:33','2024-08-01 08:08:33'),(20,'#ddffbb','淡綠色','2024-08-01 08:08:33','2024-08-01 08:08:33'),(21,'#bb7844','咖啡色','2024-08-01 08:11:44','2024-08-01 08:11:44'),(22,'#bb7844','咖啡色','2024-08-01 08:11:44','2024-08-01 08:11:44'),(23,'#ddffbb','淡綠色','2024-08-01 08:11:44','2024-08-01 08:11:44'),(24,'#cccccc','灰色','2024-08-01 08:11:44','2024-08-01 08:11:44'),(25,'#cccccc','灰色','2024-08-01 08:11:44','2024-08-01 08:11:44'),(26,'#ddffbb','淡綠色','2024-08-01 08:11:44','2024-08-01 08:11:44'),(27,'#ddf0e1','天藍色','2024-08-01 08:15:32','2024-08-01 08:15:32'),(28,'#ddf0e1','天藍色','2024-08-01 08:15:32','2024-08-01 08:15:32'),(29,'#ddf0e1','天藍色','2024-08-01 08:15:32','2024-08-01 08:15:32'),(30,'#cccccc','灰色','2024-08-01 08:15:32','2024-08-01 08:15:32'),(31,'#cccccc','灰色','2024-08-01 08:15:32','2024-08-01 08:15:32'),(32,'#cccccc','灰色','2024-08-01 08:15:32','2024-08-01 08:15:32'),(33,'#334455','深藍色','2024-08-01 08:15:32','2024-08-01 08:15:32'),(34,'#334455','深藍色','2024-08-01 08:15:32','2024-08-01 08:15:32'),(35,'#334455','深藍色','2024-08-01 08:15:32','2024-08-01 08:15:32'),(45,'#ffffff','天藍色','2024-08-01 08:48:29','2024-08-01 08:48:29'),(46,'#ffdddd','淡粉紅','2024-08-01 08:48:29','2024-08-01 08:48:29'),(47,'#ffdddd','淡粉紅','2024-08-01 08:48:29','2024-08-01 08:48:29'),(48,'#ddffbb','青綠色','2024-08-01 08:51:57','2024-08-01 08:51:57'),(49,'#ddffbb','青綠色','2024-08-01 08:51:57','2024-08-01 08:51:57'),(50,'#ddf0ff','淡藍色','2024-08-01 08:51:57','2024-08-01 08:51:57'),(51,'#ddf0ff','淡藍色','2024-08-01 08:51:57','2024-08-01 08:51:57'),(52,'#ffffff','白色','2024-08-01 08:55:14','2024-08-01 08:55:14'),(53,'#ffffff','白色','2024-08-01 08:55:14','2024-08-01 08:55:14'),(54,'#ddf0ff','淡藍色','2024-08-01 08:55:14','2024-08-01 08:55:14'),(55,'#ddf0ff','淡藍色','2024-08-01 08:55:14','2024-08-01 08:55:14'),(56,'#ffffff','白色','2024-08-01 08:55:14','2024-08-01 08:55:14'),(57,'#ddf0ff','淡藍色','2024-08-01 08:55:14','2024-08-01 08:55:14'),(58,'#ffffff','白色','2024-08-01 09:05:04','2024-08-01 09:05:04'),(59,'#ffffff','白色','2024-08-01 09:05:04','2024-08-01 09:05:04'),(60,'#cccccc','灰色','2024-08-01 09:05:04','2024-08-01 09:05:04'),(61,'#cccccc','灰色','2024-08-01 09:05:04','2024-08-01 09:05:04'),(62,'#ffffff','白色','2024-08-01 09:05:04','2024-08-01 09:05:04'),(63,'#cccccc','灰色','2024-08-01 09:05:04','2024-08-01 09:05:04'),(64,'#334455','深藍色','2024-08-01 09:08:10','2024-08-01 09:08:10'),(65,'#334455','深藍色','2024-08-01 09:08:10','2024-08-01 09:08:10'),(66,'#334455','深藍色','2024-08-01 09:08:10','2024-08-01 09:08:10'),(67,'#334455','深藍色','2024-08-01 09:08:10','2024-08-01 09:08:10'),(68,'#ddf0ff','淺藍色','2024-08-01 09:10:19','2024-08-01 09:10:19'),(69,'#ddf0ff','淺藍色','2024-08-01 09:10:19','2024-08-01 09:10:19'),(70,'#bb7744','咖啡色','2024-08-01 09:10:19','2024-08-01 09:10:19'),(71,'#bb7744','咖啡色','2024-08-01 09:10:19','2024-08-01 09:10:19'),(72,'#334455','深藍色','2024-08-01 09:12:11','2024-08-01 09:12:11'),(73,'#334455','深藍色','2024-08-01 09:12:11','2024-08-01 09:12:11'),(74,'#bb7744','咖啡色','2024-08-01 09:12:11','2024-08-01 09:12:11'),(75,'#bb7744','咖啡色','2024-08-01 09:12:11','2024-08-01 09:12:11'),(76,'#ffffff','深藍色','2024-08-01 09:14:31','2024-08-01 09:14:31'),(77,'#ffd3d3','淡粉紅','2024-08-01 09:14:31','2024-08-01 09:14:31'),(78,'#ffffff','白色','2024-08-01 09:16:19','2024-08-01 09:16:19'),(79,'#d3f0ff','天藍色','2024-08-01 09:16:19','2024-08-01 09:16:19');
/*!40000 ALTER TABLE `color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hot`
--

DROP TABLE IF EXISTS `hot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hot` (
  `hot_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`hot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hot`
--

LOCK TABLES `hot` WRITE;
/*!40000 ALTER TABLE `hot` DISABLE KEYS */;
/*!40000 ALTER TABLE `hot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hot_product`
--

DROP TABLE IF EXISTS `hot_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hot_product` (
  `hot_id` bigint unsigned NOT NULL,
  `product_id` bigint unsigned NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`hot_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `hot_product_ibfk_1` FOREIGN KEY (`hot_id`) REFERENCES `hot` (`hot_id`),
  CONSTRAINT `hot_product_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hot_product`
--

LOCK TABLES `hot_product` WRITE;
/*!40000 ALTER TABLE `hot_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `hot_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `image_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`image_id`),
  UNIQUE KEY `image_url` (`image_url`),
  KEY `image_url_2` (`image_url`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,'http://3.113.167.117/uploads/201807201824_main.jpg','2024-08-01 08:01:03','2024-08-01 08:01:03'),(2,'http://3.113.167.117/uploads/7b21272c.jpeg','2024-08-01 08:01:03','2024-08-01 08:01:03'),(3,'http://3.113.167.117/uploads/4e964d50.jpeg','2024-08-01 08:01:03','2024-08-01 08:01:03'),(4,'http://3.113.167.117/uploads/8ae3f36c.jpeg','2024-08-01 08:01:03','2024-08-01 08:01:03'),(9,'http://3.113.167.117/uploads/201807202140_main.jpg','2024-08-01 08:08:33','2024-08-01 08:08:33'),(10,'http://3.113.167.117/uploads/201807202140_0.jpg','2024-08-01 08:08:33','2024-08-01 08:08:33'),(11,'http://3.113.167.117/uploads/201807202140_1.jpg','2024-08-01 08:08:33','2024-08-01 08:08:33'),(12,'http://3.113.167.117/uploads/201902191242_0.jpg','2024-08-01 08:08:33','2024-08-01 08:08:33'),(13,'http://3.113.167.117/uploads/201902191242_1.jpg','2024-08-01 08:08:33','2024-08-01 08:08:33'),(14,'http://3.113.167.117/uploads/201807202150_main.jpg','2024-08-01 08:11:44','2024-08-01 08:11:44'),(15,'http://3.113.167.117/uploads/201807202150_0.jpg','2024-08-01 08:11:44','2024-08-01 08:11:44'),(16,'http://3.113.167.117/uploads/201807202150_1.jpg','2024-08-01 08:11:44','2024-08-01 08:11:44'),(17,'http://3.113.167.117/uploads/201902191245_0.jpg','2024-08-01 08:11:44','2024-08-01 08:11:44'),(18,'http://3.113.167.117/uploads/201902191245_1.jpg','2024-08-01 08:11:44','2024-08-01 08:11:44'),(19,'http://3.113.167.117/uploads/201807202157_main.jpg','2024-08-01 08:15:32','2024-08-01 08:15:32'),(20,'http://3.113.167.117/uploads/201807242232_0.jpg','2024-08-01 08:15:32','2024-08-01 08:15:32'),(21,'http://3.113.167.117/uploads/201807202157_1.jpg','2024-08-01 08:15:32','2024-08-01 08:15:32'),(22,'http://3.113.167.117/uploads/201902191247_0.jpg','2024-08-01 08:15:32','2024-08-01 08:15:32'),(23,'http://3.113.167.117/uploads/201902191247_1.jpg','2024-08-01 08:15:32','2024-08-01 08:15:32'),(28,'http://3.113.167.117/uploads/5a54ebc4.jpeg','2024-08-01 08:48:29','2024-08-01 08:48:29'),(29,'http://3.113.167.117/uploads/01.jpeg','2024-08-01 08:48:29','2024-08-01 08:48:29'),(30,'http://3.113.167.117/uploads/03.jpeg','2024-08-01 08:48:29','2024-08-01 08:48:29'),(31,'http://3.113.167.117/uploads/02.jpeg','2024-08-01 08:48:29','2024-08-01 08:48:29'),(32,'http://3.113.167.117/uploads/04.jpeg','2024-08-01 08:48:29','2024-08-01 08:48:29'),(33,'http://3.113.167.117/uploads/05.jpg','2024-08-01 08:51:57','2024-08-01 08:51:57'),(34,'http://3.113.167.117/uploads/06.jpg','2024-08-01 08:51:57','2024-08-01 08:51:57'),(35,'http://3.113.167.117/uploads/08.jpg','2024-08-01 08:51:58','2024-08-01 08:51:58'),(36,'http://3.113.167.117/uploads/07.jpg','2024-08-01 08:51:58','2024-08-01 08:51:58'),(37,'http://3.113.167.117/uploads/09.jpg','2024-08-01 08:51:58','2024-08-01 08:51:58'),(38,'http://3.113.167.117/uploads/201807242211_main.jpg','2024-08-01 08:55:14','2024-08-01 08:55:14'),(39,'http://3.113.167.117/uploads/201807242211_0.jpg','2024-08-01 08:55:15','2024-08-01 08:55:15'),(40,'http://3.113.167.117/uploads/201807242211_1.jpg','2024-08-01 08:55:15','2024-08-01 08:55:15'),(41,'http://3.113.167.117/uploads/56165161.jpg','2024-08-01 08:55:15','2024-08-01 08:55:15'),(42,'http://3.113.167.117/uploads/516516.jpg','2024-08-01 08:55:15','2024-08-01 08:55:15'),(43,'http://3.113.167.117/uploads/201807242216_main.jpg','2024-08-01 09:05:04','2024-08-01 09:05:04'),(44,'http://3.113.167.117/uploads/201807242216_0.jpg','2024-08-01 09:05:05','2024-08-01 09:05:05'),(45,'http://3.113.167.117/uploads/201807242216_1.jpg','2024-08-01 09:05:05','2024-08-01 09:05:05'),(46,'http://3.113.167.117/uploads/561561.jpg','2024-08-01 09:05:05','2024-08-01 09:05:05'),(47,'http://3.113.167.117/uploads/896532.jpg','2024-08-01 09:05:05','2024-08-01 09:05:05'),(48,'http://3.113.167.117/uploads/201807242222_main.jpg','2024-08-01 09:08:10','2024-08-01 09:08:10'),(49,'http://3.113.167.117/uploads/201807242222_0.jpg','2024-08-01 09:08:10','2024-08-01 09:08:10'),(50,'http://3.113.167.117/uploads/201807242222_1.jpg','2024-08-01 09:08:10','2024-08-01 09:08:10'),(51,'http://3.113.167.117/uploads/789456.jpg','2024-08-01 09:08:10','2024-08-01 09:08:10'),(52,'http://3.113.167.117/uploads/8469165189.jpg','2024-08-01 09:08:10','2024-08-01 09:08:10'),(53,'http://3.113.167.117/uploads/201807242228_main.jpg','2024-08-01 09:10:19','2024-08-01 09:10:19'),(54,'http://3.113.167.117/uploads/201807242228_0.jpg','2024-08-01 09:10:19','2024-08-01 09:10:19'),(55,'http://3.113.167.117/uploads/201807242228_1.jpg','2024-08-01 09:10:19','2024-08-01 09:10:19'),(56,'http://3.113.167.117/uploads/49841891894.jpg','2024-08-01 09:10:19','2024-08-01 09:10:19'),(57,'http://3.113.167.117/uploads/894981561891.jpg','2024-08-01 09:10:19','2024-08-01 09:10:19'),(58,'http://3.113.167.117/uploads/201807242230_main.jpg','2024-08-01 09:12:11','2024-08-01 09:12:11'),(59,'http://3.113.167.117/uploads/201807242230_0.jpg','2024-08-01 09:12:11','2024-08-01 09:12:11'),(60,'http://3.113.167.117/uploads/201807242230_1.jpg','2024-08-01 09:12:11','2024-08-01 09:12:11'),(61,'http://3.113.167.117/uploads/98115891981.jpg','2024-08-01 09:12:11','2024-08-01 09:12:11'),(62,'http://3.113.167.117/uploads/871981891818.jpg','2024-08-01 09:12:11','2024-08-01 09:12:11'),(63,'http://3.113.167.117/uploads/201807242232_main.jpg','2024-08-01 09:14:31','2024-08-01 09:14:31'),(64,'http://3.113.167.117/uploads/201807202157_0.jpg','2024-08-01 09:14:31','2024-08-01 09:14:31'),(65,'http://3.113.167.117/uploads/201807242232_1.jpg','2024-08-01 09:14:31','2024-08-01 09:14:31'),(66,'http://3.113.167.117/uploads/1585616581851.jpg','2024-08-01 09:14:31','2024-08-01 09:14:31'),(67,'http://3.113.167.117/uploads/98189189981.jpg','2024-08-01 09:14:31','2024-08-01 09:14:31'),(68,'http://3.113.167.117/uploads/201807242234_main.jpg','2024-08-01 09:16:19','2024-08-01 09:16:19'),(69,'http://3.113.167.117/uploads/201807242234_0.jpg','2024-08-01 09:16:19','2024-08-01 09:16:19'),(70,'http://3.113.167.117/uploads/201807242234_1.jpg','2024-08-01 09:16:19','2024-08-01 09:16:19'),(71,'http://3.113.167.117/uploads/51898198198189.jpg','2024-08-01 09:16:20','2024-08-01 09:16:20'),(72,'http://3.113.167.117/uploads/891919819841.jpg','2024-08-01 09:16:20','2024-08-01 09:16:20');
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `order_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned DEFAULT NULL,
  `order_number` varchar(255) NOT NULL,
  `shipping` varchar(255) NOT NULL,
  `payment` varchar(255) NOT NULL,
  `subtotal` decimal(10,2) NOT NULL,
  `freight` decimal(10,2) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `prime` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,1,'2024080219293925112','delivery','credit_card',1234.00,14.00,1300.00,'a8ef6539918a0f7bb8263170cbb7959a8e137907ff636fff3faa17a6870d0f78','failed','2024-08-02 11:29:39','2024-08-02 11:29:39'),(2,1,'2024080411350945310','delivery','credit_card',1234.00,14.00,1300.00,'a8ef6539918a0f7bb8263170cbb7959a8e137907ff636fff3faa17a6870d0f78','failed','2024-08-04 03:35:09','2024-08-04 03:35:10'),(3,1,'2024080411362113152','delivery','credit_card',1234.00,14.00,1300.00,'1f2a7dd30b8ebe7d7efb91f3795fe149842e21ccb65fd85392d53161eae73819','paid','2024-08-04 03:36:21','2024-08-04 03:36:21'),(4,2,'2024080412594832348','delivery','credit_card',799.00,30.00,829.00,'6bdac7dc6de6ffd1a6f9e3bfc95565b018d6e1c618b66a184e2c13fa77c239d3','paid','2024-08-04 04:59:48','2024-08-04 04:59:49'),(5,2,'2024080413010055568','delivery','credit_card',2397.00,30.00,2427.00,'c8def2fc8aba2947b897e45504b29f139f1f9a721a3be1a108627b8c81957c46','paid','2024-08-04 05:01:00','2024-08-04 05:01:01'),(6,2,'2024080415111736318','delivery','credit_card',4995.00,30.00,5025.00,'9d47d83f1f47749134fc0673ca9d4ad33f8b0088eb9f5d1a6d6d4d6551ffe2d4','paid','2024-08-04 07:11:17','2024-08-04 07:11:18'),(7,2,'2024080415281122710','delivery','credit_card',1598.00,30.00,1628.00,'c869629abe7b819da5bc663f8bb9c6b2836a349e9ec433c3084dc0c923f113be','paid','2024-08-04 07:28:11','2024-08-04 07:28:12'),(8,2,'2024080415284243889','delivery','credit_card',3196.00,30.00,3226.00,'3f105d1db7085585cdcb9a2e9532e2f9ec6509171cd7360c914636957a848a77','paid','2024-08-04 07:28:42','2024-08-04 07:28:42'),(9,6,'2024080418560125884','delivery','credit_card',2396.00,30.00,2426.00,'b8715e0a6b8f92f6b67a475049c08e3dbd9b327106ce83fcd1f7d91ad4802548','paid','2024-08-04 10:56:01','2024-08-04 10:56:01'),(10,6,'2024080418584513804','delivery','credit_card',2396.00,30.00,2426.00,'9657c0071e53e8677aba67821ebff5440d17c6e798225c63ed78a9f97493c406','paid','2024-08-04 10:58:45','2024-08-04 10:58:46'),(11,6,'2024080418593245634','delivery','credit_card',9596.00,30.00,9626.00,'6b83c4ada33d1e95cf317b06f24f6ac03db793820fe0368dd037b03f95e1eb96','paid','2024-08-04 10:59:32','2024-08-04 10:59:32'),(12,6,'2024080419164830496','delivery','credit_card',1797.00,30.00,1827.00,'f669126e081cf5b7531914ac42f0cec6c3c70ac79afb0ea545936206daa65872','paid','2024-08-04 11:16:48','2024-08-04 11:16:48'),(13,6,'2024080422212436278','delivery','credit_card',1198.00,30.00,1228.00,'7a1003555d5846961ee9fa4657aa469b671797c27fda48c66215e73b89563940','paid','2024-08-04 14:21:24','2024-08-04 14:21:24'),(14,6,'2024080422223155040','delivery','credit_card',7998.00,30.00,8028.00,'cdf2148f019df122211d3a6a92047ad92cdfa18cd7df6014d5c69469524cc99a','paid','2024-08-04 14:22:31','2024-08-04 14:22:32'),(15,6,'2024080422251356371','delivery','credit_card',1797.00,30.00,1827.00,'85a7d2977351bdc22892043605dc607836784d56e98919e6e257188f424260c9','paid','2024-08-04 14:25:13','2024-08-04 14:25:15');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `order_item_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint unsigned NOT NULL,
  `product_id` bigint unsigned NOT NULL,
  `variant_id` bigint DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `color_name` varchar(255) DEFAULT NULL,
  `color_code` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `quantity` int NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_item_id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  KEY `variant_id` (`variant_id`),
  CONSTRAINT `order_item_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`),
  CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `order_item_ibfk_3` FOREIGN KEY (`variant_id`) REFERENCES `variant` (`variant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,1,10,NULL,'活力花紋長筒牛仔褲',1299.00,'淺藍','DDF0FF','M',1,'2024-08-02 11:29:39','2024-08-02 11:29:39'),(2,2,15,NULL,'活力花紋長筒牛仔褲',1299.00,'淺藍','DDF0FF','M',1,'2024-08-04 03:35:09','2024-08-04 03:35:09'),(3,3,15,NULL,'活力花紋長筒牛仔褲',1299.00,'淺藍','DDF0FF','M',1,'2024-08-04 03:36:21','2024-08-04 03:36:21'),(4,4,1,NULL,'前開衩扭結洋裝',799.00,'白色','#ffffff','M',1,'2024-08-04 04:59:48','2024-08-04 04:59:48'),(5,5,1,NULL,'前開衩扭結洋裝',799.00,'白色','#ffffff','M',3,'2024-08-04 05:01:00','2024-08-04 05:01:00'),(6,6,9,NULL,'精緻扭結洋裝',999.00,'淡粉紅','#ffdddd','M',5,'2024-08-04 07:11:17','2024-08-04 07:11:17'),(7,7,1,NULL,'前開衩扭結洋裝',799.00,'白色','#ffffff','M',2,'2024-08-04 07:28:11','2024-08-04 07:28:11'),(8,8,1,NULL,'前開衩扭結洋裝',799.00,'淡綠色','#ddffbb','L',4,'2024-08-04 07:28:42','2024-08-04 07:28:42'),(9,9,3,NULL,'透肌澎澎防曬襯衫',599.00,'淡綠色','#ddffbb','M',4,'2024-08-04 10:56:01','2024-08-04 10:56:01'),(10,10,3,NULL,'透肌澎澎防曬襯衫',599.00,'淡綠色','#ddffbb','L',4,'2024-08-04 10:58:45','2024-08-04 10:58:45'),(11,11,12,NULL,'時尚輕鬆休閒西裝',2399.00,'白色','#ffffff','M',4,'2024-08-04 10:59:32','2024-08-04 10:59:32'),(12,12,3,NULL,'透肌澎澎防曬襯衫',599.00,'灰色','#cccccc','L',3,'2024-08-04 11:16:48','2024-08-04 11:16:48'),(13,13,3,NULL,'透肌澎澎防曬襯衫',599.00,'灰色','#cccccc','S',2,'2024-08-04 14:21:24','2024-08-04 14:21:24'),(14,14,13,NULL,'經典商務西裝',3999.00,'深藍色','#334455','M',2,'2024-08-04 14:22:31','2024-08-04 14:22:31'),(15,15,3,NULL,'透肌澎澎防曬襯衫',599.00,'灰色','#cccccc','L',3,'2024-08-04 14:25:13','2024-08-04 14:25:13');
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` text,
  `price` decimal(10,2) NOT NULL,
  `texture` varchar(255) DEFAULT NULL,
  `wash` varchar(255) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `story` text,
  `main_image` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'women','前開衩扭結洋裝','實品顏色依單品照為主',799.00,'棉 100% 厚薄：薄 彈性：無','機洗(水溫700度)','素材產地 / 中國 加工產地 / 中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://3.113.167.117/uploads/201807201824_main.jpg','2024-08-01 08:01:02','2024-08-01 08:01:02'),(3,'women','透肌澎澎防曬襯衫','實品顏色依單品照為主',599.00,'棉 100% 厚薄：薄 彈性：無','機洗(水溫70度)','素材產地 / 中國 加工產地 / 中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://3.113.167.117/uploads/201807202140_main.jpg','2024-08-01 08:08:33','2024-08-01 08:08:33'),(4,'women','小扇紋細織上衣','實品顏色依單品照為主',599.00,'棉 100% 厚薄：薄 彈性：無','機洗(水溫70度)','素材產地 / 中國 加工產地 / 中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://3.113.167.117/uploads/201807202150_main.jpg','2024-08-01 08:11:44','2024-08-01 08:11:44'),(5,'women','活力花紋長筒牛仔褲','實品顏色依單品照為主',1299.00,'棉 100% 厚薄：薄 彈性：無','機洗(水溫70度)','素材產地 / 中國 加工產地 / 中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://3.113.167.117/uploads/201807202157_main.jpg','2024-08-01 08:15:32','2024-08-01 08:15:32'),(9,'women','精緻扭結洋裝','實品顏色依單品照為主',999.00,'棉 100% 厚薄：薄 彈性：無','機洗(水溫70度)','素材產地 / 中國 加工產地 / 中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://3.113.167.117/uploads/5a54ebc4.jpeg','2024-08-01 08:48:29','2024-08-01 08:48:29'),(10,'women','透肌澎澎薄紗襯衫','實品顏色依單品照為主',999.00,'棉 100% 厚薄：薄 彈性：無','機洗(水溫7000度)','素材產地 / 越南 加工產地 / 越南','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://3.113.167.117/uploads/05.jpg','2024-08-01 08:51:57','2024-08-01 08:51:57'),(11,'men','純色輕薄百搭襯衫','實品顏色依單品照為主',999.00,'棉 100% 厚薄：薄 彈性：無','機洗(水溫7000度)','素材產地 / 越南 加工產地 / 越南','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://3.113.167.117/uploads/201807242211_main.jpg','2024-08-01 08:55:14','2024-08-01 08:55:14'),(12,'men','時尚輕鬆休閒西裝','實品顏色依單品照為主',2399.00,'棉 100% 厚薄：薄 彈性：無','機洗(水溫7000度)','素材產地 / 中國 加工產地 / 中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://3.113.167.117/uploads/201807242216_main.jpg','2024-08-01 09:05:04','2024-08-01 09:05:04'),(13,'men','經典商務西裝','實品顏色依單品照為主',3999.00,'棉 100% 厚薄：薄 彈性：無','機洗(水溫7000度)','素材產地 / 中國 加工產地 / 中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://3.113.167.117/uploads/201807242222_main.jpg','2024-08-01 09:08:10','2024-08-01 09:08:10'),(14,'accessories','夏日海灘戶外遮陽帽','實品顏色依單品照為主',1499.00,'棉 100% 厚薄：薄 彈性：無','機洗(水溫7000度)','素材產地 / 中國 加工產地 / 中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://3.113.167.117/uploads/201807242228_main.jpg','2024-08-01 09:10:19','2024-08-01 09:10:19'),(15,'accessories','經典牛仔帽','實品顏色依單品照為主',799.00,'棉 100% 厚薄：薄 彈性：無','機洗(水溫7000度)','素材產地 / 中國 加工產地 / 中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://3.113.167.117/uploads/201807242230_main.jpg','2024-08-01 09:12:11','2024-08-01 09:12:11'),(16,'accessories','卡哇伊多功能隨身包','實品顏色依單品照為主',1299.00,'棉 100% 厚薄：薄 彈性：無','機洗(水溫7000度)','素材產地 / 中國 加工產地 / 中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://3.113.167.117/uploads/201807242232_main.jpg','2024-08-01 09:14:31','2024-08-01 09:14:31'),(17,'accessories','柔軟氣質羊毛圍巾','實品顏色依單品照為主',1799.00,'棉 100% 厚薄：薄 彈性：無','機洗(水溫7000度)','素材產地 / 中國 加工產地 / 中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://3.113.167.117/uploads/201807242234_main.jpg','2024-08-01 09:16:19','2024-08-01 09:16:19');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS `product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_image` (
  `product_id` bigint unsigned NOT NULL,
  `image_id` bigint unsigned NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`image_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `product_image_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `product_image_ibfk_2` FOREIGN KEY (`image_id`) REFERENCES `image` (`image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_image`
--

LOCK TABLES `product_image` WRITE;
/*!40000 ALTER TABLE `product_image` DISABLE KEYS */;
INSERT INTO `product_image` VALUES (1,1,'2024-08-01 08:01:03','2024-08-01 08:01:03'),(1,2,'2024-08-01 08:01:03','2024-08-01 08:01:03'),(1,3,'2024-08-01 08:01:03','2024-08-01 08:01:03'),(1,4,'2024-08-01 08:01:03','2024-08-01 08:01:03'),(3,9,'2024-08-01 08:08:33','2024-08-01 08:08:33'),(3,10,'2024-08-01 08:08:33','2024-08-01 08:08:33'),(3,11,'2024-08-01 08:08:33','2024-08-01 08:08:33'),(3,12,'2024-08-01 08:08:33','2024-08-01 08:08:33'),(3,13,'2024-08-01 08:08:33','2024-08-01 08:08:33'),(4,14,'2024-08-01 08:11:44','2024-08-01 08:11:44'),(4,15,'2024-08-01 08:11:44','2024-08-01 08:11:44'),(4,16,'2024-08-01 08:11:44','2024-08-01 08:11:44'),(4,17,'2024-08-01 08:11:44','2024-08-01 08:11:44'),(4,18,'2024-08-01 08:11:44','2024-08-01 08:11:44'),(5,19,'2024-08-01 08:15:32','2024-08-01 08:15:32'),(5,20,'2024-08-01 08:15:32','2024-08-01 08:15:32'),(5,21,'2024-08-01 08:15:32','2024-08-01 08:15:32'),(5,22,'2024-08-01 08:15:32','2024-08-01 08:15:32'),(5,23,'2024-08-01 08:15:32','2024-08-01 08:15:32'),(9,28,'2024-08-01 08:48:29','2024-08-01 08:48:29'),(9,29,'2024-08-01 08:48:29','2024-08-01 08:48:29'),(9,30,'2024-08-01 08:48:29','2024-08-01 08:48:29'),(9,31,'2024-08-01 08:48:29','2024-08-01 08:48:29'),(9,32,'2024-08-01 08:48:29','2024-08-01 08:48:29'),(10,33,'2024-08-01 08:51:57','2024-08-01 08:51:57'),(10,34,'2024-08-01 08:51:57','2024-08-01 08:51:57'),(10,35,'2024-08-01 08:51:58','2024-08-01 08:51:58'),(10,36,'2024-08-01 08:51:58','2024-08-01 08:51:58'),(10,37,'2024-08-01 08:51:58','2024-08-01 08:51:58'),(11,38,'2024-08-01 08:55:14','2024-08-01 08:55:14'),(11,39,'2024-08-01 08:55:15','2024-08-01 08:55:15'),(11,40,'2024-08-01 08:55:15','2024-08-01 08:55:15'),(11,41,'2024-08-01 08:55:15','2024-08-01 08:55:15'),(11,42,'2024-08-01 08:55:15','2024-08-01 08:55:15'),(12,43,'2024-08-01 09:05:04','2024-08-01 09:05:04'),(12,44,'2024-08-01 09:05:05','2024-08-01 09:05:05'),(12,45,'2024-08-01 09:05:05','2024-08-01 09:05:05'),(12,46,'2024-08-01 09:05:05','2024-08-01 09:05:05'),(12,47,'2024-08-01 09:05:05','2024-08-01 09:05:05'),(13,48,'2024-08-01 09:08:10','2024-08-01 09:08:10'),(13,49,'2024-08-01 09:08:10','2024-08-01 09:08:10'),(13,50,'2024-08-01 09:08:10','2024-08-01 09:08:10'),(13,51,'2024-08-01 09:08:10','2024-08-01 09:08:10'),(13,52,'2024-08-01 09:08:10','2024-08-01 09:08:10'),(14,53,'2024-08-01 09:10:19','2024-08-01 09:10:19'),(14,54,'2024-08-01 09:10:19','2024-08-01 09:10:19'),(14,55,'2024-08-01 09:10:19','2024-08-01 09:10:19'),(14,56,'2024-08-01 09:10:19','2024-08-01 09:10:19'),(14,57,'2024-08-01 09:10:19','2024-08-01 09:10:19'),(15,58,'2024-08-01 09:12:11','2024-08-01 09:12:11'),(15,59,'2024-08-01 09:12:11','2024-08-01 09:12:11'),(15,60,'2024-08-01 09:12:11','2024-08-01 09:12:11'),(15,61,'2024-08-01 09:12:11','2024-08-01 09:12:11'),(15,62,'2024-08-01 09:12:11','2024-08-01 09:12:11'),(16,63,'2024-08-01 09:14:31','2024-08-01 09:14:31'),(16,64,'2024-08-01 09:14:31','2024-08-01 09:14:31'),(16,65,'2024-08-01 09:14:31','2024-08-01 09:14:31'),(16,66,'2024-08-01 09:14:31','2024-08-01 09:14:31'),(16,67,'2024-08-01 09:14:31','2024-08-01 09:14:31'),(17,68,'2024-08-01 09:16:19','2024-08-01 09:16:19'),(17,69,'2024-08-01 09:16:19','2024-08-01 09:16:19'),(17,70,'2024-08-01 09:16:19','2024-08-01 09:16:19'),(17,71,'2024-08-01 09:16:20','2024-08-01 09:16:20'),(17,72,'2024-08-01 09:16:20','2024-08-01 09:16:20');
/*!40000 ALTER TABLE `product_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipient`
--

DROP TABLE IF EXISTS `recipient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipient` (
  `recipient_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(30) NOT NULL,
  `email` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `time` varchar(255) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`recipient_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `recipient_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipient`
--

LOCK TABLES `recipient` WRITE;
/*!40000 ALTER TABLE `recipient` DISABLE KEYS */;
INSERT INTO `recipient` VALUES (1,1,'Luke','0987654321','luke@gmail.com','市政府站','morning','2024-08-02 11:29:39','2024-08-02 11:29:39'),(2,2,'Luke','0987654321','luke@gmail.com','市政府站','morning','2024-08-04 03:35:09','2024-08-04 03:35:09'),(3,3,'Luke','0987654321','luke@gmail.com','市政府站','morning','2024-08-04 03:36:21','2024-08-04 03:36:21'),(4,4,'許阿柴','0987654321','james010203@gmail.com','台北市信義區市府路1號','morning','2024-08-04 04:59:48','2024-08-04 04:59:48'),(5,5,'許阿柴','0987654321','james010203@gmail.com','台北市信義區市府路1號','morning','2024-08-04 05:01:00','2024-08-04 05:01:00'),(6,6,'許阿柴','0987654321','james010203@gmail.com','台北市信義區市府路1號','morning','2024-08-04 07:11:17','2024-08-04 07:11:17'),(7,7,'許阿柴','0987654321','james010203@gmail.com','台北市信義區市府路1號','morning','2024-08-04 07:28:11','2024-08-04 07:28:11'),(8,8,'許阿柴','0987654321','james010203@gmail.com','台北市信義區市府路1號','morning','2024-08-04 07:28:42','2024-08-04 07:28:42'),(9,9,'許阿柴','0987654321','james010203@gmail.com','台北市信義區市府路1號','morning','2024-08-04 10:56:01','2024-08-04 10:56:01'),(10,10,'許阿柴','0987654321','james010203@gmail.com','台北市信義區市府路1號','morning','2024-08-04 10:58:45','2024-08-04 10:58:45'),(11,11,'許阿柴','0987654321','james010203@gmail.com','台北市信義區市府路1號','morning','2024-08-04 10:59:32','2024-08-04 10:59:32'),(12,12,'許阿柴','0987654321','james010203@gmail.com','台北市信義區市府路1號','morning','2024-08-04 11:16:48','2024-08-04 11:16:48'),(13,13,'許阿柴','0987654321','james010203@gmail.com','台北市信義區市府路1號','morning','2024-08-04 14:21:24','2024-08-04 14:21:24'),(14,14,'許阿柴','0987654321','james010203@gmail.com','台北市信義區市府路1號','morning','2024-08-04 14:22:31','2024-08-04 14:22:31'),(15,15,'許阿柴','0987654321','james010203@gmail.com','台北市信義區市府路1號','morning','2024-08-04 14:25:13','2024-08-04 14:25:13');
/*!40000 ALTER TABLE `recipient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `provider` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'facebook','許舜慈','yoyojames888@yahoo.com.tw','SOCIAL_LOGIN_NO_PASSWORD','https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=8025376567482994&height=50&width=50&ext=1725154959&hash=AbYFw4ZgctMGApoLAIChdM8n','2024-08-02 01:42:39','2024-08-02 01:42:39'),(2,'native','stylishtest','stylishtest_15484261@test.121','$2a$10$WLRzJtAGG3Ia5rDYIThND.gf.SFpQ3v/QFOIjRUAnRqoBQxHx3Tga','default_url','2024-08-04 04:59:20','2024-08-04 04:59:20'),(3,'native','administrator','james56012623@gmail.com','$2a$10$u5shIp.cHm68wcu4f1RBhueZC9vsTniTqqZibaenFUt394yl110/.','default_url','2024-08-04 09:34:58','2024-08-04 09:34:58'),(4,'native','webtest','web@test','$2a$10$XpXQyq0qaYJDqkk9L.x5W.mUFWCicTdYPeLPvbz/cbe6MVvwOjfui','default_url','2024-08-04 09:46:57','2024-08-04 09:46:57'),(5,'native','1','11@11','$2a$10$TsMJIMqbQwFi1GP75jKRpu1kbEC6C7NTjJCrCAiPT2ue/FE/.jf5G','default_url','2024-08-04 09:53:15','2024-08-04 09:53:15'),(6,'native','許舜慈','test@11','$2a$10$WYRhy7N/PEEHKDWvq3b3FO1eTFDTv221ejduyEbqX8uMtOxQVyWSK','default_url','2024-08-04 09:54:37','2024-08-04 09:54:37');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variant`
--

DROP TABLE IF EXISTS `variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variant` (
  `variant_id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint unsigned DEFAULT NULL,
  `color_code` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`variant_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `variant_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variant`
--

LOCK TABLES `variant` WRITE;
/*!40000 ALTER TABLE `variant` DISABLE KEYS */;
INSERT INTO `variant` VALUES (1,1,'#ffffff','S',0,'2024-08-01 08:01:03','2024-08-03 17:08:01'),(2,1,'#ffffff','M',8,'2024-08-01 08:01:03','2024-08-01 08:01:03'),(3,1,'#ffffff','L',9,'2024-08-01 08:01:03','2024-08-01 08:01:03'),(4,1,'#ddffbb','S',2,'2024-08-01 08:01:03','2024-08-01 08:01:03'),(5,1,'#ddffbb','L',4,'2024-08-01 08:01:03','2024-08-01 08:01:03'),(6,1,'#cccccc','S',1,'2024-08-01 08:01:03','2024-08-01 08:01:03'),(7,1,'#cccccc','M',2,'2024-08-01 08:01:03','2024-08-01 08:01:03'),(8,1,'#cccccc','L',3,'2024-08-01 08:01:03','2024-08-01 08:01:03'),(15,3,'#cccccc','S',5,'2024-08-01 08:08:33','2024-08-01 08:08:33'),(16,3,'#ddffbb','S',2,'2024-08-01 08:08:33','2024-08-01 08:08:33'),(17,3,'#ddffbb','L',4,'2024-08-01 08:08:33','2024-08-01 08:08:33'),(18,3,'#cccccc','M',2,'2024-08-01 08:08:33','2024-08-01 08:08:33'),(19,3,'#cccccc','L',3,'2024-08-01 08:08:33','2024-08-01 08:08:33'),(20,3,'#ddffbb','M',5,'2024-08-01 08:08:33','2024-08-01 08:08:33'),(21,4,'#bb7844','S',5,'2024-08-01 08:11:44','2024-08-01 08:11:44'),(22,4,'#bb7844','M',2,'2024-08-01 08:11:44','2024-08-01 08:11:44'),(23,4,'#ddffbb','S',4,'2024-08-01 08:11:44','2024-08-01 08:11:44'),(24,4,'#cccccc','M',2,'2024-08-01 08:11:44','2024-08-01 08:11:44'),(25,4,'#cccccc','S',3,'2024-08-01 08:11:44','2024-08-01 08:11:44'),(26,4,'#ddffbb','M',5,'2024-08-01 08:11:44','2024-08-01 08:11:44'),(27,5,'#ddf0e1','S',5,'2024-08-01 08:15:32','2024-08-01 08:15:32'),(28,5,'#ddf0e1','M',2,'2024-08-01 08:15:32','2024-08-01 08:15:32'),(29,5,'#ddf0e1','L',4,'2024-08-01 08:15:32','2024-08-01 08:15:32'),(30,5,'#cccccc','S',2,'2024-08-01 08:15:32','2024-08-01 08:15:32'),(31,5,'#cccccc','M',3,'2024-08-01 08:15:32','2024-08-01 08:15:32'),(32,5,'#cccccc','L',5,'2024-08-01 08:15:32','2024-08-01 08:15:32'),(33,5,'#334455','S',1,'2024-08-01 08:15:32','2024-08-01 08:15:32'),(34,5,'#334455','M',2,'2024-08-01 08:15:32','2024-08-01 08:15:32'),(35,5,'#334455','L',3,'2024-08-01 08:15:32','2024-08-01 08:15:32'),(45,9,'#ffffff','M',5,'2024-08-01 08:48:29','2024-08-01 08:48:29'),(46,9,'#ffdddd','S',4,'2024-08-01 08:48:29','2024-08-01 08:48:29'),(47,9,'#ffdddd','M',6,'2024-08-01 08:48:29','2024-08-01 08:48:29'),(48,10,'#ddffbb','M',5,'2024-08-01 08:51:57','2024-08-01 08:51:57'),(49,10,'#ddffbb','L',4,'2024-08-01 08:51:57','2024-08-01 08:51:57'),(50,10,'#ddf0ff','M',6,'2024-08-01 08:51:57','2024-08-01 08:51:57'),(51,10,'#ddf0ff','L',7,'2024-08-01 08:51:57','2024-08-01 08:51:57'),(52,11,'#ffffff','M',5,'2024-08-01 08:55:14','2024-08-01 08:55:14'),(53,11,'#ffffff','L',4,'2024-08-01 08:55:14','2024-08-01 08:55:14'),(54,11,'#ddf0ff','M',6,'2024-08-01 08:55:14','2024-08-01 08:55:14'),(55,11,'#ddf0ff','L',7,'2024-08-01 08:55:14','2024-08-01 08:55:14'),(56,11,'#ffffff','XL',3,'2024-08-01 08:55:14','2024-08-01 08:55:14'),(57,11,'#ddf0ff','XL',4,'2024-08-01 08:55:14','2024-08-01 08:55:14'),(58,12,'#ffffff','S',5,'2024-08-01 09:05:04','2024-08-01 09:05:04'),(59,12,'#ffffff','M',4,'2024-08-01 09:05:04','2024-08-01 09:05:04'),(60,12,'#cccccc','S',6,'2024-08-01 09:05:04','2024-08-01 09:05:04'),(61,12,'#cccccc','M',7,'2024-08-01 09:05:04','2024-08-01 09:05:04'),(62,12,'#ffffff','L',3,'2024-08-01 09:05:04','2024-08-01 09:05:04'),(63,12,'#cccccc','L',4,'2024-08-01 09:05:04','2024-08-01 09:05:04'),(64,13,'#334455','S',1,'2024-08-01 09:08:10','2024-08-01 09:08:10'),(65,13,'#334455','M',2,'2024-08-01 09:08:10','2024-08-01 09:08:10'),(66,13,'#334455','L',3,'2024-08-01 09:08:10','2024-08-01 09:08:10'),(67,13,'#334455','XL',4,'2024-08-01 09:08:10','2024-08-01 09:08:10'),(68,14,'#ddf0ff','M',2,'2024-08-01 09:10:19','2024-08-01 09:10:19'),(69,14,'#ddf0ff','L',3,'2024-08-01 09:10:19','2024-08-01 09:10:19'),(70,14,'#bb7744','M',4,'2024-08-01 09:10:19','2024-08-01 09:10:19'),(71,14,'#bb7744','L',5,'2024-08-01 09:10:19','2024-08-01 09:10:19'),(72,15,'#334455','M',2,'2024-08-01 09:12:11','2024-08-01 09:12:11'),(73,15,'#334455','L',3,'2024-08-01 09:12:11','2024-08-01 09:12:11'),(74,15,'#bb7744','M',4,'2024-08-01 09:12:11','2024-08-01 09:12:11'),(75,15,'#bb7744','L',5,'2024-08-01 09:12:11','2024-08-01 09:12:11'),(76,16,'#ffffff','F',3,'2024-08-01 09:14:31','2024-08-01 09:17:48'),(77,16,'#ffd3d3','F',8,'2024-08-01 09:14:31','2024-08-01 09:17:48'),(78,17,'#ffffff','F',2,'2024-08-01 09:16:19','2024-08-01 09:17:48'),(79,17,'#d3f0ff','F',5,'2024-08-01 09:16:19','2024-08-01 09:17:48');
/*!40000 ALTER TABLE `variant` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-04 22:30:13
