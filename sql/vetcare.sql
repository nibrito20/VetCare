-- MySQL dump 10.13  Distrib 26.7.0, for macos15 (arm64)
--
-- Host: localhost    Database: vetcare
-- ------------------------------------------------------
-- Server version	26.7.0

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '38af83f6-a221-11f1-95e1-d4e6066a02cc:1-304';

--
-- Table structure for table `Animal`
--

DROP TABLE IF EXISTS `Animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Animal` (
  `Codigo` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(60) NOT NULL,
  `Especie` varchar(60) DEFAULT NULL,
  `Raca` varchar(60) DEFAULT NULL,
  `Idade` int DEFAULT NULL,
  `Dia` int DEFAULT NULL,
  `Mes` int DEFAULT NULL,
  `Ano` int DEFAULT NULL,
  `Sexo` char(1) DEFAULT NULL,
  `fk_Dono_CPF` char(11) DEFAULT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `fk_animal_dono` (`fk_Dono_CPF`),
  KEY `idx_animal_especie` (`Especie`),
  CONSTRAINT `fk_animal_dono` FOREIGN KEY (`fk_Dono_CPF`) REFERENCES `Dono` (`CPF`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `chk_animal_ano` CHECK (((`Ano` is null) or (`Ano` between 1900 and 2100))),
  CONSTRAINT `chk_animal_dia` CHECK (((`Dia` is null) or (`Dia` between 1 and 31))),
  CONSTRAINT `chk_animal_idade` CHECK (((`Idade` is null) or (`Idade` >= 0))),
  CONSTRAINT `chk_animal_mes` CHECK (((`Mes` is null) or (`Mes` between 1 and 12))),
  CONSTRAINT `chk_animal_sexo` CHECK (((`Sexo` is null) or (`Sexo` in (_utf8mb4'M',_utf8mb4'F'))))
) ENGINE=InnoDB AUTO_INCREMENT=362 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Animal`
--

LOCK TABLES `Animal` WRITE;
/*!40000 ALTER TABLE `Animal` DISABLE KEYS */;
INSERT INTO `Animal` VALUES (1,'Mel','Canino','Golden Retriever',3,10,5,2023,'F','50000000001'),(2,'Thor','Felino','Persa',5,15,8,2021,'M','50000000002'),(3,'Bob','Canino','Poodle',7,20,2,2019,'M','50000000003'),(4,'Luna','Felino','Siamês',2,12,11,2024,'F','50000000004'),(5,'Zeus','Canino','Pastor Alemão',4,3,6,2022,'M','50000000005'),(6,'Nina','Canino','Shih-tzu',6,18,9,2020,'F','50000000006'),(7,'Pandora','Felino','Angorá',1,25,1,2025,'F','50000000007'),(8,'Spike','Canino','Bulldog',3,5,4,2023,'M','50000000008'),(9,'Bela','Felino','Maine Coon',4,14,7,2022,'F','50000000009'),(10,'Max','Canino','Labrador',5,22,3,2021,'M','50000000010'),(11,'Lola','Canino','Beagle',2,11,10,2024,'F','50000000011'),(12,'Simba','Felino','Sphynx',3,9,12,2023,'M','50000000012'),(13,'Zeca','Canino','Boxer',6,4,2,2020,'M','50000000013'),(14,'Maggie','Felino','Ragdoll',1,19,6,2025,'F','50000000014'),(15,'Toby','Canino','Dachshund',4,28,8,2022,'M','50000000015'),(16,'Mia','Felino','SRD',2,13,3,2024,'F','50000000016'),(17,'Fred','Canino','Rottweiler',5,30,1,2021,'M','50000000017'),(18,'Pantera','Felino','Bombay',3,7,9,2023,'F','50000000018'),(19,'Billy','Canino','Border Collie',2,16,5,2024,'M','50000000019'),(20,'Chloe','Felino','British Shorthair',4,21,11,2022,'F','50000000020'),(21,'Pingo','Canino','Chihuahua',8,2,4,2018,'M','50000000021'),(22,'Lily','Felino','Abissínio',3,26,10,2023,'F','50000000022'),(23,'Marley','Canino','Pitbull',4,8,1,2022,'M','50000000023'),(24,'Fifi','Felino','Burmês',2,17,7,2024,'F','50000000024'),(25,'Duke','Canino','Mastiff',6,23,12,2020,'M','50000000025'),(26,'Sasha','Felino','Norueguês',5,6,2,2021,'F','50000000026'),(27,'Apollo','Canino','Husky Siberiano',3,29,6,2023,'M','50000000027'),(28,'Kitty','Felino','Turco Angorá',1,10,3,2025,'F','50000000028'),(29,'Gus','Canino','Pinscher',7,15,8,2019,'M','50000000029'),(30,'Amora','Canino','Spitz Alemão',2,24,5,2024,'F','50000000030'),(31,'joao','canino','pinscher',2,22,12,2003,'M','12345612355'),(296,'Lili','Canino','Shih Tzu',10,2,5,2016,'F','99958932407'),(297,'Safira','Canino','SRD',5,9,6,2021,'F','99922402882'),(298,'Bolinha','Ave','Calopsita',3,13,12,2023,'F','99966762698'),(299,'Estrela','Canino','Shih Tzu',1,28,7,2025,'F','99950179890'),(300,'Simba','Felino','Maine Coon',7,22,9,2019,'M','99969751506'),(301,'Fifi','Felino','British Shorthair',7,17,9,2019,'F','99955974974'),(302,'Cleo','Felino','Siamês',7,6,6,2019,'F','99946904691'),(303,'Rex','Canino','Beagle',10,25,3,2016,'M','99988837763'),(304,'Cacau','Canino','Maltês',12,17,9,2014,'F','99978749195'),(305,'Princesa','Canino','Yorkshire',11,1,4,2015,'F','99978005242'),(306,'Belinha','Canino','Shih Tzu',5,5,9,2021,'F','99958932407'),(307,'Felix','Felino','Angorá',3,16,12,2023,'M','99934257297'),(308,'Frajola','Felino','Siamês',10,12,6,2016,'M','99944161462'),(309,'Pandora','Canino','Poodle',8,5,4,2018,'F','99958924722'),(310,'Fred','Canino','Bulldog Francês',2,19,11,2024,'M','99911617912'),(311,'Tico','Ave','Agapornis',8,26,5,2018,'M','99943437260'),(312,'Mel','Canino','Husky Siberiano',2,18,5,2024,'F','99911617912'),(313,'Floquinho','Coelho','Mini Lop',2,2,12,2024,'F','99946904691'),(314,'Oliver','Felino','Persa',4,16,10,2022,'M','99966762698'),(315,'Spike','Canino','Shih Tzu',5,3,9,2021,'M','99964529139'),(316,'Kiara','Felino','Persa',4,5,6,2022,'F','99922402882'),(317,'Tom','Felino','Siamês',1,19,6,2025,'M','99977828520'),(318,'Nina','Canino','Shih Tzu',1,7,9,2025,'F','99980875208'),(319,'Apolo','Canino','Labrador',5,8,3,2021,'M','99963901794'),(320,'Tobias','Canino','Pinscher',5,6,6,2021,'M','99912704347'),(321,'Meg','Canino','Bulldog Francês',5,23,6,2021,'F','99969139614'),(322,'Rocky','Canino','Maltês',7,18,8,2019,'M','99949906911'),(323,'Dora','Canino','SRD',11,7,12,2015,'F','99926124950'),(324,'Tigrinho','Felino','Maine Coon',4,20,11,2022,'M','99978749195'),(325,'Gato','Felino','SRD',3,7,7,2023,'M','99977611629'),(326,'Lady','Canino','SRD',13,21,11,2013,'F','99988220685'),(327,'Pantera','Felino','SRD',4,16,1,2022,'F','99989598703'),(328,'Gata','Felino','Bengal',10,5,8,2016,'F','99955974974'),(329,'Fofo','Coelho','Anão Holandês',6,15,6,2020,'M','99968138193'),(330,'Dama','Canino','Yorkshire',6,22,12,2020,'F','99924441184'),(331,'Fiona','Canino','Pastor Alemão',13,15,2,2013,'F','99969139614'),(332,'Tina','Felino','SRD',9,27,9,2017,'F','99946732386'),(333,'Bidu','Canino','Rottweiler',3,19,2,2023,'M','99989598703'),(334,'Costelinha','Coelho','Mini Lop',8,4,10,2018,'M','99978005242'),(335,'Mimi','Felino','Angorá',9,20,8,2017,'F','99926124950'),(336,'Maya','Canino','Poodle',2,5,6,2024,'F','99958924722'),(337,'Bob','Canino','SRD',2,28,9,2024,'M','99982618725'),(338,'Lola','Canino','SRD',1,25,2,2025,'F','99949292913'),(339,'Duke','Canino','Bulldog Francês',10,23,6,2016,'M','99983889293'),(340,'Amendoim','Canino','Shih Tzu',5,23,6,2021,'M','99963901794'),(341,'Jade','Canino','Pinscher',4,28,9,2022,'F','99919664553'),(342,'Luna','Canino','Labrador',5,6,10,2021,'F','99981846147'),(343,'Nala','Felino','SRD',2,19,8,2024,'F','99963668550'),(344,'Bella','Canino','Golden Retriever',1,13,4,2025,'F','99980875208'),(345,'Nick','Canino','Poodle',9,8,5,2017,'M','99949292913'),(346,'Mafalda','Felino','SRD',5,19,12,2021,'F','99940517335'),(347,'Branquinho','Ave','Calopsita',8,17,3,2018,'M','99971501120'),(348,'Garfield','Felino','Siamês',9,3,10,2017,'M','99944161462'),(349,'Salem','Felino','Siamês',9,26,2,2017,'M','99958110490'),(350,'Lua','Felino','SRD',1,2,4,2025,'F','99934257297'),(351,'Amora','Canino','Pastor Alemão',2,12,5,2024,'F','99953817880'),(352,'Toby','Canino','Shih Tzu',12,7,1,2014,'M','99933261436'),(353,'Pudim','Canino','Pit Bull',6,12,1,2020,'M','99950276403'),(354,'Mia','Felino','Angorá',5,28,1,2021,'F','99958110490'),(355,'Mingau','Felino','Persa',13,10,1,2013,'M','99922794004'),(356,'Chico','Canino','SRD',1,1,4,2025,'M','99961588703'),(357,'Cacau','Felino','Persa',1,3,5,2025,'F','99967328274'),(358,'Mimo','Felino','Siamês',6,8,9,2020,'M','99943437260'),(359,'Leo','Felino','Angorá',6,18,4,2020,'M','99947362647'),(360,'Bento','Canino','Golden Retriever',3,14,1,2023,'M','99971501120'),(361,'Hulk','Canino','Poodle',5,6,10,2021,'M','99915052880');
/*!40000 ALTER TABLE `Animal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Atendimento`
--

DROP TABLE IF EXISTS `Atendimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Atendimento` (
  `Codigo` int NOT NULL,
  `Status` varchar(20) DEFAULT 'Agendado',
  `Dia` int NOT NULL,
  `Mes` int NOT NULL,
  `Ano` int NOT NULL,
  `Horario` time NOT NULL,
  `Diagnostico` varchar(500) DEFAULT NULL,
  `Custo` decimal(10,2) DEFAULT '0.00',
  `Nivel_de_Gravidade` varchar(20) DEFAULT NULL,
  `Atendimento_TIPO` int NOT NULL,
  `fk_Animal_Codigo` int NOT NULL,
  `fk_Funcionario_CPF` char(11) NOT NULL,
  `fk_Especialidade_Codigo` int NOT NULL,
  `fk_Plantao_Codigo` int DEFAULT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `fk_atendimento_animal` (`fk_Animal_Codigo`),
  KEY `fk_atendimento_especialidade` (`fk_Especialidade_Codigo`),
  KEY `fk_atendimento_plantao` (`fk_Plantao_Codigo`),
  KEY `idx_atendimento_funcionario` (`fk_Funcionario_CPF`),
  CONSTRAINT `fk_atendimento_animal` FOREIGN KEY (`fk_Animal_Codigo`) REFERENCES `Animal` (`Codigo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_atendimento_especialidade` FOREIGN KEY (`fk_Especialidade_Codigo`) REFERENCES `Especialidade` (`Codigo`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_atendimento_funcionario` FOREIGN KEY (`fk_Funcionario_CPF`) REFERENCES `Funcionario` (`CPF`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_atendimento_plantao` FOREIGN KEY (`fk_Plantao_Codigo`) REFERENCES `Plantao` (`Codigo`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `chk_atendimento_ano` CHECK ((`Ano` between 1900 and 2100)),
  CONSTRAINT `chk_atendimento_custo` CHECK ((`Custo` >= 0)),
  CONSTRAINT `chk_atendimento_dia` CHECK ((`Dia` between 1 and 31)),
  CONSTRAINT `chk_atendimento_emergencia` CHECK ((((`Atendimento_TIPO` = 2) and (`Nivel_de_Gravidade` is not null) and (`fk_Plantao_Codigo` is not null)) or ((`Atendimento_TIPO` = 1) and (`Nivel_de_Gravidade` is null) and (`fk_Plantao_Codigo` is null)))),
  CONSTRAINT `chk_atendimento_mes` CHECK ((`Mes` between 1 and 12)),
  CONSTRAINT `chk_atendimento_status` CHECK ((`Status` in (_utf8mb4'Agendado',_utf8mb4'Realizado',_utf8mb4'Cancelado'))),
  CONSTRAINT `chk_atendimento_tipo` CHECK ((`Atendimento_TIPO` in (1,2)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Atendimento`
--

LOCK TABLES `Atendimento` WRITE;
/*!40000 ALTER TABLE `Atendimento` DISABLE KEYS */;
INSERT INTO `Atendimento` VALUES (1,'Realizado',1,6,2026,'09:00:00','Check-up rotina',150.00,NULL,1,1,'10000000001',1,NULL),(2,'Realizado',2,6,2026,'10:00:00','Otite leve',120.00,NULL,1,2,'10000000002',2,NULL),(3,'Realizado',3,6,2026,'11:00:00','Fratura exposta',600.00,'Alta',2,3,'10000000003',5,3),(4,'Agendado',4,6,2026,'14:00:00',NULL,200.00,NULL,1,4,'10000000004',6,NULL),(5,'Realizado',5,6,2026,'15:00:00','Dermatite alérgica',180.00,NULL,1,5,'10000000002',3,NULL),(6,'Realizado',6,6,2026,'16:00:00','Intoxicação grave',850.00,'Crítica',2,6,'10000000003',5,6),(7,'Agendado',7,6,2026,'09:30:00',NULL,150.00,NULL,1,7,'10000000001',1,NULL),(8,'Realizado',8,6,2026,'10:30:00','Conjuntivite',130.00,NULL,1,8,'10000000004',6,NULL),(9,'Realizado',9,6,2026,'11:30:00','Parada cardiorrespiratória',1200.00,'MUITO GRAVE',2,9,'10000000003',5,9),(10,'Agendado',10,6,2026,'13:30:00',NULL,250.00,NULL,1,10,'10000000007',9,NULL),(11,'Realizado',11,6,2026,'08:30:00','Limpeza de tártaro',300.00,NULL,1,11,'10000000004',11,NULL),(12,'Realizado',12,6,2026,'15:30:00','Atropelamento',950.00,'Alta',2,12,'10000000001',1,12),(13,'Agendado',13,6,2026,'16:30:00',NULL,160.00,NULL,1,13,'10000000002',2,NULL),(14,'Realizado',14,6,2026,'09:00:00','Alergia alimentar',190.00,NULL,1,14,'10000000002',3,NULL),(15,'Realizado',15,6,2026,'10:00:00','Torção gástrica',1500.00,'Crítica',2,15,'10000000005',7,15),(16,'Agendado',16,6,2026,'11:00:00',NULL,220.00,NULL,1,16,'10000000006',8,NULL),(17,'Realizado',17,6,2026,'14:00:00','Insuficiência renal',400.00,NULL,1,17,'10000000005',10,NULL),(18,'Realizado',18,6,2026,'15:00:00','Queda de altura',750.00,'Média',2,18,'10000000001',1,18),(19,'Agendado',19,6,2026,'16:00:00',NULL,170.00,NULL,1,19,'10000000007',9,NULL),(20,'Realizado',20,6,2026,'09:30:00','Problema comportamental',250.00,NULL,1,20,'10000000006',14,NULL),(21,'Realizado',21,6,2026,'10:30:00','Picada de cobra',1100.00,'Crítica',2,21,'10000000008',13,21),(22,'Agendado',22,6,2026,'11:30:00',NULL,180.00,NULL,1,22,'10000000002',12,NULL),(23,'Realizado',23,6,2026,'13:30:00','Acompanhamento geriátrico',200.00,NULL,1,23,'10000000010',26,NULL),(24,'Realizado',24,6,2026,'14:30:00','Obstrução uretral',800.00,'Alta',2,24,'10000000005',10,24),(25,'Agendado',25,6,2026,'15:30:00',NULL,150.00,NULL,1,25,'10000000009',15,NULL),(26,'Realizado',26,6,2026,'09:00:00','Revisão pós-cirúrgica',100.00,NULL,1,26,'10000000001',1,NULL),(27,'Realizado',27,6,2026,'10:00:00','Queimadura leve',350.00,'Baixa',2,27,'10000000004',6,27),(28,'Agendado',28,6,2026,'11:00:00',NULL,210.00,NULL,1,28,'10000000008',25,NULL),(29,'Realizado',29,6,2026,'14:00:00','Diarreia aguda',140.00,NULL,1,29,'10000000007',22,NULL),(30,'Realizado',30,6,2026,'15:00:00','Choque anafilático',990.00,'Crítica',2,30,'10000000003',5,30),(999,'Realizado',11,11,2026,'14:00:00','Consulta de rotina teste',150.00,NULL,1,1,'12345678901',1,NULL),(1000,'Realizado',11,11,2026,'15:00:00','Atendimento solidario resgate',0.00,NULL,1,1,'12345678901',1,NULL),(1001,'Realizado',1,6,2026,'11:00:00','Dermatite alérgica',290.00,'Média',2,330,'20000000005',1,11),(1002,'Realizado',1,6,2026,'14:00:00','Consulta de rotina (check-up)',115.00,NULL,1,313,'20000000001',14,NULL),(1003,'Realizado',3,6,2026,'22:30:00','Fratura de membro',5290.00,'MUITO GRAVE',2,336,'20000000004',5,2),(1004,'Agendado',4,6,2026,'09:30:00','Pancreatite',1140.00,'Alta',2,330,'20000000009',3,12),(1005,'Realizado',5,6,2026,'09:30:00','Vacinação antirrábica',70.00,NULL,1,316,'20000000002',17,NULL),(1006,'Realizado',5,6,2026,'11:00:00','Cardiopatia',1385.00,'Alta',2,323,'20000000003',28,3),(1007,'Realizado',5,6,2026,'11:30:00','Gastroenterite',285.00,'Média',2,302,'20000000002',17,13),(1008,'Agendado',6,6,2026,'11:00:00','Abscesso por briga',325.00,'Média',2,324,'20000000004',5,23),(1009,'Cancelado',9,6,2026,'22:30:00','Doença renal crônica',900.00,'Alta',2,332,'20000000006',30,4),(1010,'Realizado',13,6,2026,'09:00:00','Consulta de rotina (check-up)',120.00,NULL,1,347,'20000000005',1,NULL),(1011,'Realizado',18,6,2026,'11:00:00','Vermifugação',65.00,NULL,1,314,'20000000002',17,NULL),(1012,'Agendado',19,6,2026,'16:30:00','Ferimento por briga',415.00,'Média',2,342,'20000000005',1,21),(1013,'Realizado',20,6,2026,'16:00:00','Consulta de rotina (check-up)',130.00,NULL,1,307,'20000000002',17,NULL),(1014,'Realizado',23,6,2026,'14:30:00','Vacinação antirrábica',65.00,NULL,1,332,'20000000005',1,NULL),(1015,'Realizado',24,6,2026,'09:00:00','Castração',1305.00,'Média',2,338,'20000000004',5,22),(1016,'Agendado',25,6,2026,'08:00:00','Gastroenterite',335.00,'Média',2,323,'20000000001',14,3),(1017,'Realizado',26,6,2026,'08:00:00','Vacinação V4/V5',110.00,NULL,1,317,'20000000009',3,NULL),(1018,'Cancelado',27,6,2026,'09:30:00','Abscesso por briga',375.00,'Média',2,300,'20000000005',1,23),(1019,'Realizado',27,6,2026,'15:00:00','Dermatite alérgica',365.00,'Média',2,306,'20000000001',14,4),(1020,'Agendado',28,6,2026,'08:30:00','Fratura de membro',3945.00,'MUITO GRAVE',2,330,'20000000006',30,1),(1021,'Realizado',30,6,2026,'20:00:00','Torção gástrica',7160.00,'MUITO GRAVE',2,304,'20000000004',5,11),(1022,'Realizado',30,6,2026,'22:30:00','Cistite / obstrução urinária',1500.00,'Crítica',2,359,'20000000008',20,21),(1023,'Realizado',1,7,2026,'16:00:00','Dermatite alérgica',365.00,'Média',2,342,'20000000001',14,2),(1024,'Agendado',2,7,2026,'09:00:00','Abscesso por briga',475.00,'Média',2,307,'20000000002',17,12),(1025,'Realizado',2,7,2026,'15:00:00','Dermatofitose',255.00,'Média',2,332,'20000000003',28,22),(1026,'Realizado',3,7,2026,'11:00:00','Consulta de rotina (check-up)',150.00,NULL,1,328,'20000000002',17,NULL),(1027,'Cancelado',4,7,2026,'11:00:00','Dermatite alérgica',260.00,'Média',2,319,'20000000001',14,13),(1028,'Agendado',4,7,2026,'12:00:00','Erliquiose (doença do carrapato)',905.00,'Alta',2,351,'20000000006',30,23),(1029,'Realizado',7,7,2026,'12:30:00','Síndrome do paraquedista (queda)',2700.00,'MUITO GRAVE',2,332,'20000000005',1,4),(1030,'Realizado',8,7,2026,'14:00:00','Limpeza de tártaro',590.00,'Média',2,341,'20000000004',5,1),(1031,'Realizado',8,7,2026,'15:00:00','Vacinação V10',110.00,NULL,1,310,'20000000010',18,NULL),(1032,'Agendado',10,7,2026,'09:00:00','Consulta de rotina (check-up)',150.00,NULL,1,330,'20000000002',17,NULL),(1033,'Realizado',10,7,2026,'09:30:00','Otite externa',245.00,'Média',2,333,'20000000010',18,2),(1034,'Realizado',11,7,2026,'11:00:00','Cistite / obstrução urinária',1385.00,'Crítica',2,327,'20000000003',28,12),(1035,'Realizado',13,7,2026,'13:30:00','Consulta de rotina (check-up)',165.00,NULL,1,307,'20000000001',14,NULL),(1036,'Cancelado',14,7,2026,'10:30:00','Vacinação antirrábica',60.00,NULL,1,323,'20000000001',14,NULL),(1037,'Realizado',14,7,2026,'19:30:00','Displasia coxofemoral',1725.00,'Alta',2,323,'20000000002',17,13),(1038,'Realizado',14,7,2026,'22:30:00','Erliquiose (doença do carrapato)',775.00,'Alta',2,312,'20000000002',17,23),(1039,'Realizado',15,7,2026,'09:00:00','Consulta de rotina (check-up)',135.00,NULL,1,312,'20000000001',14,NULL),(1040,'Agendado',15,7,2026,'10:00:00','Consulta de rotina (check-up)',105.00,NULL,1,298,'20000000001',14,NULL),(1041,'Realizado',15,7,2026,'11:00:00','Dermatite alérgica',255.00,'Média',2,315,'20000000010',18,11),(1042,'Realizado',17,7,2026,'09:00:00','Vacinação antirrábica',60.00,NULL,1,330,'20000000003',28,NULL),(1043,'Realizado',19,7,2026,'18:30:00','Estase gastrointestinal',965.00,'Alta',2,334,'20000000006',30,2),(1044,'Agendado',20,7,2026,'11:00:00','Limpeza de tártaro',600.00,'Média',2,330,'20000000003',28,12),(1045,'Cancelado',21,7,2026,'10:30:00','Consulta de rotina (check-up)',130.00,NULL,1,317,'20000000005',1,NULL),(1046,'Realizado',22,7,2026,'11:00:00','Dermatofitose',370.00,'Média',2,317,'20000000004',5,3),(1047,'Realizado',24,7,2026,'15:30:00','Corte de unhas e dentes',90.00,NULL,1,313,'20000000001',14,NULL),(1048,'Agendado',24,7,2026,'16:00:00','Consulta de rotina (check-up)',130.00,NULL,1,323,'20000000002',17,NULL),(1049,'Realizado',24,7,2026,'17:00:00','Vacinação antirrábica',60.00,NULL,1,317,'20000000002',17,NULL),(1050,'Realizado',24,7,2026,'19:30:00','Parvovirose',1245.00,'Crítica',2,333,'20000000003',28,1),(1051,'Realizado',26,7,2026,'11:00:00','Cistite / obstrução urinária',1685.00,'Crítica',2,316,'20000000001',14,11),(1052,'Agendado',27,7,2026,'14:30:00','Consulta de rotina (check-up)',130.00,NULL,1,331,'20000000003',28,NULL),(1053,'Realizado',28,7,2026,'15:30:00','Consulta de rotina (check-up)',95.00,NULL,1,334,'20000000001',14,NULL),(1054,'Cancelado',28,7,2026,'16:30:00','Vacinação V4/V5',140.00,NULL,1,332,'20000000003',28,NULL),(1055,'Realizado',29,7,2026,'08:30:00','Vacinação V4/V5',135.00,NULL,1,317,'20000000002',17,NULL),(1056,'Agendado',29,7,2026,'09:30:00','Vacinação V4/V5',125.00,NULL,1,332,'20000000006',30,NULL),(1057,'Realizado',30,7,2026,'11:00:00','Vacinação antirrábica',70.00,NULL,1,310,'20000000001',14,NULL),(1058,'Realizado',31,7,2026,'16:00:00','Atropelamento',5200.00,'MUITO GRAVE',2,321,'20000000003',28,23),(1059,'Realizado',6,8,2026,'09:00:00','Estase gastrointestinal',980.00,'Alta',2,334,'20000000001',14,4),(1060,'Agendado',6,8,2026,'11:00:00','Abscesso por briga',465.00,'Média',2,307,'20000000001',14,1),(1061,'Realizado',7,8,2026,'14:30:00','Dermatite alérgica',235.00,'Média',2,340,'20000000003',28,11),(1062,'Realizado',7,8,2026,'22:30:00','Displasia coxofemoral',980.00,'Alta',2,331,'20000000004',5,21),(1063,'Cancelado',8,8,2026,'11:30:00','Dermatite alérgica',325.00,'Média',2,330,'20000000001',14,2),(1064,'Agendado',11,8,2026,'10:30:00','Limpeza de tártaro',560.00,'Média',2,325,'20000000007',2,12),(1065,'Realizado',11,8,2026,'16:00:00','Cistite / obstrução urinária',2275.00,'Crítica',2,332,'20000000002',17,22),(1066,'Realizado',13,8,2026,'10:00:00','Consulta de rotina (check-up)',125.00,NULL,1,330,'20000000001',14,NULL),(1067,'Realizado',13,8,2026,'15:00:00','Consulta de rotina (check-up)',140.00,NULL,1,340,'20000000005',1,NULL),(1068,'Agendado',13,8,2026,'17:30:00','Consulta de rotina (check-up)',125.00,NULL,1,333,'20000000002',17,NULL),(1069,'Realizado',14,8,2026,'10:00:00','Conjuntivite',160.00,NULL,1,353,'20000000004',5,NULL),(1070,'Realizado',15,8,2026,'08:30:00','Maloclusão dentária',260.00,'Média',2,334,'20000000001',14,1),(1071,'Realizado',15,8,2026,'14:30:00','Consulta de rotina (check-up)',155.00,NULL,1,334,'20000000002',17,NULL),(1072,'Cancelado',17,8,2026,'09:30:00','Gastroenterite',370.00,'Média',2,350,'20000000006',30,21),(1073,'Realizado',18,8,2026,'10:00:00','Cardiopatia',1090.00,'Alta',2,331,'20000000005',1,2),(1074,'Realizado',19,8,2026,'17:30:00','Corte de bico e unhas',75.00,NULL,1,298,'20000000002',17,NULL),(1075,'Realizado',20,8,2026,'08:00:00','Vacinação V4/V5',130.00,NULL,1,324,'20000000001',14,NULL),(1076,'Agendado',20,8,2026,'11:00:00','FIV/FeLV (retrovirose)',535.00,'Alta',2,325,'20000000006',30,3),(1077,'Realizado',21,8,2026,'11:00:00','Vacinação antirrábica',70.00,NULL,1,332,'20000000005',1,NULL),(1078,'Realizado',22,8,2026,'17:00:00','Abscesso por briga',310.00,'Média',2,316,'20000000003',28,23),(1079,'Realizado',23,8,2026,'15:30:00','Erliquiose (doença do carrapato)',890.00,'Alta',2,338,'20000000005',1,4),(1080,'Agendado',24,8,2026,'16:30:00','Consulta de rotina (check-up)',130.00,NULL,1,334,'20000000010',18,NULL),(1081,'Cancelado',26,8,2026,'16:00:00','Gastroenterite',390.00,'Média',2,312,'20000000003',28,11),(1082,'Realizado',29,8,2026,'18:00:00','Cinomose',1865.00,'Crítica',2,337,'20000000003',28,21),(1083,'Realizado',31,8,2026,'07:00:00','Cardiopatia',915.00,'Alta',2,304,'20000000010',18,2),(1084,'Agendado',31,8,2026,'09:00:00','Consulta de rotina (check-up)',135.00,NULL,1,343,'20000000003',28,NULL),(1085,'Realizado',31,8,2026,'13:30:00','Abscesso por briga',415.00,'Média',2,302,'20000000006',30,22),(1086,'Realizado',31,8,2026,'14:00:00','Gastroenterite',520.00,'Média',2,307,'20000000009',3,3),(1087,'Realizado',31,8,2026,'16:30:00','Vermifugação',80.00,NULL,1,317,'20000000005',1,NULL),(1088,'Agendado',1,9,2026,'14:00:00','Consulta de rotina (check-up)',175.00,NULL,1,323,'20000000002',17,NULL),(1089,'Realizado',2,9,2026,'08:30:00','Vermifugação',80.00,NULL,1,317,'20000000001',14,NULL),(1090,'Cancelado',2,9,2026,'09:30:00','Consulta de rotina (check-up)',190.00,NULL,1,303,'20000000003',28,NULL),(1091,'Realizado',2,9,2026,'16:30:00','Vacinação antirrábica',65.00,NULL,1,356,'20000000001',14,NULL),(1092,'Agendado',2,9,2026,'17:30:00','Corte de unhas e dentes',85.00,NULL,1,329,'20000000001',14,NULL),(1093,'Realizado',3,9,2026,'13:30:00','Gastroenterite',410.00,'Média',2,307,'20000000006',30,2),(1094,'Realizado',3,9,2026,'15:30:00','Fratura de membro',3700.00,'MUITO GRAVE',2,326,'20000000007',2,12),(1095,'Realizado',4,9,2026,'10:30:00','Dermatite alérgica',355.00,'Média',2,361,'20000000002',17,22),(1096,'Agendado',7,9,2026,'22:30:00','Síndrome do paraquedista (queda)',3790.00,'MUITO GRAVE',2,308,'20000000007',2,3),(1097,'Realizado',8,9,2026,'15:30:00','Otite externa',275.00,'Média',2,341,'20000000009',3,13),(1098,'Realizado',10,9,2026,'09:00:00','Vacinação V4/V5',135.00,NULL,1,350,'20000000006',30,NULL),(1099,'Cancelado',10,9,2026,'13:30:00','Vacinação V10',135.00,NULL,1,352,'20000000002',17,NULL),(1100,'Agendado',10,9,2026,'14:00:00','Otite externa',230.00,'Média',2,340,'20000000002',17,1),(1101,'Realizado',11,9,2026,'15:00:00','Consulta de rotina (check-up)',145.00,NULL,1,357,'20000000006',30,NULL),(1102,'Realizado',11,9,2026,'19:00:00','Estase gastrointestinal',605.00,'Alta',2,334,'20000000004',5,21),(1103,'Realizado',12,9,2026,'08:30:00','Insuficiência renal aguda',2735.00,'Crítica',2,321,'20000000001',14,2),(1104,'Agendado',12,9,2026,'08:30:00','Consulta de rotina (check-up)',135.00,NULL,1,346,'20000000001',14,NULL),(1105,'Realizado',12,9,2026,'15:00:00','Otite externa',215.00,'Média',2,345,'20000000001',14,22),(1106,'Realizado',14,9,2026,'16:30:00','Vermifugação',70.00,NULL,1,317,'20000000002',17,NULL),(1107,'Realizado',15,9,2026,'09:00:00','Gastroenterite',340.00,'Média',2,307,'20000000002',17,13),(1108,'Cancelado',15,9,2026,'13:30:00','Dermatite alérgica',255.00,'Média',2,341,'20000000005',1,23),(1109,'Realizado',16,9,2026,'08:30:00','Rinotraqueíte felina',330.00,'Média',2,317,'20000000001',14,4),(1110,'Realizado',16,9,2026,'14:00:00','Conjuntivite',185.00,NULL,1,307,'20000000002',17,NULL),(1111,'Realizado',16,9,2026,'14:30:00','Vacinação antirrábica',80.00,NULL,1,330,'20000000003',28,NULL),(1112,'Agendado',16,9,2026,'16:30:00','Consulta de rotina (check-up)',125.00,NULL,1,332,'20000000001',14,NULL),(1113,'Realizado',16,9,2026,'17:30:00','Ferimento por briga',445.00,'Média',2,319,'20000000003',28,2),(1114,'Realizado',18,9,2026,'11:00:00','Vacinação V10',125.00,NULL,1,326,'20000000001',14,NULL),(1115,'Realizado',18,9,2026,'15:00:00','Consulta de rotina (check-up)',135.00,NULL,1,315,'20000000009',3,NULL),(1116,'Agendado',21,9,2026,'15:00:00','Consulta de rotina (check-up)',125.00,NULL,1,313,'20000000001',14,NULL),(1117,'Cancelado',22,9,2026,'17:00:00','Torção gástrica',5610.00,'MUITO GRAVE',2,319,'20000000002',17,13),(1118,'Realizado',23,9,2026,'11:00:00','Consulta de rotina (check-up)',140.00,NULL,1,359,'20000000003',28,NULL),(1119,'Realizado',25,9,2026,'09:00:00','Consulta de rotina (check-up)',155.00,NULL,1,326,'20000000003',28,NULL),(1120,'Agendado',25,9,2026,'15:30:00','Ferimento por briga',405.00,'Média',2,320,'20000000010',18,1),(1121,'Realizado',26,9,2026,'21:00:00','Insuficiência renal aguda',2915.00,'Crítica',2,323,'20000000004',5,11),(1122,'Realizado',28,9,2026,'15:00:00','Consulta de rotina (check-up)',175.00,NULL,1,340,'20000000001',14,NULL),(1123,'Realizado',28,9,2026,'20:00:00','Atropelamento',5750.00,'MUITO GRAVE',2,323,'20000000002',17,2),(1124,'Agendado',29,9,2026,'17:00:00','Consulta de rotina (check-up)',165.00,NULL,1,334,'20000000002',17,NULL),(1125,'Realizado',29,9,2026,'17:30:00','Consulta de rotina (check-up)',165.00,NULL,1,326,'20000000002',17,NULL),(1126,'Cancelado',30,9,2026,'08:00:00','Otite externa',260.00,'Média',2,353,'20000000001',14,3),(1127,'Realizado',30,9,2026,'09:00:00','Gastroenterite',515.00,'Média',2,323,'20000000004',5,13),(1128,'Agendado',2,10,2026,'08:00:00','Dermatite alérgica',330.00,'Média',2,323,'20000000001',14,23),(1129,'Realizado',2,10,2026,'11:30:00','Limpeza de tártaro',810.00,'Média',2,342,'20000000006',30,4),(1130,'Realizado',3,10,2026,'10:30:00','Vacinação V10',120.00,NULL,1,330,'20000000002',17,NULL),(1131,'Realizado',3,10,2026,'14:30:00','Otite externa',230.00,'Média',2,326,'20000000004',5,11),(1132,'Agendado',3,10,2026,'16:00:00','Limpeza de tártaro',560.00,'Média',2,330,'20000000003',28,21),(1133,'Realizado',5,10,2026,'11:00:00','Vacinação V4/V5',140.00,NULL,1,327,'20000000010',18,NULL),(1134,'Realizado',5,10,2026,'13:30:00','Cistite / obstrução urinária',2325.00,'Crítica',2,300,'20000000008',20,12),(1135,'Cancelado',6,10,2026,'16:00:00','Castração',950.00,'Média',2,326,'20000000006',30,22),(1136,'Agendado',7,10,2026,'16:30:00','Castração',765.00,'Média',2,317,'20000000004',5,3),(1137,'Realizado',7,10,2026,'16:30:00','Fratura de membro',3900.00,'MUITO GRAVE',2,356,'20000000002',17,13),(1138,'Realizado',12,10,2026,'09:30:00','Consulta de rotina (check-up)',125.00,NULL,1,298,'20000000008',20,NULL),(1139,'Realizado',12,10,2026,'15:00:00','Vacinação V4/V5',115.00,NULL,1,317,'20000000001',14,NULL),(1140,'Agendado',13,10,2026,'10:00:00','Otite externa',270.00,'Média',2,315,'20000000001',14,1),(1141,'Realizado',13,10,2026,'17:00:00','Corte de bico e unhas',90.00,NULL,1,311,'20000000006',30,NULL),(1142,'Realizado',14,10,2026,'13:30:00','Vermifugação',90.00,NULL,1,299,'20000000001',14,NULL),(1143,'Realizado',14,10,2026,'13:30:00','Otite externa',290.00,'Média',2,333,'20000000008',20,2),(1144,'Cancelado',14,10,2026,'13:30:00','Conjuntivite',165.00,NULL,1,342,'20000000005',1,NULL),(1145,'Realizado',14,10,2026,'16:30:00','Maloclusão dentária',470.00,'Média',2,334,'20000000004',5,22),(1146,'Realizado',14,10,2026,'17:30:00','Limpeza de tártaro',750.00,'Média',2,355,'20000000001',14,3),(1147,'Realizado',15,10,2026,'15:30:00','Maloclusão dentária',335.00,'Média',2,329,'20000000003',28,13),(1148,'Agendado',19,10,2026,'07:00:00','Erliquiose (doença do carrapato)',940.00,'Alta',2,304,'20000000001',14,23),(1149,'Realizado',20,10,2026,'08:00:00','Estase gastrointestinal',840.00,'Alta',2,334,'20000000006',30,4),(1150,'Realizado',20,10,2026,'11:30:00','Vacinação V4/V5',140.00,NULL,1,317,'20000000008',20,NULL),(1151,'Realizado',20,10,2026,'16:30:00','Conjuntivite',120.00,NULL,1,321,'20000000003',28,NULL),(1152,'Agendado',20,10,2026,'17:00:00','Cistite / obstrução urinária',1840.00,'Crítica',2,307,'20000000002',17,21),(1153,'Cancelado',21,10,2026,'09:00:00','Dermatite alérgica',300.00,'Média',2,351,'20000000002',17,2),(1154,'Realizado',21,10,2026,'09:00:00','Consulta de rotina (check-up)',155.00,NULL,1,316,'20000000001',14,NULL),(1155,'Realizado',21,10,2026,'15:00:00','Vermifugação',70.00,NULL,1,344,'20000000001',14,NULL),(1156,'Agendado',21,10,2026,'16:30:00','Dermatite alérgica',240.00,'Média',2,361,'20000000006',30,3),(1157,'Realizado',22,10,2026,'15:00:00','Abscesso por briga',430.00,'Média',2,300,'20000000002',17,13),(1158,'Realizado',22,10,2026,'16:30:00','Vacinação antirrábica',100.00,NULL,1,307,'20000000009',3,NULL),(1159,'Realizado',23,10,2026,'10:30:00','Limpeza de tártaro',745.00,'Média',2,316,'20000000003',28,4),(1160,'Agendado',23,10,2026,'17:00:00','Pancreatite',1170.00,'Alta',2,360,'20000000001',14,1),(1161,'Realizado',24,10,2026,'15:30:00','Maloclusão dentária',385.00,'Média',2,334,'20000000002',17,11),(1162,'Cancelado',25,10,2026,'10:30:00','Doença renal crônica',1105.00,'Alta',2,349,'20000000001',14,21),(1163,'Realizado',27,10,2026,'10:00:00','Dermatite alérgica',240.00,'Média',2,353,'20000000004',5,2),(1164,'Agendado',27,10,2026,'13:30:00','Dermatite alérgica',255.00,'Média',2,333,'20000000008',20,12),(1165,'Realizado',27,10,2026,'14:00:00','Síndrome do paraquedista (queda)',4425.00,'MUITO GRAVE',2,348,'20000000001',14,22),(1166,'Realizado',28,10,2026,'08:30:00','Rinotraqueíte felina',375.00,'Média',2,317,'20000000003',28,3),(1167,'Realizado',28,10,2026,'11:30:00','Ferimento por briga',305.00,'Média',2,299,'20000000006',30,13),(1168,'Agendado',28,10,2026,'11:30:00','Otite externa',265.00,'Média',2,321,'20000000001',14,23),(1169,'Realizado',28,10,2026,'14:30:00','Consulta de rotina (check-up)',180.00,NULL,1,309,'20000000002',17,NULL),(1170,'Realizado',29,10,2026,'08:30:00','Maloclusão dentária',435.00,'Média',2,334,'20000000001',14,1),(1171,'Cancelado',29,10,2026,'15:00:00','Consulta de rotina (check-up)',155.00,NULL,1,361,'20000000001',14,NULL),(1172,'Agendado',29,10,2026,'15:00:00','Consulta de rotina (check-up)',185.00,NULL,1,326,'20000000001',14,NULL),(1173,'Realizado',30,10,2026,'10:00:00','Gastroenterite',365.00,'Média',2,321,'20000000001',14,2),(1174,'Realizado',30,10,2026,'10:00:00','Consulta de rotina (check-up)',140.00,NULL,1,334,'20000000002',17,NULL),(1175,'Realizado',2,11,2026,'10:00:00','Dermatite alérgica',250.00,'Média',2,333,'20000000001',14,22),(1176,'Agendado',2,11,2026,'10:30:00','Vacinação antirrábica',80.00,NULL,1,356,'20000000004',5,NULL),(1177,'Realizado',2,11,2026,'11:00:00','Consulta de rotina (check-up)',140.00,NULL,1,313,'20000000004',5,NULL),(1178,'Realizado',3,11,2026,'17:00:00','Cardiopatia',1200.00,'Alta',2,345,'20000000001',14,23),(1179,'Realizado',4,11,2026,'14:00:00','Consulta de rotina (check-up)',105.00,NULL,1,313,'20000000001',14,NULL),(1180,'Cancelado',5,11,2026,'07:00:00','Cardiopatia',1155.00,'Alta',2,326,'20000000003',28,1),(1181,'Realizado',5,11,2026,'08:00:00','Castração',1495.00,'Média',2,356,'20000000001',14,11),(1182,'Realizado',5,11,2026,'08:00:00','Vermifugação',90.00,NULL,1,350,'20000000007',2,NULL),(1183,'Realizado',5,11,2026,'09:00:00','Corte de unhas e dentes',70.00,NULL,1,334,'20000000001',14,NULL),(1184,'Agendado',5,11,2026,'10:00:00','Dermatite alérgica',365.00,'Média',2,315,'20000000005',1,12),(1185,'Realizado',5,11,2026,'11:00:00','Limpeza de tártaro',770.00,'Média',2,314,'20000000002',17,22),(1186,'Realizado',5,11,2026,'14:00:00','Vacinação V10',110.00,NULL,1,326,'20000000003',28,NULL),(1187,'Realizado',6,11,2026,'11:00:00','Dermatofitose',385.00,'Média',2,308,'20000000006',30,13),(1188,'Agendado',6,11,2026,'15:00:00','Consulta de rotina (check-up)',175.00,NULL,1,321,'20000000001',14,NULL),(1189,'Cancelado',6,11,2026,'16:00:00','Vacinação V4/V5',155.00,NULL,1,355,'20000000002',17,NULL),(1190,'Realizado',9,11,2026,'11:30:00','Vermifugação',100.00,NULL,1,327,'20000000004',5,NULL),(1191,'Realizado',10,11,2026,'09:30:00','Castração',725.00,'Média',2,349,'20000000010',18,11),(1192,'Agendado',10,11,2026,'10:30:00','Vermifugação',65.00,NULL,1,317,'20000000001',14,NULL),(1193,'Realizado',10,11,2026,'11:00:00','Vacinação V4/V5',140.00,NULL,1,317,'20000000007',2,NULL),(1194,'Realizado',10,11,2026,'14:30:00','Consulta de rotina (check-up)',130.00,NULL,1,298,'20000000001',14,NULL),(1195,'Realizado',10,11,2026,'16:00:00','Otite externa',240.00,'Média',2,309,'20000000003',28,22),(1196,'Agendado',11,11,2026,'09:00:00','Dermatite alérgica',310.00,'Média',2,322,'20000000005',1,3),(1197,'Realizado',11,11,2026,'10:30:00','Gastroenterite',360.00,'Média',2,335,'20000000002',17,13),(1198,'Cancelado',11,11,2026,'13:30:00','Cardiopatia',1280.00,'Alta',2,354,'20000000006',30,23),(1199,'Realizado',11,11,2026,'15:30:00','Cistite / obstrução urinária',1920.00,'Crítica',2,358,'20000000004',5,4),(1200,'Agendado',11,11,2026,'18:00:00','Fratura de membro',4150.00,'MUITO GRAVE',2,361,'20000000008',20,1);
/*!40000 ALTER TABLE `Atendimento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 */ /*!50003 TRIGGER `trg_ValidaCustoAtendimento` BEFORE INSERT ON `atendimento` FOR EACH ROW BEGIN
    IF NEW.Custo < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Erro: O custo do atendimento nao pode ser negativo!';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 */ /*!50003 TRIGGER `trg_LogNovoAtendimento` AFTER INSERT ON `atendimento` FOR EACH ROW BEGIN
    INSERT INTO Log_Atendimento (Mensagem)
    VALUES (CONCAT('Novo atendimento registado para o animal ID: ', NEW.fk_Animal_Codigo, ' com custo R$ ', NEW.Custo));
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Atua_em`
--

DROP TABLE IF EXISTS `Atua_em`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Atua_em` (
  `fk_Funcionario_CPF` char(11) NOT NULL,
  `fk_Especialidade_Codigo` int NOT NULL,
  PRIMARY KEY (`fk_Funcionario_CPF`,`fk_Especialidade_Codigo`),
  KEY `fk_atua_em_especialidade` (`fk_Especialidade_Codigo`),
  CONSTRAINT `fk_atua_em_especialidade` FOREIGN KEY (`fk_Especialidade_Codigo`) REFERENCES `Especialidade` (`Codigo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_atua_em_funcionario` FOREIGN KEY (`fk_Funcionario_CPF`) REFERENCES `Funcionario` (`CPF`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Atua_em`
--

LOCK TABLES `Atua_em` WRITE;
/*!40000 ALTER TABLE `Atua_em` DISABLE KEYS */;
INSERT INTO `Atua_em` VALUES ('10000000001',1),('10000000002',2),('10000000002',3),('10000000001',4),('10000000003',5),('10000000004',6),('10000000005',7),('10000000006',8),('10000000007',9),('10000000005',10),('10000000004',11),('10000000002',12),('10000000008',13),('10000000006',14),('10000000009',15),('10000000007',16),('10000000001',17),('10000000004',18),('10000000005',19),('10000000006',20),('10000000003',21),('10000000007',22),('10000000010',23),('10000000009',24),('10000000008',25),('10000000010',26),('10000000010',27),('10000000008',28),('10000000003',29),('10000000009',30);
/*!40000 ALTER TABLE `Atua_em` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Dono`
--

DROP TABLE IF EXISTS `Dono`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Dono` (
  `CPF` char(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Telefone` varchar(20) DEFAULT NULL,
  `Cidade` varchar(60) DEFAULT NULL,
  `Rua` varchar(100) DEFAULT NULL,
  `Bairro` varchar(60) DEFAULT NULL,
  `Numero` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`CPF`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Dono`
--

LOCK TABLES `Dono` WRITE;
/*!40000 ALTER TABLE `Dono` DISABLE KEYS */;
INSERT INTO `Dono` VALUES ('12345612355','edu','81933567289','recife','alegre','oi','11111111'),('50000000001','Mariana Lima','(81) 99999-0001','Recife','Rua 1','Madalena','10'),('50000000002','Roberto Cavalcanti','(81)99966-3344','Jaboatão','Rua 2','Piedade','20'),('50000000003','Camila Albuquerque','(81)93345-6677','Olinda','Rua 3','Bairro Novo','30'),('50000000004','Lucas Gabriel','(81) 99999-0004','Recife','Rua 4','Aflitos','40'),('50000000005','Fernanda Torres','(81) 99999-0005','Jaboatão','Rua 5','Candeias','50'),('50000000006','Bruno Cardoso','(81) 99999-0006','Recife','Rua 6','Boa Viagem','60'),('50000000007','Tatiane Souza','(81) 99999-0007','Recife','Rua 7','Espinheiro','70'),('50000000008','Diego Ramos','(81) 99999-0008','Olinda','Rua 8','Casa Caiada','80'),('50000000009','Juliana Paes','(81) 99999-0009','Recife','Rua 9','Graças','90'),('50000000010','Rodrigo Hilbert','(81) 99999-0010','Recife','Rua 10','Derby','100'),('50000000011','Ana Maria','(81) 99999-0011','Recife','Rua 11','Torre','11'),('50000000012','Carlos Alberto','(81) 99999-0012','Jaboatão','Rua 12','Barra de Jangada','12'),('50000000013','Débora Falabella','(81) 99999-0013','Recife','Rua 13','Casa Forte','13'),('50000000014','Eri Johnson','(81) 99999-0014','Recife','Rua 14','Encruzilhada','14'),('50000000015','Flávia Alessandra','(81) 99999-0015','Olinda','Rua 15','Rio Doce','15'),('50000000016','Gustavo Lima','(81) 99999-0016','Recife','Rua 16','Parnamirim','16'),('50000000017','Heloísa Périssé','(81) 99999-0017','Recife','Rua 17','Jaqueira','17'),('50000000018','Igor Rickli','(81) 99999-0018','Jaboatão','Rua 18','Prazeres','18'),('50000000019','Joana Fomm','(81) 99999-0019','Recife','Rua 19','Boa Vista','19'),('50000000020','Kleber Toledo','(81) 99999-0020','Recife','Rua 20','Santo Amaro','20'),('50000000021','Larissa Manoela','(81) 99999-0021','Recife','Rua 21','Imbiribeira','21'),('50000000022','Murilo Benício','(81) 99999-0022','Olinda','Rua 22','Ouro Preto','22'),('50000000023','Nívea Maria','(81) 99999-0023','Recife','Rua 23','Caxangá','23'),('50000000024','Otaviano Costa','(81) 99999-0024','Recife','Rua 24','Várzea','24'),('50000000025','Patrícia Pillar','(81) 99999-0025','Jaboatão','Rua 25','Candeias','25'),('50000000026','Quirino Silva','(81) 99999-0026','Recife','Rua 26','Afogados','26'),('50000000027','Regina Duarte','(81) 99999-0027','Recife','Rua 27','San Martin','27'),('50000000028','Sílvio Santos','(81) 99999-0028','Olinda','Rua 28','Amaro Branco','28'),('50000000029','Tais Araújo','(81) 99999-0029','Recife','Rua 29','Prado','29'),('50000000030','Umberto Magnani','(81) 99999-0030','Recife','Rua 30','Madalena','30'),('99911617912','Daniela Nunes','(81) 98481-1376','Recife','Rua Amélia','Casa Forte','394'),('99912704347','José Freitas','(81) 98974-6370','Recife','Rua Amélia','Iputinga','1345'),('99915052880','Fernanda Martins Bezerra','(81) 99545-4088','Recife','Rua Barão de Souza Leão','Boa Viagem','538'),('99919664553','Felipe Monteiro','(81) 99189-9955','Recife','Rua do Hospício','Apipucos','824'),('99922402882','José Carvalho Carvalho','(81) 99070-6800','Recife','Rua do Sol','Tamarineira','127'),('99922794004','Felipe Pinto','(81) 98935-8690','Olinda','Rua Joaquim Nabuco','Rio Doce','2302'),('99924441184','Cláudia Melo Melo','(81) 99729-4047','Recife','Rua Setúbal','Torre','1752'),('99926124950','Beatriz Araújo Fernandes','(81) 98010-6807','Recife','Rua das Graças','Várzea','163'),('99933261436','Luciana Dias','(81) 99765-3471','Olinda','Rua Setúbal','Rio Doce','962'),('99934257297','Verônica Costa','(81) 98190-8440','Recife','Rua da Hora','Várzea','2242'),('99940517335','Aline Leite','(81) 98330-5273','Paulista','Rua do Futuro','Janga','66'),('99943437260','Larissa Carvalho Fernandes','(81) 98041-9882','Recife','Rua da Hora','Aflitos','2307'),('99944161462','Natália Fernandes Martins','(81) 98910-2967','Recife','Rua das Graças','Santo Amaro','1131'),('99946732386','Mônica Siqueira','(81) 98933-1622','Recife','Rua Setúbal','Derby','893'),('99946904691','Jéssica Martins','(81) 98720-9820','Jaboatão dos Guararapes','Rua José de Alencar','Piedade','1158'),('99947362647','Tatiane Cavalcanti','(81) 99464-8155','Recife','Rua da Aurora','Tamarineira','1363'),('99949292913','Gustavo Moreira Araújo','(81) 99304-8496','Recife','Rua Joaquim Nabuco','Cordeiro','2388'),('99949906911','Beatriz Tavares Nascimento','(81) 99988-9463','Recife','Rua Dom Bosco','Santo Amaro','439'),('99950179890','Leonardo Fernandes','(81) 98704-3862','Recife','Rua do Futuro','Iputinga','2048'),('99950276403','Rodrigo Leite Fernandes','(81) 99590-7897','Recife','Avenida Caxangá','Graças','761'),('99953817880','Ana Moreira','(81) 98644-3271','Recife','Avenida Norte','Imbiribeira','744'),('99955974974','Igor Cavalcanti Costa','(81) 99607-9440','Olinda','Rua do Futuro','Carmo','759'),('99958110490','Juliana Lopes','(81) 99389-2138','Recife','Rua Joaquim Nabuco','Casa Amarela','149'),('99958924722','Davi Carvalho','(81) 99648-3797','Recife','Avenida Conselheiro Aguiar','Parnamirim','2138'),('99958932407','Davi Araújo Correia','(81) 99692-9145','Recife','Rua Padre Carapuceiro','Encruzilhada','147'),('99961588703','Sandra Leite','(81) 99920-3209','Olinda','Rua Joaquim Nabuco','Bairro Novo','1947'),('99963668550','Mateus Teixeira','(81) 99951-2681','Olinda','Avenida Caxangá','Carmo','2355'),('99963901794','Gustavo Oliveira','(81) 98697-9527','Jaboatão dos Guararapes','Rua da Hora','Piedade','56'),('99964529139','Ricardo Fernandes Ribeiro','(81) 98770-1231','Jaboatão dos Guararapes','Rua Real da Torre','Candeias','310'),('99966762698','Leonardo Santos','(81) 98812-5177','Olinda','Avenida Conselheiro Aguiar','Bairro Novo','105'),('99967328274','Luciana Martins Cardoso','(81) 98282-2650','Olinda','Rua da Aurora','Bairro Novo','2395'),('99968138193','Diego Lopes Leite','(81) 98238-7970','Recife','Rua das Graças','Apipucos','2021'),('99969139614','Vanessa Melo Fernandes','(81) 98981-9404','Paulista','Rua das Graças','Janga','2367'),('99969751506','Gustavo Tavares','(81) 98549-3172','Recife','Rua Real da Torre','Torre','404'),('99971501120','Gustavo Correia','(81) 98005-2322','Recife','Rua Real da Torre','Tamarineira','411'),('99977611629','Larissa Cavalcanti','(81) 99182-6062','Olinda','Rua da Aurora','Rio Doce','1499'),('99977828520','Vanessa Albuquerque','(81) 98980-7585','Recife','Rua Padre Carapuceiro','Aflitos','1215'),('99978005242','Rodrigo Pinto','(81) 99020-7064','Recife','Rua Real da Torre','Aflitos','1694'),('99978749195','Igor Moreira Ribeiro','(81) 99967-8507','Paulista','Rua das Graças','Maranguape','1484'),('99980875208','Paulo Barros','(81) 99405-7130','Jaboatão dos Guararapes','Rua das Graças','Prazeres','1040'),('99981846147','Thiago Silva','(81) 98643-4444','Recife','Rua Setúbal','Torre','1434'),('99982618725','Henrique Almeida','(81) 98172-7844','Recife','Rua Dom Bosco','Graças','1263'),('99983889293','Camila Almeida','(81) 99373-2680','Recife','Rua do Sol','Várzea','1935'),('99988220685','Samuel Freitas','(81) 98156-6577','Recife','Rua do Hospício','Encruzilhada','2213'),('99988837763','Patrícia Carvalho','(81) 98549-6853','Recife','Avenida Conselheiro Aguiar','Boa Viagem','1293'),('99989598703','Rodrigo Gomes Souza','(81) 99643-4039','Recife','Rua Real da Torre','Santo Amaro','1294');
/*!40000 ALTER TABLE `Dono` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Especialidade`
--

DROP TABLE IF EXISTS `Especialidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Especialidade` (
  `Codigo` int NOT NULL,
  `Descricao` varchar(100) NOT NULL,
  PRIMARY KEY (`Codigo`),
  UNIQUE KEY `uq_especialidade_descricao` (`Descricao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Especialidade`
--

LOCK TABLES `Especialidade` WRITE;
/*!40000 ALTER TABLE `Especialidade` DISABLE KEYS */;
INSERT INTO `Especialidade` VALUES (14,'Acupuntura Veterinária'),(17,'Anestesiologia Veterinária'),(28,'Bem-Estar Animal'),(5,'Cardiologia Veterinária'),(1,'Cirurgia Geral'),(30,'Cirurgia Plástica e Reparadora'),(2,'Clínica Médica de Pequenos Animais'),(20,'Comportamento Animal (Etologia)'),(3,'Dermatologia Veterinária'),(18,'Diagnóstico por Imagem'),(9,'Endocrinologia Veterinária'),(27,'Farmacologia Veterinária'),(15,'Fisioterapia e Reabilitação'),(22,'Gastroenterologia Veterinária'),(26,'Geriatria Veterinária'),(23,'Infectologia Veterinária'),(12,'Medicina Felina'),(29,'Medicina Intensiva (UTI)'),(10,'Nefrologia e Urologia'),(25,'Neonatologia Veterinária'),(8,'Neurologia Veterinária'),(16,'Nutrologia de Cães e Gatos'),(11,'Odontologia Veterinária'),(6,'Oftalmologia Veterinária'),(7,'Oncologia Veterinária'),(4,'Ortopedia'),(19,'Patologia Clínica Veterinária'),(21,'Pneumologia Veterinária'),(13,'Silvestres e Exóticos'),(24,'Toxicologia Veterinária');
/*!40000 ALTER TABLE `Especialidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Exame`
--

DROP TABLE IF EXISTS `Exame`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Exame` (
  `Codigo` int NOT NULL,
  `Descricao` varchar(100) NOT NULL,
  `fk_Atendimento_Codigo` int NOT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `fk_exame_atendimento` (`fk_Atendimento_Codigo`),
  CONSTRAINT `fk_exame_atendimento` FOREIGN KEY (`fk_Atendimento_Codigo`) REFERENCES `Atendimento` (`Codigo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Exame`
--

LOCK TABLES `Exame` WRITE;
/*!40000 ALTER TABLE `Exame` DISABLE KEYS */;
INSERT INTO `Exame` VALUES (1,'Hemograma Completo',1),(2,'Citologia de Ouvido',2),(3,'Raio-X de Pata',3),(4,'Perfil Bioquímico',4),(5,'Raspado Cutâneo',5),(6,'Ultrassonografia Abdominal',6),(7,'Fecal de Rotina',7),(8,'Teste de Schirmer',8),(9,'Ecocardiograma',9),(10,'Urina Tipo 1',10),(11,'Avaliação Periodontal',11),(12,'Raio-X Total',12),(13,'Hemograma de Controle',13),(14,'Teste Alérgico',14),(15,'Laparoscopia Diagnóstica',15),(16,'Ressonância Magnética',16),(17,'Dosagem de Creatinina e Ureia',17),(18,'Tomografia Computadorizada',18),(19,'Perfil Hormonal',19),(20,'Avaliação Comportamental',20),(21,'Sorologia para Peçonhentos',21),(22,'Ultrassom Renal',22),(23,'Hemograma Geriátrico',23),(24,'Cateterismo Vesical',24),(25,'Fisioterapia Avaliativa',25),(26,'Curativo e Retirada de Pontos',26),(27,'Histopatológico de Pele',27),(28,'Neonatal Check',28),(29,'Coproparasitológico',29),(30,'Gasometria Arterial',30),(31,'Hemograma completo e bioquímica sérica',1001),(32,'Raio-X digital direcionado',1018),(33,'Ultrassonografia abdominal',1035),(34,'Exame de urina (urinálise e sedimento)',1052),(35,'Raspado de pele e citologia dermatológica',1069),(36,'Eletrocardiograma e ecocardiograma',1086),(37,'Teste rápido sorológico (FIV/FeLV/Hemoparasitas)',1103),(38,'Coproparasitológico de fezes',1120),(39,'Hemograma completo e bioquímica sérica',1137),(40,'Raio-X digital direcionado',1154),(41,'Ultrassonografia abdominal',1171),(42,'Exame de urina (urinálise e sedimento)',1188),(43,'Raspado de pele e citologia dermatológica',1005),(44,'Eletrocardiograma e ecocardiograma',1022),(45,'Teste rápido sorológico (FIV/FeLV/Hemoparasitas)',1039),(46,'Coproparasitológico de fezes',1056),(47,'Hemograma completo e bioquímica sérica',1073),(48,'Raio-X digital direcionado',1090),(49,'Ultrassonografia abdominal',1107),(50,'Exame de urina (urinálise e sedimento)',1124),(51,'Raspado de pele e citologia dermatológica',1141),(52,'Eletrocardiograma e ecocardiograma',1158),(53,'Teste rápido sorológico (FIV/FeLV/Hemoparasitas)',1175),(54,'Coproparasitológico de fezes',1192),(55,'Hemograma completo e bioquímica sérica',1009),(56,'Raio-X digital direcionado',1026),(57,'Ultrassonografia abdominal',1043),(58,'Exame de urina (urinálise e sedimento)',1060),(59,'Raspado de pele e citologia dermatológica',1077),(60,'Eletrocardiograma e ecocardiograma',1094),(61,'Teste rápido sorológico (FIV/FeLV/Hemoparasitas)',1111),(62,'Coproparasitológico de fezes',1128),(63,'Hemograma completo e bioquímica sérica',1145),(64,'Raio-X digital direcionado',1162),(65,'Ultrassonografia abdominal',1179),(66,'Exame de urina (urinálise e sedimento)',1196),(67,'Raspado de pele e citologia dermatológica',1013),(68,'Eletrocardiograma e ecocardiograma',1030),(69,'Teste rápido sorológico (FIV/FeLV/Hemoparasitas)',1047),(70,'Coproparasitológico de fezes',1064),(71,'Hemograma completo e bioquímica sérica',1081),(72,'Raio-X digital direcionado',1098),(73,'Ultrassonografia abdominal',1115),(74,'Exame de urina (urinálise e sedimento)',1132),(75,'Raspado de pele e citologia dermatológica',1149),(76,'Eletrocardiograma e ecocardiograma',1166),(77,'Teste rápido sorológico (FIV/FeLV/Hemoparasitas)',1183),(78,'Coproparasitológico de fezes',1200),(79,'Hemograma completo e bioquímica sérica',1017),(80,'Raio-X digital direcionado',1034),(81,'Ultrassonografia abdominal',1051),(82,'Exame de urina (urinálise e sedimento)',1068),(83,'Raspado de pele e citologia dermatológica',1085),(84,'Eletrocardiograma e ecocardiograma',1102),(85,'Teste rápido sorológico (FIV/FeLV/Hemoparasitas)',1119),(86,'Coproparasitológico de fezes',1136),(87,'Hemograma completo e bioquímica sérica',1153),(88,'Raio-X digital direcionado',1170),(89,'Ultrassonografia abdominal',1187),(90,'Exame de urina (urinálise e sedimento)',1004),(91,'Raspado de pele e citologia dermatológica',1021),(92,'Eletrocardiograma e ecocardiograma',1038),(93,'Teste rápido sorológico (FIV/FeLV/Hemoparasitas)',1055),(94,'Coproparasitológico de fezes',1072),(95,'Hemograma completo e bioquímica sérica',1089),(96,'Raio-X digital direcionado',1106),(97,'Ultrassonografia abdominal',1123),(98,'Exame de urina (urinálise e sedimento)',1140),(99,'Raspado de pele e citologia dermatológica',1157),(100,'Eletrocardiograma e ecocardiograma',1174),(101,'Teste rápido sorológico (FIV/FeLV/Hemoparasitas)',1191),(102,'Coproparasitológico de fezes',1008),(103,'Hemograma completo e bioquímica sérica',1025),(104,'Raio-X digital direcionado',1042),(105,'Ultrassonografia abdominal',1059),(106,'Exame de urina (urinálise e sedimento)',1076),(107,'Raspado de pele e citologia dermatológica',1093),(108,'Eletrocardiograma e ecocardiograma',1110),(109,'Teste rápido sorológico (FIV/FeLV/Hemoparasitas)',1127),(110,'Coproparasitológico de fezes',1144),(111,'Hemograma completo e bioquímica sérica',1161),(112,'Raio-X digital direcionado',1178),(113,'Ultrassonografia abdominal',1195),(114,'Exame de urina (urinálise e sedimento)',1012),(115,'Raspado de pele e citologia dermatológica',1029),(116,'Eletrocardiograma e ecocardiograma',1046),(117,'Teste rápido sorológico (FIV/FeLV/Hemoparasitas)',1063),(118,'Coproparasitológico de fezes',1080),(119,'Hemograma completo e bioquímica sérica',1097),(120,'Raio-X digital direcionado',1114),(121,'Ultrassonografia abdominal',1131),(122,'Exame de urina (urinálise e sedimento)',1148),(123,'Raspado de pele e citologia dermatológica',1165),(124,'Eletrocardiograma e ecocardiograma',1182),(125,'Teste rápido sorológico (FIV/FeLV/Hemoparasitas)',1199),(126,'Coproparasitológico de fezes',1016),(127,'Hemograma completo e bioquímica sérica',1033),(128,'Raio-X digital direcionado',1050),(129,'Ultrassonografia abdominal',1067),(130,'Exame de urina (urinálise e sedimento)',1084),(131,'Raspado de pele e citologia dermatológica',1101),(132,'Eletrocardiograma e ecocardiograma',1118),(133,'Teste rápido sorológico (FIV/FeLV/Hemoparasitas)',1135),(134,'Coproparasitológico de fezes',1152),(135,'Hemograma completo e bioquímica sérica',1169),(136,'Raio-X digital direcionado',1186),(137,'Ultrassonografia abdominal',1003),(138,'Exame de urina (urinálise e sedimento)',1020),(139,'Raspado de pele e citologia dermatológica',1037),(140,'Eletrocardiograma e ecocardiograma',1054),(141,'Teste rápido sorológico (FIV/FeLV/Hemoparasitas)',1071),(142,'Coproparasitológico de fezes',1088),(143,'Hemograma completo e bioquímica sérica',1105),(144,'Raio-X digital direcionado',1122),(145,'Ultrassonografia abdominal',1139),(146,'Exame de urina (urinálise e sedimento)',1156),(147,'Raspado de pele e citologia dermatológica',1173);
/*!40000 ALTER TABLE `Exame` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Funcionario`
--

DROP TABLE IF EXISTS `Funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Funcionario` (
  `CPF` char(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Telefone` varchar(20) DEFAULT NULL,
  `Rua` varchar(100) DEFAULT NULL,
  `Bairro` varchar(60) DEFAULT NULL,
  `Numero` varchar(10) DEFAULT NULL,
  `Cidade` varchar(60) DEFAULT NULL,
  `CRV` varchar(20) DEFAULT NULL,
  `Funcionario_TIPO` int NOT NULL,
  PRIMARY KEY (`CPF`),
  UNIQUE KEY `uq_funcionario_crv` (`CRV`),
  CONSTRAINT `chk_funcionario_crv_veterinario` CHECK ((((`Funcionario_TIPO` = 1) and (`CRV` is not null)) or ((`Funcionario_TIPO` <> 1) and (`CRV` is null)))),
  CONSTRAINT `chk_funcionario_tipo` CHECK ((`Funcionario_TIPO` in (1,2,3,4)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Funcionario`
--

LOCK TABLES `Funcionario` WRITE;
/*!40000 ALTER TABLE `Funcionario` DISABLE KEYS */;
INSERT INTO `Funcionario` VALUES ('10000000001','Dr. Carlos Silva','(81) 98888-0001','Rua A','Boa Viagem','100','Recife','CRV-PE 1001',1),('10000000002','Dra. Beatriz Souza','(81) 98888-0002','Rua B','Graças','200','Recife','CRV-PE 1002',1),('10000000003','Dr. Fernando Lima','(81) 98888-0003','Rua C','Espinheiro','300','Recife','CRV-PE 1003',1),('10000000004','Dr. Roberto Dias','(81) 98888-0004','Rua D','Madalena','400','Recife','CRV-PE 1004',1),('10000000005','Dra. Camila Rocha','(81) 98888-0005','Rua E','Aflitos','500','Recife','CRV-PE 1005',1),('10000000006','Dr. Paulo Mendes','(81) 98888-0006','Rua F','Derby','600','Recife','CRV-PE 1006',1),('10000000007','Dra. Larissa Azevedo','(81) 98888-0007','Rua G','Parnamirim','700','Recife','CRV-PE 1007',1),('10000000008','Dr. Marcelo Farias','(81) 98888-0008','Rua H','Casa Amarela','800','Recife','CRV-PE 1008',1),('10000000009','Dra. Juliana Freitas','(81) 98888-0009','Rua I','Boa Vista','900','Recife','CRV-PE 1009',1),('10000000010','Dr. Eduardo Nunes','(81) 98888-0010','Rua J','Santo Amaro','1000','Recife','CRV-PE 1010',1),('12345678901','Dr. Vet Teste',NULL,NULL,NULL,NULL,NULL,'CRV12345',1),('20000000001','Marcos Assistente 1','(81) 97777-0001','Av 1','Piedade','10','Jaboatão',NULL,2),('20000000002','Ana Assistente 2','(81) 97777-0002','Av 2','Candeias','20','Jaboatão',NULL,2),('20000000003','Bruno Assistente 3','(81) 97777-0003','Av 3','Boa Viagem','30','Recife',NULL,2),('20000000004','Carla Assistente 4','(81) 97777-0004','Av 4','Imbiribeira','40','Recife',NULL,2),('20000000005','Daniel Assistente 5','(81) 97777-0005','Av 5','Espinheiro','50','Recife',NULL,2),('20000000006','Elaine Assistente 6','(81) 97777-0006','Av 6','Graças','60','Recife',NULL,2),('20000000007','Felipe Assistente 7','(81) 97777-0007','Av 7','Madalena','70','Recife',NULL,2),('20000000008','Gisele Assistente 8','(81) 97777-0008','Av 8','Torre','80','Recife',NULL,2),('20000000009','Higor Assistente 9','(81) 97777-0009','Av 9','Bongi','90','Recife',NULL,2),('20000000010','Jéssica Assistente 10','(81) 97777-0010','Av 10','Afogados','100','Recife',NULL,2),('30000000001','Recepcionista Ana','(81) 96666-0001','Rua K','Boa Viagem','11','Recife',NULL,3),('30000000002','Recepcionista Beto','(81) 96666-0002','Rua L','Pina','22','Recife',NULL,3),('30000000003','Recepcionista Clara','(81) 96666-0003','Rua M','Derby','33','Recife',NULL,3),('30000000004','Recepcionista Diego','(81) 96666-0004','Rua N','Espinheiro','44','Recife',NULL,3),('30000000005','Recepcionista Elena','(81) 96666-0005','Rua O','Graças','55','Recife',NULL,3),('40000000001','Limpeza João','(81) 95555-0001','Rua P','São José','1','Recife',NULL,4),('40000000002','Limpeza Maria','(81) 95555-0002','Rua Q','Santo Antônio','2','Recife',NULL,4),('40000000003','Limpeza Pedro','(81) 95555-0003','Rua R','Boa Vista','3','Recife',NULL,4),('40000000004','Limpeza Rosa','(81) 95555-0004','Rua S','Recife Antigo','4','Recife',NULL,4),('40000000005','Limpeza Tiago','(81) 95555-0005','Rua T','Madalena','5','Recife',NULL,4);
/*!40000 ALTER TABLE `Funcionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Log_Atendimento`
--

DROP TABLE IF EXISTS `Log_Atendimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Log_Atendimento` (
  `Log_Codigo` int NOT NULL AUTO_INCREMENT,
  `Mensagem` varchar(255) DEFAULT NULL,
  `Data_Log` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Log_Codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Log_Atendimento`
--

LOCK TABLES `Log_Atendimento` WRITE;
/*!40000 ALTER TABLE `Log_Atendimento` DISABLE KEYS */;
INSERT INTO `Log_Atendimento` VALUES (1,'Novo atendimento registado para o animal ID: 1 com custo R$ 150.00','2026-11-12 01:24:02'),(2,'Novo atendimento registado para o animal ID: 1 com custo R$ 0.00','2026-11-12 01:24:07'),(3,'Novo atendimento registado para o animal ID: 330 com custo R$ 290.00','2026-11-19 02:31:28'),(4,'Novo atendimento registado para o animal ID: 313 com custo R$ 115.00','2026-11-19 02:31:28'),(5,'Novo atendimento registado para o animal ID: 336 com custo R$ 5290.00','2026-11-19 02:31:28'),(6,'Novo atendimento registado para o animal ID: 330 com custo R$ 1140.00','2026-11-19 02:31:28'),(7,'Novo atendimento registado para o animal ID: 316 com custo R$ 70.00','2026-11-19 02:31:28'),(8,'Novo atendimento registado para o animal ID: 323 com custo R$ 1385.00','2026-11-19 02:31:28'),(9,'Novo atendimento registado para o animal ID: 302 com custo R$ 285.00','2026-11-19 02:31:28'),(10,'Novo atendimento registado para o animal ID: 324 com custo R$ 325.00','2026-11-19 02:31:28'),(11,'Novo atendimento registado para o animal ID: 332 com custo R$ 900.00','2026-11-19 02:31:28'),(12,'Novo atendimento registado para o animal ID: 347 com custo R$ 120.00','2026-11-19 02:31:28'),(13,'Novo atendimento registado para o animal ID: 314 com custo R$ 65.00','2026-11-19 02:31:28'),(14,'Novo atendimento registado para o animal ID: 342 com custo R$ 415.00','2026-11-19 02:31:28'),(15,'Novo atendimento registado para o animal ID: 307 com custo R$ 130.00','2026-11-19 02:31:28'),(16,'Novo atendimento registado para o animal ID: 332 com custo R$ 65.00','2026-11-19 02:31:28'),(17,'Novo atendimento registado para o animal ID: 338 com custo R$ 1305.00','2026-11-19 02:31:28'),(18,'Novo atendimento registado para o animal ID: 323 com custo R$ 335.00','2026-11-19 02:31:28'),(19,'Novo atendimento registado para o animal ID: 317 com custo R$ 110.00','2026-11-19 02:31:28'),(20,'Novo atendimento registado para o animal ID: 300 com custo R$ 375.00','2026-11-19 02:31:28'),(21,'Novo atendimento registado para o animal ID: 306 com custo R$ 365.00','2026-11-19 02:31:28'),(22,'Novo atendimento registado para o animal ID: 330 com custo R$ 3945.00','2026-11-19 02:31:28'),(23,'Novo atendimento registado para o animal ID: 304 com custo R$ 7160.00','2026-11-19 02:31:28'),(24,'Novo atendimento registado para o animal ID: 359 com custo R$ 1500.00','2026-11-19 02:31:28'),(25,'Novo atendimento registado para o animal ID: 342 com custo R$ 365.00','2026-11-19 02:31:28'),(26,'Novo atendimento registado para o animal ID: 307 com custo R$ 475.00','2026-11-19 02:31:28'),(27,'Novo atendimento registado para o animal ID: 332 com custo R$ 255.00','2026-11-19 02:31:28'),(28,'Novo atendimento registado para o animal ID: 328 com custo R$ 150.00','2026-11-19 02:31:28'),(29,'Novo atendimento registado para o animal ID: 319 com custo R$ 260.00','2026-11-19 02:31:28'),(30,'Novo atendimento registado para o animal ID: 351 com custo R$ 905.00','2026-11-19 02:31:28'),(31,'Novo atendimento registado para o animal ID: 332 com custo R$ 2700.00','2026-11-19 02:31:28'),(32,'Novo atendimento registado para o animal ID: 341 com custo R$ 590.00','2026-11-19 02:31:28'),(33,'Novo atendimento registado para o animal ID: 310 com custo R$ 110.00','2026-11-19 02:31:28'),(34,'Novo atendimento registado para o animal ID: 330 com custo R$ 150.00','2026-11-19 02:31:28'),(35,'Novo atendimento registado para o animal ID: 333 com custo R$ 245.00','2026-11-19 02:31:28'),(36,'Novo atendimento registado para o animal ID: 327 com custo R$ 1385.00','2026-11-19 02:31:28'),(37,'Novo atendimento registado para o animal ID: 307 com custo R$ 165.00','2026-11-19 02:31:28'),(38,'Novo atendimento registado para o animal ID: 323 com custo R$ 60.00','2026-11-19 02:31:28'),(39,'Novo atendimento registado para o animal ID: 323 com custo R$ 1725.00','2026-11-19 02:31:28'),(40,'Novo atendimento registado para o animal ID: 312 com custo R$ 775.00','2026-11-19 02:31:28'),(41,'Novo atendimento registado para o animal ID: 312 com custo R$ 135.00','2026-11-19 02:31:28'),(42,'Novo atendimento registado para o animal ID: 298 com custo R$ 105.00','2026-11-19 02:31:28'),(43,'Novo atendimento registado para o animal ID: 315 com custo R$ 255.00','2026-11-19 02:31:28'),(44,'Novo atendimento registado para o animal ID: 330 com custo R$ 60.00','2026-11-19 02:31:28'),(45,'Novo atendimento registado para o animal ID: 334 com custo R$ 965.00','2026-11-19 02:31:28'),(46,'Novo atendimento registado para o animal ID: 330 com custo R$ 600.00','2026-11-19 02:31:28'),(47,'Novo atendimento registado para o animal ID: 317 com custo R$ 130.00','2026-11-19 02:31:28'),(48,'Novo atendimento registado para o animal ID: 317 com custo R$ 370.00','2026-11-19 02:31:28'),(49,'Novo atendimento registado para o animal ID: 313 com custo R$ 90.00','2026-11-19 02:31:28'),(50,'Novo atendimento registado para o animal ID: 323 com custo R$ 130.00','2026-11-19 02:31:28'),(51,'Novo atendimento registado para o animal ID: 317 com custo R$ 60.00','2026-11-19 02:31:28'),(52,'Novo atendimento registado para o animal ID: 333 com custo R$ 1245.00','2026-11-19 02:31:28'),(53,'Novo atendimento registado para o animal ID: 316 com custo R$ 1685.00','2026-11-19 02:31:28'),(54,'Novo atendimento registado para o animal ID: 331 com custo R$ 130.00','2026-11-19 02:31:28'),(55,'Novo atendimento registado para o animal ID: 334 com custo R$ 95.00','2026-11-19 02:31:28'),(56,'Novo atendimento registado para o animal ID: 332 com custo R$ 140.00','2026-11-19 02:31:28'),(57,'Novo atendimento registado para o animal ID: 317 com custo R$ 135.00','2026-11-19 02:31:28'),(58,'Novo atendimento registado para o animal ID: 332 com custo R$ 125.00','2026-11-19 02:31:28'),(59,'Novo atendimento registado para o animal ID: 310 com custo R$ 70.00','2026-11-19 02:31:28'),(60,'Novo atendimento registado para o animal ID: 321 com custo R$ 5200.00','2026-11-19 02:31:28'),(61,'Novo atendimento registado para o animal ID: 334 com custo R$ 980.00','2026-11-19 02:31:28'),(62,'Novo atendimento registado para o animal ID: 307 com custo R$ 465.00','2026-11-19 02:31:28'),(63,'Novo atendimento registado para o animal ID: 340 com custo R$ 235.00','2026-11-19 02:31:28'),(64,'Novo atendimento registado para o animal ID: 331 com custo R$ 980.00','2026-11-19 02:31:28'),(65,'Novo atendimento registado para o animal ID: 330 com custo R$ 325.00','2026-11-19 02:31:28'),(66,'Novo atendimento registado para o animal ID: 325 com custo R$ 560.00','2026-11-19 02:31:28'),(67,'Novo atendimento registado para o animal ID: 332 com custo R$ 2275.00','2026-11-19 02:31:28'),(68,'Novo atendimento registado para o animal ID: 330 com custo R$ 125.00','2026-11-19 02:31:28'),(69,'Novo atendimento registado para o animal ID: 340 com custo R$ 140.00','2026-11-19 02:31:28'),(70,'Novo atendimento registado para o animal ID: 333 com custo R$ 125.00','2026-11-19 02:31:28'),(71,'Novo atendimento registado para o animal ID: 353 com custo R$ 160.00','2026-11-19 02:31:28'),(72,'Novo atendimento registado para o animal ID: 334 com custo R$ 260.00','2026-11-19 02:31:28'),(73,'Novo atendimento registado para o animal ID: 334 com custo R$ 155.00','2026-11-19 02:31:28'),(74,'Novo atendimento registado para o animal ID: 350 com custo R$ 370.00','2026-11-19 02:31:28'),(75,'Novo atendimento registado para o animal ID: 331 com custo R$ 1090.00','2026-11-19 02:31:28'),(76,'Novo atendimento registado para o animal ID: 298 com custo R$ 75.00','2026-11-19 02:31:28'),(77,'Novo atendimento registado para o animal ID: 324 com custo R$ 130.00','2026-11-19 02:31:28'),(78,'Novo atendimento registado para o animal ID: 325 com custo R$ 535.00','2026-11-19 02:31:28'),(79,'Novo atendimento registado para o animal ID: 332 com custo R$ 70.00','2026-11-19 02:31:28'),(80,'Novo atendimento registado para o animal ID: 316 com custo R$ 310.00','2026-11-19 02:31:28'),(81,'Novo atendimento registado para o animal ID: 338 com custo R$ 890.00','2026-11-19 02:31:28'),(82,'Novo atendimento registado para o animal ID: 334 com custo R$ 130.00','2026-11-19 02:31:28'),(83,'Novo atendimento registado para o animal ID: 312 com custo R$ 390.00','2026-11-19 02:31:28'),(84,'Novo atendimento registado para o animal ID: 337 com custo R$ 1865.00','2026-11-19 02:31:28'),(85,'Novo atendimento registado para o animal ID: 304 com custo R$ 915.00','2026-11-19 02:31:28'),(86,'Novo atendimento registado para o animal ID: 343 com custo R$ 135.00','2026-11-19 02:31:28'),(87,'Novo atendimento registado para o animal ID: 302 com custo R$ 415.00','2026-11-19 02:31:28'),(88,'Novo atendimento registado para o animal ID: 307 com custo R$ 520.00','2026-11-19 02:31:28'),(89,'Novo atendimento registado para o animal ID: 317 com custo R$ 80.00','2026-11-19 02:31:28'),(90,'Novo atendimento registado para o animal ID: 323 com custo R$ 175.00','2026-11-19 02:31:28'),(91,'Novo atendimento registado para o animal ID: 317 com custo R$ 80.00','2026-11-19 02:31:28'),(92,'Novo atendimento registado para o animal ID: 303 com custo R$ 190.00','2026-11-19 02:31:28'),(93,'Novo atendimento registado para o animal ID: 356 com custo R$ 65.00','2026-11-19 02:31:28'),(94,'Novo atendimento registado para o animal ID: 329 com custo R$ 85.00','2026-11-19 02:31:28'),(95,'Novo atendimento registado para o animal ID: 307 com custo R$ 410.00','2026-11-19 02:31:28'),(96,'Novo atendimento registado para o animal ID: 326 com custo R$ 3700.00','2026-11-19 02:31:28'),(97,'Novo atendimento registado para o animal ID: 361 com custo R$ 355.00','2026-11-19 02:31:28'),(98,'Novo atendimento registado para o animal ID: 308 com custo R$ 3790.00','2026-11-19 02:31:28'),(99,'Novo atendimento registado para o animal ID: 341 com custo R$ 275.00','2026-11-19 02:31:28'),(100,'Novo atendimento registado para o animal ID: 350 com custo R$ 135.00','2026-11-19 02:31:28'),(101,'Novo atendimento registado para o animal ID: 352 com custo R$ 135.00','2026-11-19 02:31:28'),(102,'Novo atendimento registado para o animal ID: 340 com custo R$ 230.00','2026-11-19 02:31:28'),(103,'Novo atendimento registado para o animal ID: 357 com custo R$ 145.00','2026-11-19 02:31:28'),(104,'Novo atendimento registado para o animal ID: 334 com custo R$ 605.00','2026-11-19 02:31:28'),(105,'Novo atendimento registado para o animal ID: 321 com custo R$ 2735.00','2026-11-19 02:31:28'),(106,'Novo atendimento registado para o animal ID: 346 com custo R$ 135.00','2026-11-19 02:31:28'),(107,'Novo atendimento registado para o animal ID: 345 com custo R$ 215.00','2026-11-19 02:31:28'),(108,'Novo atendimento registado para o animal ID: 317 com custo R$ 70.00','2026-11-19 02:31:28'),(109,'Novo atendimento registado para o animal ID: 307 com custo R$ 340.00','2026-11-19 02:31:28'),(110,'Novo atendimento registado para o animal ID: 341 com custo R$ 255.00','2026-11-19 02:31:28'),(111,'Novo atendimento registado para o animal ID: 317 com custo R$ 330.00','2026-11-19 02:31:28'),(112,'Novo atendimento registado para o animal ID: 307 com custo R$ 185.00','2026-11-19 02:31:28'),(113,'Novo atendimento registado para o animal ID: 330 com custo R$ 80.00','2026-11-19 02:31:28'),(114,'Novo atendimento registado para o animal ID: 332 com custo R$ 125.00','2026-11-19 02:31:28'),(115,'Novo atendimento registado para o animal ID: 319 com custo R$ 445.00','2026-11-19 02:31:28'),(116,'Novo atendimento registado para o animal ID: 326 com custo R$ 125.00','2026-11-19 02:31:28'),(117,'Novo atendimento registado para o animal ID: 315 com custo R$ 135.00','2026-11-19 02:31:28'),(118,'Novo atendimento registado para o animal ID: 313 com custo R$ 125.00','2026-11-19 02:31:28'),(119,'Novo atendimento registado para o animal ID: 319 com custo R$ 5610.00','2026-11-19 02:31:28'),(120,'Novo atendimento registado para o animal ID: 359 com custo R$ 140.00','2026-11-19 02:31:28'),(121,'Novo atendimento registado para o animal ID: 326 com custo R$ 155.00','2026-11-19 02:31:28'),(122,'Novo atendimento registado para o animal ID: 320 com custo R$ 405.00','2026-11-19 02:31:28'),(123,'Novo atendimento registado para o animal ID: 323 com custo R$ 2915.00','2026-11-19 02:31:28'),(124,'Novo atendimento registado para o animal ID: 340 com custo R$ 175.00','2026-11-19 02:31:28'),(125,'Novo atendimento registado para o animal ID: 323 com custo R$ 5750.00','2026-11-19 02:31:28'),(126,'Novo atendimento registado para o animal ID: 334 com custo R$ 165.00','2026-11-19 02:31:28'),(127,'Novo atendimento registado para o animal ID: 326 com custo R$ 165.00','2026-11-19 02:31:28'),(128,'Novo atendimento registado para o animal ID: 353 com custo R$ 260.00','2026-11-19 02:31:28'),(129,'Novo atendimento registado para o animal ID: 323 com custo R$ 515.00','2026-11-19 02:31:28'),(130,'Novo atendimento registado para o animal ID: 323 com custo R$ 330.00','2026-11-19 02:31:28'),(131,'Novo atendimento registado para o animal ID: 342 com custo R$ 810.00','2026-11-19 02:31:28'),(132,'Novo atendimento registado para o animal ID: 330 com custo R$ 120.00','2026-11-19 02:31:28'),(133,'Novo atendimento registado para o animal ID: 326 com custo R$ 230.00','2026-11-19 02:31:28'),(134,'Novo atendimento registado para o animal ID: 330 com custo R$ 560.00','2026-11-19 02:31:28'),(135,'Novo atendimento registado para o animal ID: 327 com custo R$ 140.00','2026-11-19 02:31:28'),(136,'Novo atendimento registado para o animal ID: 300 com custo R$ 2325.00','2026-11-19 02:31:28'),(137,'Novo atendimento registado para o animal ID: 326 com custo R$ 950.00','2026-11-19 02:31:28'),(138,'Novo atendimento registado para o animal ID: 317 com custo R$ 765.00','2026-11-19 02:31:28'),(139,'Novo atendimento registado para o animal ID: 356 com custo R$ 3900.00','2026-11-19 02:31:28'),(140,'Novo atendimento registado para o animal ID: 298 com custo R$ 125.00','2026-11-19 02:31:28'),(141,'Novo atendimento registado para o animal ID: 317 com custo R$ 115.00','2026-11-19 02:31:28'),(142,'Novo atendimento registado para o animal ID: 315 com custo R$ 270.00','2026-11-19 02:31:28'),(143,'Novo atendimento registado para o animal ID: 311 com custo R$ 90.00','2026-11-19 02:31:28'),(144,'Novo atendimento registado para o animal ID: 299 com custo R$ 90.00','2026-11-19 02:31:28'),(145,'Novo atendimento registado para o animal ID: 333 com custo R$ 290.00','2026-11-19 02:31:28'),(146,'Novo atendimento registado para o animal ID: 342 com custo R$ 165.00','2026-11-19 02:31:28'),(147,'Novo atendimento registado para o animal ID: 334 com custo R$ 470.00','2026-11-19 02:31:28'),(148,'Novo atendimento registado para o animal ID: 355 com custo R$ 750.00','2026-11-19 02:31:28'),(149,'Novo atendimento registado para o animal ID: 329 com custo R$ 335.00','2026-11-19 02:31:28'),(150,'Novo atendimento registado para o animal ID: 304 com custo R$ 940.00','2026-11-19 02:31:28'),(151,'Novo atendimento registado para o animal ID: 334 com custo R$ 840.00','2026-11-19 02:31:28'),(152,'Novo atendimento registado para o animal ID: 317 com custo R$ 140.00','2026-11-19 02:31:28'),(153,'Novo atendimento registado para o animal ID: 321 com custo R$ 120.00','2026-11-19 02:31:28'),(154,'Novo atendimento registado para o animal ID: 307 com custo R$ 1840.00','2026-11-19 02:31:28'),(155,'Novo atendimento registado para o animal ID: 351 com custo R$ 300.00','2026-11-19 02:31:28'),(156,'Novo atendimento registado para o animal ID: 316 com custo R$ 155.00','2026-11-19 02:31:28'),(157,'Novo atendimento registado para o animal ID: 344 com custo R$ 70.00','2026-11-19 02:31:28'),(158,'Novo atendimento registado para o animal ID: 361 com custo R$ 240.00','2026-11-19 02:31:28'),(159,'Novo atendimento registado para o animal ID: 300 com custo R$ 430.00','2026-11-19 02:31:28'),(160,'Novo atendimento registado para o animal ID: 307 com custo R$ 100.00','2026-11-19 02:31:28'),(161,'Novo atendimento registado para o animal ID: 316 com custo R$ 745.00','2026-11-19 02:31:28'),(162,'Novo atendimento registado para o animal ID: 360 com custo R$ 1170.00','2026-11-19 02:31:28'),(163,'Novo atendimento registado para o animal ID: 334 com custo R$ 385.00','2026-11-19 02:31:28'),(164,'Novo atendimento registado para o animal ID: 349 com custo R$ 1105.00','2026-11-19 02:31:28'),(165,'Novo atendimento registado para o animal ID: 353 com custo R$ 240.00','2026-11-19 02:31:28'),(166,'Novo atendimento registado para o animal ID: 333 com custo R$ 255.00','2026-11-19 02:31:28'),(167,'Novo atendimento registado para o animal ID: 348 com custo R$ 4425.00','2026-11-19 02:31:28'),(168,'Novo atendimento registado para o animal ID: 317 com custo R$ 375.00','2026-11-19 02:31:28'),(169,'Novo atendimento registado para o animal ID: 299 com custo R$ 305.00','2026-11-19 02:31:28'),(170,'Novo atendimento registado para o animal ID: 321 com custo R$ 265.00','2026-11-19 02:31:28'),(171,'Novo atendimento registado para o animal ID: 309 com custo R$ 180.00','2026-11-19 02:31:28'),(172,'Novo atendimento registado para o animal ID: 334 com custo R$ 435.00','2026-11-19 02:31:28'),(173,'Novo atendimento registado para o animal ID: 361 com custo R$ 155.00','2026-11-19 02:31:28'),(174,'Novo atendimento registado para o animal ID: 326 com custo R$ 185.00','2026-11-19 02:31:28'),(175,'Novo atendimento registado para o animal ID: 321 com custo R$ 365.00','2026-11-19 02:31:28'),(176,'Novo atendimento registado para o animal ID: 334 com custo R$ 140.00','2026-11-19 02:31:28'),(177,'Novo atendimento registado para o animal ID: 333 com custo R$ 250.00','2026-11-19 02:31:28'),(178,'Novo atendimento registado para o animal ID: 356 com custo R$ 80.00','2026-11-19 02:31:28'),(179,'Novo atendimento registado para o animal ID: 313 com custo R$ 140.00','2026-11-19 02:31:28'),(180,'Novo atendimento registado para o animal ID: 345 com custo R$ 1200.00','2026-11-19 02:31:28'),(181,'Novo atendimento registado para o animal ID: 313 com custo R$ 105.00','2026-11-19 02:31:28'),(182,'Novo atendimento registado para o animal ID: 326 com custo R$ 1155.00','2026-11-19 02:31:28'),(183,'Novo atendimento registado para o animal ID: 356 com custo R$ 1495.00','2026-11-19 02:31:28'),(184,'Novo atendimento registado para o animal ID: 350 com custo R$ 90.00','2026-11-19 02:31:28'),(185,'Novo atendimento registado para o animal ID: 334 com custo R$ 70.00','2026-11-19 02:31:28'),(186,'Novo atendimento registado para o animal ID: 315 com custo R$ 365.00','2026-11-19 02:31:28'),(187,'Novo atendimento registado para o animal ID: 314 com custo R$ 770.00','2026-11-19 02:31:28'),(188,'Novo atendimento registado para o animal ID: 326 com custo R$ 110.00','2026-11-19 02:31:28'),(189,'Novo atendimento registado para o animal ID: 308 com custo R$ 385.00','2026-11-19 02:31:28'),(190,'Novo atendimento registado para o animal ID: 321 com custo R$ 175.00','2026-11-19 02:31:28'),(191,'Novo atendimento registado para o animal ID: 355 com custo R$ 155.00','2026-11-19 02:31:28'),(192,'Novo atendimento registado para o animal ID: 327 com custo R$ 100.00','2026-11-19 02:31:28'),(193,'Novo atendimento registado para o animal ID: 349 com custo R$ 725.00','2026-11-19 02:31:28'),(194,'Novo atendimento registado para o animal ID: 317 com custo R$ 65.00','2026-11-19 02:31:28'),(195,'Novo atendimento registado para o animal ID: 317 com custo R$ 140.00','2026-11-19 02:31:28'),(196,'Novo atendimento registado para o animal ID: 298 com custo R$ 130.00','2026-11-19 02:31:28'),(197,'Novo atendimento registado para o animal ID: 309 com custo R$ 240.00','2026-11-19 02:31:28'),(198,'Novo atendimento registado para o animal ID: 322 com custo R$ 310.00','2026-11-19 02:31:28'),(199,'Novo atendimento registado para o animal ID: 335 com custo R$ 360.00','2026-11-19 02:31:28'),(200,'Novo atendimento registado para o animal ID: 354 com custo R$ 1280.00','2026-11-19 02:31:28'),(201,'Novo atendimento registado para o animal ID: 358 com custo R$ 1920.00','2026-11-19 02:31:28'),(202,'Novo atendimento registado para o animal ID: 361 com custo R$ 4150.00','2026-11-19 02:31:28');
/*!40000 ALTER TABLE `Log_Atendimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Medicamento`
--

DROP TABLE IF EXISTS `Medicamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Medicamento` (
  `Codigo` int NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Tarja` varchar(20) DEFAULT 'Nenhuma',
  PRIMARY KEY (`Codigo`),
  CONSTRAINT `chk_medicamento_tarja` CHECK ((`Tarja` in (_utf8mb4'Nenhuma',_utf8mb4'Amarela',_utf8mb4'Vermelha',_utf8mb4'Preta')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Medicamento`
--

LOCK TABLES `Medicamento` WRITE;
/*!40000 ALTER TABLE `Medicamento` DISABLE KEYS */;
INSERT INTO `Medicamento` VALUES (1,'Dipirona Veterinária 500mg','Nenhuma'),(2,'Meloxicam 2mg','Vermelha'),(3,'Amoxicilina com Clavulanato','Vermelha'),(4,'Cloridrato de Tramadol','Preta'),(5,'Prednisolona 20mg','Vermelha'),(6,'Omeprazol 10mg','Nenhuma'),(7,'Ivermectina 1%','Amarela'),(8,'Ketamina 10%','Preta'),(9,'Furosemida 40mg','Vermelha'),(10,'Enrofloxacina 50mg','Vermelha'),(11,'Ranitidina 150mg','Nenhuma'),(12,'Sulfadiazina + Trimetoprima','Vermelha'),(13,'Clorexidina Solução','Nenhuma'),(14,'Dexametasona 2mg','Vermelha'),(15,'Cefalexina 250mg','Vermelha'),(16,'Gabapentina 300mg','Preta'),(17,'Marbofloxacina 20mg','Vermelha'),(18,'Cloranfenicol 500mg','Vermelha'),(19,'Metronidazol 250mg','Vermelha'),(20,'Sulfassalazina 500mg','Vermelha'),(21,'Atropina 1%','Amarela'),(22,'Acepromazina 0,2%','Preta'),(23,'Xilazina 2%','Preta'),(24,'Propofol 1%','Preta'),(25,'Tramadol 50mg','Preta'),(26,'Carprofeno 25mg','Vermelha'),(27,'Firocoxib 57mg','Vermelha'),(28,'Ondansetrona 4mg','Nenhuma'),(29,'Sucralfato 1g','Nenhuma'),(30,'Lactulona Xarope','Nenhuma');
/*!40000 ALTER TABLE `Medicamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Plantao`
--

DROP TABLE IF EXISTS `Plantao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Plantao` (
  `Codigo` int NOT NULL,
  `Dia` int NOT NULL,
  `Mes` int NOT NULL,
  `Ano` int NOT NULL,
  `Horario_Inicio` time NOT NULL,
  `Horario_Fim` time NOT NULL,
  `fk_Funcionario_CPF` char(11) NOT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `fk_plantao_funcionario` (`fk_Funcionario_CPF`),
  CONSTRAINT `fk_plantao_funcionario` FOREIGN KEY (`fk_Funcionario_CPF`) REFERENCES `Funcionario` (`CPF`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_plantao_ano` CHECK ((`Ano` between 1900 and 2100)),
  CONSTRAINT `chk_plantao_dia` CHECK ((`Dia` between 1 and 31)),
  CONSTRAINT `chk_plantao_horario` CHECK ((`Horario_Fim` > `Horario_Inicio`)),
  CONSTRAINT `chk_plantao_mes` CHECK ((`Mes` between 1 and 12))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Plantao`
--

LOCK TABLES `Plantao` WRITE;
/*!40000 ALTER TABLE `Plantao` DISABLE KEYS */;
INSERT INTO `Plantao` VALUES (1,1,6,2026,'08:00:00','14:00:00','10000000001'),(2,2,6,2026,'14:00:00','22:00:00','10000000002'),(3,3,6,2026,'07:00:00','13:00:00','10000000003'),(4,4,6,2026,'13:00:00','19:00:00','10000000004'),(5,5,6,2026,'08:00:00','16:00:00','10000000005'),(6,6,6,2026,'12:00:00','20:00:00','10000000006'),(7,7,6,2026,'08:00:00','14:00:00','10000000007'),(8,8,6,2026,'14:00:00','22:00:00','10000000008'),(9,9,6,2026,'07:00:00','13:00:00','10000000009'),(10,10,6,2026,'13:00:00','19:00:00','10000000010'),(11,11,6,2026,'08:00:00','14:00:00','10000000001'),(12,12,6,2026,'14:00:00','22:00:00','10000000002'),(13,13,6,2026,'07:00:00','13:00:00','10000000003'),(14,14,6,2026,'13:00:00','19:00:00','10000000004'),(15,15,6,2026,'08:00:00','16:00:00','10000000005'),(16,16,6,2026,'12:00:00','20:00:00','10000000006'),(17,17,6,2026,'08:00:00','14:00:00','10000000007'),(18,18,6,2026,'14:00:00','22:00:00','10000000008'),(19,19,6,2026,'07:00:00','13:00:00','10000000009'),(20,20,6,2026,'13:00:00','19:00:00','10000000010'),(21,21,6,2026,'08:00:00','14:00:00','10000000001'),(22,22,6,2026,'14:00:00','22:00:00','10000000002'),(23,23,6,2026,'07:00:00','13:00:00','10000000003'),(24,24,6,2026,'13:00:00','19:00:00','10000000004'),(25,25,6,2026,'08:00:00','16:00:00','10000000005'),(26,26,6,2026,'12:00:00','20:00:00','10000000006'),(27,27,6,2026,'08:00:00','14:00:00','10000000007'),(28,28,6,2026,'14:00:00','22:00:00','10000000008'),(29,29,6,2026,'07:00:00','13:00:00','10000000009'),(30,30,6,2026,'13:00:00','19:00:00','10000000010');
/*!40000 ALTER TABLE `Plantao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Receita`
--

DROP TABLE IF EXISTS `Receita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Receita` (
  `fk_Tratamento_Codigo` int NOT NULL,
  `fk_Medicamento_Codigo` int NOT NULL,
  `Horario` time NOT NULL,
  `Quantidade` decimal(10,2) NOT NULL,
  PRIMARY KEY (`fk_Tratamento_Codigo`,`fk_Medicamento_Codigo`,`Horario`),
  KEY `fk_receita_medicamento` (`fk_Medicamento_Codigo`),
  CONSTRAINT `fk_receita_medicamento` FOREIGN KEY (`fk_Medicamento_Codigo`) REFERENCES `Medicamento` (`Codigo`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_receita_tratamento` FOREIGN KEY (`fk_Tratamento_Codigo`) REFERENCES `Tratamento` (`fk_Atendimento_Codigo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `chk_receita_quantidade` CHECK ((`Quantidade` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Receita`
--

LOCK TABLES `Receita` WRITE;
/*!40000 ALTER TABLE `Receita` DISABLE KEYS */;
INSERT INTO `Receita` VALUES (1,1,'08:00:00',1.00),(2,2,'12:00:00',1.50),(3,4,'08:00:00',2.00),(4,6,'07:30:00',1.00),(5,5,'09:00:00',1.00),(6,8,'10:00:00',0.50),(7,1,'08:00:00',1.00),(8,13,'12:00:00',1.00),(9,9,'06:00:00',2.00),(10,11,'08:00:00',1.00),(11,3,'14:00:00',1.50),(12,4,'10:00:00',2.00),(13,1,'08:00:00',1.00),(14,5,'09:00:00',1.00),(15,24,'22:00:00',1.00),(16,16,'08:00:00',1.00),(17,9,'07:00:00',1.50),(18,4,'12:00:00',2.00),(19,6,'08:00:00',1.00),(20,22,'20:00:00',0.50),(21,21,'10:00:00',1.00),(22,10,'08:00:00',2.00),(23,6,'08:00:00',1.00),(24,9,'09:00:00',1.50),(25,26,'12:00:00',1.00),(26,1,'08:00:00',1.00),(27,13,'14:00:00',1.00),(28,15,'08:00:00',2.00),(29,29,'08:00:00',1.00),(30,14,'06:00:00',1.00);
/*!40000 ALTER TABLE `Receita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tratamento`
--

DROP TABLE IF EXISTS `Tratamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Tratamento` (
  `fk_Atendimento_Codigo` int NOT NULL,
  PRIMARY KEY (`fk_Atendimento_Codigo`),
  CONSTRAINT `fk_tratamento_atendimento` FOREIGN KEY (`fk_Atendimento_Codigo`) REFERENCES `Atendimento` (`Codigo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tratamento`
--

LOCK TABLES `Tratamento` WRITE;
/*!40000 ALTER TABLE `Tratamento` DISABLE KEYS */;
INSERT INTO `Tratamento` VALUES (1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11),(12),(13),(14),(15),(16),(17),(18),(19),(20),(21),(22),(23),(24),(25),(26),(27),(28),(29),(30);
/*!40000 ALTER TABLE `Tratamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vw_presricoes_detalhadas`
--

DROP TABLE IF EXISTS `vw_presricoes_detalhadas`;
/*!50001 DROP VIEW IF EXISTS `vw_presricoes_detalhadas`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_presricoes_detalhadas` AS SELECT 
 1 AS `Nome_Animal`,
 1 AS `Raca`,
 1 AS `Nome_Medicamento`,
 1 AS `Tarja`,
 1 AS `Quantidade`,
 1 AS `Horario`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_prontuario_completo`
--

DROP TABLE IF EXISTS `vw_prontuario_completo`;
/*!50001 DROP VIEW IF EXISTS `vw_prontuario_completo`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_prontuario_completo` AS SELECT 
 1 AS `Codigo_Atendimento`,
 1 AS `Dia`,
 1 AS `Mes`,
 1 AS `Ano`,
 1 AS `Horario`,
 1 AS `Nome_Animal`,
 1 AS `Especie`,
 1 AS `Nome_Dono`,
 1 AS `Nome_Funcionario`,
 1 AS `Diagnostico`,
 1 AS `Custo`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'vetcare'
--
/*!50003 DROP FUNCTION IF EXISTS `fn_ClassificarPorte` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  FUNCTION `fn_ClassificarPorte`(p_Animal_Codigo INT) RETURNS varchar(30) CHARSET utf8mb4
    DETERMINISTIC
BEGIN
    DECLARE v_Raca VARCHAR(60);
    DECLARE v_Especie VARCHAR(68);
    DECLARE v_Porte VARCHAR(30);

    SELECT Raca, Especie INTO v_Raca, v_Especie 
    FROM Animal 
    WHERE Codigo = p_Animal_Codigo;

    SET v_Porte = CASE 
        WHEN v_Raca IN ('Mastiff', 'Rottweiler', 'Pastor Alemão', 'Golden Retriever', 'Husky Siberiano') THEN 'Grande Porte'
        WHEN v_Raca IN ('Bulldog', 'Border Collie', 'Boxer', 'Labrador') THEN 'Médio Porte'
        ELSE 'Pequeno Porte / Mini'
    END;

    RETURN v_Porte;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `fn_CustoTotalAnimal` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  FUNCTION `fn_CustoTotalAnimal`(p_Animal_Codigo INT) RETURNS decimal(10,2)
    DETERMINISTIC
BEGIN
    DECLARE v_Total DECIMAL(10,2);
    
    SELECT SUM(Custo) INTO v_Total 
    FROM Atendimento 
    WHERE fk_Animal_Codigo = p_Animal_Codigo;
    
    RETURN IFNULL(v_Total, 0.00);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_AtualizarTelefoneDono` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  PROCEDURE `sp_AtualizarTelefoneDono`(
    IN p_CPF CHAR(11), 
    IN p_NovoTelefone VARCHAR(20)
)
BEGIN
    UPDATE Dono 
    SET Telefone = p_NovoTelefone 
    WHERE CPF = p_CPF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_RelatorioResumoAnimal` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE  PROCEDURE `sp_RelatorioResumoAnimal`(
    IN p_Animal_Codigo INT
)
BEGIN
    SELECT 
        a.Codigo AS CodigoAnimal,
        a.Nome AS NomeAnimal,
        a.Raca,
        d.CPF AS CpfDono,
        d.Nome AS NomeDono,
        d.Telefone AS TelefoneDono,
        CONCAT(at.Dia, '/', at.Mes, '/', at.Ano) AS DataAtendimento,
        at.Diagnostico,
        at.Custo
    FROM Animal a
    JOIN Dono d ON a.fk_Dono_CPF = d.CPF
    LEFT JOIN Atendimento at ON a.Codigo = at.fk_Animal_Codigo
    WHERE (p_Animal_Codigo IS NULL OR a.Codigo = p_Animal_Codigo);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `vw_presricoes_detalhadas`
--

/*!50001 DROP VIEW IF EXISTS `vw_presricoes_detalhadas`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013  SQL SECURITY DEFINER */
/*!50001 VIEW `vw_presricoes_detalhadas` AS select `an`.`Nome` AS `Nome_Animal`,`an`.`Raca` AS `Raca`,`m`.`Nome` AS `Nome_Medicamento`,`m`.`Tarja` AS `Tarja`,`r`.`Quantidade` AS `Quantidade`,`r`.`Horario` AS `Horario` from ((((`receita` `r` join `tratamento` `t` on((`r`.`fk_Tratamento_Codigo` = `t`.`fk_Atendimento_Codigo`))) join `atendimento` `at` on((`t`.`fk_Atendimento_Codigo` = `at`.`Codigo`))) join `animal` `an` on((`at`.`fk_Animal_Codigo` = `an`.`Codigo`))) join `medicamento` `m` on((`r`.`fk_Medicamento_Codigo` = `m`.`Codigo`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_prontuario_completo`
--

/*!50001 DROP VIEW IF EXISTS `vw_prontuario_completo`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013  SQL SECURITY DEFINER */
/*!50001 VIEW `vw_prontuario_completo` AS select `at`.`Codigo` AS `Codigo_Atendimento`,`at`.`Dia` AS `Dia`,`at`.`Mes` AS `Mes`,`at`.`Ano` AS `Ano`,`at`.`Horario` AS `Horario`,`an`.`Nome` AS `Nome_Animal`,`an`.`Especie` AS `Especie`,`dn`.`Nome` AS `Nome_Dono`,`fn`.`Nome` AS `Nome_Funcionario`,`at`.`Diagnostico` AS `Diagnostico`,`at`.`Custo` AS `Custo` from (((`atendimento` `at` join `animal` `an` on((`at`.`fk_Animal_Codigo` = `an`.`Codigo`))) join `dono` `dn` on((`an`.`fk_Dono_CPF` = `dn`.`CPF`))) join `funcionario` `fn` on((`at`.`fk_Funcionario_CPF` = `fn`.`CPF`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-25 20:19:03
