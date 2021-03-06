-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: crdb
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `carrepair`
--

DROP TABLE IF EXISTS `carrepair`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrepair` (
  `carserviceid` int NOT NULL,
  `vehicleid` int NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  KEY `carserviceid` (`carserviceid`),
  KEY `vehicleid` (`vehicleid`),
  CONSTRAINT `carrepair_ibfk_1` FOREIGN KEY (`carserviceid`) REFERENCES `carservice` (`id`),
  CONSTRAINT `carrepair_ibfk_2` FOREIGN KEY (`vehicleid`) REFERENCES `vehicle` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrepair`
--

LOCK TABLES `carrepair` WRITE;
/*!40000 ALTER TABLE `carrepair` DISABLE KEYS */;
INSERT INTO `carrepair` VALUES (3,29634,NULL),(98,31580,NULL),(99,27171,NULL),(12,27738,NULL),(11,39191,NULL),(58,10351,NULL),(63,39612,NULL),(22,13181,NULL),(57,10533,NULL),(35,20992,NULL),(6,15202,NULL),(34,32052,NULL),(95,38886,NULL),(87,12262,NULL),(35,41054,NULL),(29,14454,NULL),(74,49414,NULL),(49,7886,NULL),(61,14063,NULL),(8,14911,NULL),(48,991,NULL),(27,2235,NULL),(99,5744,NULL),(65,46552,NULL),(22,31153,NULL),(32,38598,NULL),(48,34522,NULL),(49,4593,NULL),(61,28779,NULL),(66,38949,NULL),(22,41376,NULL),(88,26510,NULL),(2,48966,NULL),(23,42352,NULL),(8,46195,NULL),(36,37109,NULL),(34,37125,NULL),(34,6285,NULL),(41,32601,NULL),(4,43723,NULL),(93,34253,NULL),(69,5483,NULL),(76,10445,NULL),(19,39652,NULL),(33,2997,NULL),(64,19239,NULL),(36,15663,NULL),(89,5630,NULL),(85,47755,NULL),(39,32437,NULL),(89,45001,NULL),(82,45566,NULL),(58,43879,NULL),(48,36913,NULL),(5,42697,NULL),(37,7761,NULL),(85,19414,NULL),(7,35831,NULL),(73,47382,NULL),(6,12281,NULL),(97,16401,NULL),(15,10010,NULL),(17,24010,NULL),(85,27398,NULL),(89,3541,NULL),(99,8477,NULL),(99,19893,NULL),(51,38134,NULL),(60,49329,NULL),(26,31185,NULL),(38,12430,NULL),(66,48320,NULL),(88,26673,NULL),(25,39568,NULL),(19,34987,NULL),(76,4187,NULL),(35,27102,NULL),(43,10416,NULL),(12,8763,NULL),(90,34450,NULL),(74,26050,NULL),(24,18415,NULL),(58,44469,NULL),(39,27748,NULL),(51,46541,NULL),(1,27125,NULL),(84,46782,NULL),(44,38599,NULL),(16,31131,NULL),(14,34376,NULL),(35,48374,NULL),(9,36730,NULL),(22,47691,NULL),(78,7723,NULL),(31,18473,NULL),(65,12946,NULL),(56,34734,NULL),(91,6887,NULL),(95,7935,NULL),(91,3731,NULL),(34,13002,NULL),(32,3139,NULL),(38,33375,NULL),(39,47346,NULL),(74,16365,NULL),(60,49722,NULL),(30,39016,NULL),(52,35383,NULL),(11,41814,NULL),(56,37430,NULL),(86,14677,NULL),(95,970,NULL),(70,13770,NULL),(29,31818,NULL),(73,31985,NULL),(57,44988,NULL),(57,20924,NULL),(69,47956,NULL),(79,29257,NULL),(98,19883,NULL),(70,28080,NULL),(75,22008,NULL),(72,48190,NULL),(20,46672,NULL),(54,4386,NULL),(60,41249,NULL),(63,2325,NULL),(50,47147,NULL),(46,16664,NULL),(53,1563,NULL),(31,20595,NULL),(14,39286,NULL),(10,34185,NULL),(13,5999,NULL),(61,42621,NULL),(77,10998,NULL),(27,37271,NULL),(19,36457,NULL),(31,4345,NULL),(92,20761,NULL),(74,30235,NULL),(91,41438,NULL),(49,21342,NULL),(9,47007,NULL),(15,20391,NULL),(2,20401,NULL),(97,28184,NULL),(87,42777,NULL),(13,34582,NULL),(56,33454,NULL),(76,16256,NULL),(92,31199,NULL),(37,13933,NULL),(47,1053,NULL),(93,6494,NULL),(54,34133,NULL),(54,43272,NULL),(8,46191,NULL),(28,23647,NULL),(11,26781,NULL),(64,46718,NULL),(95,20272,NULL),(31,44975,NULL),(64,23807,NULL),(87,3425,NULL),(38,46381,NULL),(3,7822,NULL),(67,49933,NULL),(80,30313,NULL),(46,4031,NULL),(45,16248,NULL),(6,4238,NULL),(25,13194,NULL),(6,2903,NULL),(85,28241,NULL),(34,32655,NULL),(73,2794,NULL),(18,42115,NULL),(62,19713,NULL),(98,27032,NULL),(87,44167,NULL),(92,30589,NULL),(8,30412,NULL),(74,37146,NULL),(25,16827,NULL),(37,7041,NULL),(95,25792,NULL),(49,11832,NULL),(23,10795,NULL),(22,2483,NULL),(16,15967,NULL),(90,18409,NULL),(71,41914,NULL),(22,25751,NULL),(40,43494,NULL),(49,37000,NULL),(6,39036,NULL),(2,31492,NULL),(96,15892,NULL),(95,37959,NULL),(91,18650,NULL),(17,32076,NULL),(46,21961,NULL),(1,2179,NULL),(74,44677,NULL),(89,11452,NULL),(77,45028,NULL),(94,38114,NULL),(63,8263,NULL),(33,14845,NULL),(59,12938,NULL),(54,96,NULL),(47,6316,NULL),(52,38688,NULL),(3,27343,NULL),(31,42029,NULL),(92,20012,NULL),(57,38640,NULL),(71,21163,NULL),(22,14607,NULL),(87,28375,NULL),(21,21001,NULL),(59,46403,NULL),(39,5719,NULL),(26,10369,NULL),(55,11723,NULL),(30,1625,NULL),(58,12178,NULL),(89,38783,NULL),(23,21013,NULL),(73,1113,NULL),(13,22068,NULL),(82,25707,NULL),(33,5089,NULL),(84,21817,NULL),(59,34812,NULL),(74,13729,NULL),(57,47878,NULL),(3,49597,NULL),(56,38300,NULL),(30,18732,NULL),(34,44286,NULL),(26,11870,NULL),(32,32528,NULL),(39,36086,NULL),(50,6749,NULL),(45,22090,NULL);
/*!40000 ALTER TABLE `carrepair` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-12 22:23:02
