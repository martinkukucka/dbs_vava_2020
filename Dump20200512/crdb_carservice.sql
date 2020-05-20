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
-- Table structure for table `carservice`
--

DROP TABLE IF EXISTS `carservice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carservice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `phonenumber` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `addressid` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `addressid` (`addressid`),
  CONSTRAINT `carservice_ibfk_1` FOREIGN KEY (`addressid`) REFERENCES `address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carservice`
--

LOCK TABLES `carservice` WRITE;
/*!40000 ALTER TABLE `carservice` DISABLE KEYS */;
INSERT INTO `carservice` VALUES (1,'Renner, Thompson and Halvorson','1-210-787-8675','stephaine.harber@yahoo.com',125387),(2,'Feeney, Kunze and Homenick','111-349-2829','giovanni.schaefer@gmail.com',119165),(3,'Ferry Group','1-294-789-5512','kennith.skiles@yahoo.com',99498),(4,'Bradtke, Spencer and Moore','(455) 159-5030','lorenzo.langworth@yahoo.com',116737),(5,'Durgan Group','500-831-6962','dallas.roob@yahoo.com',64392),(6,'Jacobson-Lebsack','1-894-442-2099','rima.barrows@yahoo.com',143445),(7,'Russel Inc','402-833-7842','luna.fritsch@gmail.com',128350),(8,'Luettgen, Pfeffer and Willms','1-023-272-9407','waltraud.medhurst@yahoo.com',143285),(9,'Willms and Sons','1-848-694-4673','madlyn.schamberger@hotmail.com',115271),(10,'Cruickshank-Dickinson','191.334.7506','brandie.bergstrom@gmail.com',104360),(11,'Murazik-Fadel','(972) 495-7404','lyndsey.hoeger@yahoo.com',14769),(12,'Sanford-Hermiston','(197) 036-3013','deangelo.zieme@yahoo.com',46240),(13,'Kling and Sons','1-800-357-4449','allen.shields@gmail.com',64225),(14,'Stracke Inc','(575) 180-9972','lillie.macgyver@gmail.com',114389),(15,'Morissette-Kilback','1-609-021-7294','cinderella.simonis@hotmail.com',30201),(16,'Christiansen and Sons','(666) 800-1095','samual.krajcik@hotmail.com',11482),(17,'Boyle-Kling','939.484.0344','edris.bogan@hotmail.com',44588),(18,'Kunze LLC','536.643.2909','burl.mann@gmail.com',41791),(19,'Bahringer, Fritsch and Hessel','294.661.7761','kurtis.gerhold@hotmail.com',137626),(20,'Russel LLC','140-216-6599','gale.ratke@hotmail.com',126437),(21,'Considine, Kirlin and Green','812-198-3885','wesley.hodkiewicz@gmail.com',102712),(22,'O\'Reilly Inc','1-869-570-6396','pablo.corwin@yahoo.com',49740),(23,'Wiegand LLC','1-008-670-3405','classie.stark@hotmail.com',26669),(24,'Pouros, McGlynn and Gottlieb','(434) 774-1752','jacinto.gutkowski@hotmail.com',116208),(25,'Dicki, Quitzon and Blanda','(424) 812-5693','elvis.metz@yahoo.com',77339),(26,'Adams, Weber and Koepp','273.236.8570','joan.nader@yahoo.com',100116),(27,'Armstrong-Schmeler','961.852.5195','clementine.gerlach@yahoo.com',6304),(28,'Lockman-Schuppe','1-839-947-7088','adrianna.wilkinson@hotmail.com',22725),(29,'Hilpert and Sons','(937) 340-1528','yi.raynor@hotmail.com',129232),(30,'Harris and Sons','1-562-479-1404','garnet.schinner@hotmail.com',124564),(31,'Rowe, Oberbrunner and Stark','(789) 049-1766','ewa.marks@hotmail.com',86331),(32,'Schroeder Inc','(589) 874-8205','anton.harber@gmail.com',125515),(33,'Yost Group','137-871-5985','lyda.labadie@hotmail.com',39281),(34,'Botsford and Sons','909.509.1713','cordie.ebert@yahoo.com',79993),(35,'Schimmel Inc','1-458-834-6468','velda.aufderhar@hotmail.com',77041),(36,'Brakus Inc','1-958-918-4120','niesha.halvorson@yahoo.com',105683),(37,'Senger Inc','842-515-2287','timmy.legros@hotmail.com',50004),(38,'Parisian Inc','(667) 742-2077','hermine.hammes@gmail.com',120134),(39,'Feest, Altenwerth and Harvey','661.013.0290','karri.will@hotmail.com',15558),(40,'Conroy-Renner','(274) 980-2592','mikel.lindgren@gmail.com',24015),(41,'Fay, Upton and Crona','1-144-829-2531','jonathan.kilback@gmail.com',46057),(42,'Frami, Kulas and Kuhlman','(805) 674-2986','leonarda.willms@yahoo.com',9465),(43,'Rempel and Sons','1-579-378-5955','kiersten.lynch@hotmail.com',93545),(44,'Becker, Bayer and Olson','(371) 404-8417','maegan.barton@hotmail.com',141148),(45,'Connelly LLC','1-068-336-0633','arnulfo.langosh@hotmail.com',24060),(46,'Spencer and Sons','646.381.7425','marcy.cronin@gmail.com',95523),(47,'Durgan-Hand','312.745.7387','trinidad.feeney@hotmail.com',67740),(48,'Schulist Group','1-701-624-0303','wendell.kub@yahoo.com',43398),(49,'Shanahan Inc','(384) 477-0374','jayson.morar@hotmail.com',96527),(50,'Bartell LLC','1-797-400-1214','kai.gulgowski@hotmail.com',142809),(51,'Beatty LLC','225-145-8419','johnna.nitzsche@gmail.com',31468),(52,'Klocko, Flatley and Zieme','1-589-029-5955','colene.strosin@yahoo.com',447),(53,'Torp, Zulauf and Stokes','1-808-918-8929','sarai.volkman@gmail.com',63136),(54,'Nitzsche, Gulgowski and King','058-732-9461','santiago.streich@yahoo.com',12239),(55,'McLaughlin, Mann and Barrows','(927) 916-2895','ralph.larkin@yahoo.com',75384),(56,'Leuschke-Spinka','711-998-1562','myrle.schowalter@hotmail.com',47147),(57,'Pagac-Beier','(324) 513-4677','janae.quitzon@hotmail.com',82858),(58,'Bins LLC','(485) 562-5765','verlie.wiza@gmail.com',74608),(59,'Krajcik, Thiel and Effertz','359-344-7349','shad.hartmann@gmail.com',56467),(60,'Ondricka-Bradtke','1-945-483-5876','deandre.nicolas@hotmail.com',22455),(61,'Miller Group','499.087.6969','alishia.stroman@yahoo.com',98915),(62,'Kuphal Group','(649) 857-9688','alvina.bergnaum@yahoo.com',110381),(63,'Smitham Inc','417.182.3717','alphonse.corwin@yahoo.com',22837),(64,'Treutel-Mann','(583) 409-7522','clay.hickle@hotmail.com',87712),(65,'Effertz, Hills and Schulist','(999) 776-3051','basil.west@hotmail.com',47818),(66,'Stark LLC','688-948-7958','rodrick.buckridge@yahoo.com',35582),(67,'Lindgren-Ullrich','(450) 694-7472','raisa.wehner@yahoo.com',2965),(68,'Gleichner Group','364-360-7059','bee.homenick@gmail.com',147429),(69,'Klein, Becker and Collins','854.215.3647','roselia.bayer@hotmail.com',107881),(70,'Kessler, Nicolas and Roob','1-229-777-0731','trena.hahn@gmail.com',119373),(71,'McLaughlin Group','962.692.2347','lisa.dooley@gmail.com',90488),(72,'Shanahan Inc','1-325-835-3244','wendell.rodriguez@yahoo.com',116238),(73,'Moore, Schumm and Hudson','355-545-2839','patricia.schaden@yahoo.com',941),(74,'Hodkiewicz-Donnelly','596.967.5178','wendy.jakubowski@gmail.com',62000),(75,'Champlin-Kulas','168.201.4929','annmarie.smitham@gmail.com',40794),(76,'Kertzmann Group','1-856-623-1511','ryan.reichel@hotmail.com',15549),(77,'Reilly LLC','518.793.9366','fern.harvey@yahoo.com',5681),(78,'Swaniawski-Satterfield','299.288.5037','dedra.kutch@yahoo.com',102567),(79,'Donnelly-Pfeffer','652-579-5349','alia.aufderhar@gmail.com',2683),(80,'Kutch-Kutch','1-040-381-3539','brandy.gottlieb@yahoo.com',39294),(81,'Ernser, Block and Lueilwitz','841-491-8082','diana.sauer@hotmail.com',51043),(82,'Hansen Inc','642.847.8635','quiana.waelchi@gmail.com',107052),(83,'Herman-Jacobs','(631) 611-7488','johnny.runolfsdottir@yahoo.com',139491),(84,'O\'Hara-Haley','(596) 409-4491','retha.mccullough@hotmail.com',89485),(85,'Casper, Bayer and Witting','1-378-111-2706','song.johns@hotmail.com',37228),(86,'Gutkowski Group','543-854-0009','iva.runolfsson@hotmail.com',147957),(87,'Kuphal-Luettgen','266.265.4411','terina.boehm@gmail.com',59162),(88,'Lockman, Schinner and Nicolas','1-872-801-4433','fonda.durgan@yahoo.com',136356),(89,'Kuhic Inc','(304) 432-1363','celestina.legros@hotmail.com',49152),(90,'Hoppe Inc','145-041-6524','miguel.farrell@gmail.com',86726),(91,'Dare LLC','143-929-1062','lorette.zemlak@gmail.com',77637),(92,'Feest and Sons','046.876.9463','cary.kirlin@gmail.com',80985),(93,'Murphy LLC','709.227.3348','bertie.yundt@gmail.com',64372),(94,'Schowalter Group','(031) 712-5273','darnell.bergstrom@gmail.com',69029),(95,'Friesen Group','351-377-7985','tamela.ohara@gmail.com',118536),(96,'Feest, Zboncak and Dare','(279) 247-5352','raymon.will@yahoo.com',82057),(97,'Auer Group','062.252.4059','francisco.oconnell@hotmail.com',118805),(98,'Roob, Witting and Hirthe','1-585-057-2210','shavonne.wilkinson@gmail.com',108260),(99,'Bernier-Willms','485.784.6236','aurea.stamm@hotmail.com',143196);
/*!40000 ALTER TABLE `carservice` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-12 22:23:03
