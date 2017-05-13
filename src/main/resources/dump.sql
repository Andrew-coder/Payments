-- MySQL dump 10.13  Distrib 5.7.18, for Linux (x86_64)
--
-- Host: localhost    Database: Payment
-- ------------------------------------------------------
-- Server version	5.7.18-0ubuntu0.16.04.1
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO,NO_KEY_OPTIONS,NO_TABLE_OPTIONS,NO_FIELD_OPTIONS,ANSI' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table "BankAccounts"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "BankAccounts" (
  "account_id" bigint(20) NOT NULL,
  "account_number" varchar(45) NOT NULL,
  "balance" decimal(13,2) NOT NULL,
  PRIMARY KEY ("account_id"),
  UNIQUE KEY "account_number_UNIQUE" ("account_number")
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table "BankAccounts"
--

INSERT INTO "BankAccounts" VALUES (1,'31257289113853',34231.00);
INSERT INTO "BankAccounts" VALUES (2,'40348198204762',41560.50);
INSERT INTO "BankAccounts" VALUES (3,'68520916486580',89683.00);
INSERT INTO "BankAccounts" VALUES (4,'57419027575691',10000.00);

--
-- Table structure for table "BlockCards"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "BlockCards" (
  "id" bigint(20) NOT NULL,
  "card_id" bigint(20) NOT NULL,
  PRIMARY KEY ("id"),
  KEY "fk_BlockCards_Cards1_idx" ("card_id"),
  CONSTRAINT "fk_BlockCards_Cards1" FOREIGN KEY ("card_id") REFERENCES "Cards" ("card_id") ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table "BlockCards"
--


--
-- Table structure for table "Cards"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "Cards" (
  "card_id" bigint(20) NOT NULL,
  "card_number" varchar(45) NOT NULL,
  "pin" varchar(45) NOT NULL,
  "cvv" varchar(45) NOT NULL,
  "expire_date" date NOT NULL,
  "account_id" bigint(20) NOT NULL,
  "cellphone" varchar(45) NOT NULL,
  PRIMARY KEY ("card_id"),
  UNIQUE KEY "card_number_UNIQUE" ("card_number"),
  KEY "fk_Cards_BancAccounts_idx" ("account_id"),
  CONSTRAINT "fk_Cards_BancAccounts" FOREIGN KEY ("account_id") REFERENCES "BankAccounts" ("account_id") ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table "Cards"
--

INSERT INTO "Cards" VALUES (1,'4102471200041788','1111','312','2018-08-05',1,'0631016341');
INSERT INTO "Cards" VALUES (2,'5213582311152700','2222','423','2020-04-20',2,'0958026341');
INSERT INTO "Cards" VALUES (3,'9657926755596144','3333','534','2019-02-13',3,'0958085183');
INSERT INTO "Cards" VALUES (4,'8546037866605033','4444','645','2019-05-15',4,'0653024579');

--
-- Table structure for table "Payments"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "Payments" (
  "payment_id" bigint(20) NOT NULL,
  "sender" bigint(20) DEFAULT NULL,
  "recipient" bigint(20) DEFAULT NULL,
  "sum" decimal(13,2) NOT NULL,
  "payment_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "payment_type" int(11) NOT NULL,
  "mfo" varchar(45) DEFAULT NULL,
  "usreou" varchar(45) DEFAULT NULL,
  "payment_purpose" varchar(45) DEFAULT NULL,
  PRIMARY KEY ("payment_id"),
  KEY "fk_Payments_BancAccounts1_idx" ("sender"),
  KEY "fk_Payments_BancAccounts2_idx" ("recipient"),
  KEY "fk_Payments_PaymentsTypes1_idx" ("payment_type"),
  CONSTRAINT "fk_Payments_BancAccounts1" FOREIGN KEY ("sender") REFERENCES "BankAccounts" ("account_id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_Payments_BancAccounts2" FOREIGN KEY ("recipient") REFERENCES "BankAccounts" ("account_id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_Payments_PaymentsTypes1" FOREIGN KEY ("payment_type") REFERENCES "PaymentsTypes" ("type_id") ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table "Payments"
--

INSERT INTO "Payments" VALUES (18,2,1,1000.00,'2017-05-07 14:27:02',2,NULL,NULL,'purpose');
INSERT INTO "Payments" VALUES (19,2,1,1500.00,'2017-05-07 15:38:25',2,NULL,NULL,'purpose');
INSERT INTO "Payments" VALUES (20,2,1,2000.00,'2017-05-07 15:42:10',2,NULL,NULL,'purpose');
INSERT INTO "Payments" VALUES (21,NULL,2,300.00,'2017-05-07 15:56:11',1,NULL,NULL,NULL);
INSERT INTO "Payments" VALUES (22,NULL,2,2301.00,'2017-05-07 16:00:16',1,NULL,NULL,NULL);
INSERT INTO "Payments" VALUES (23,NULL,1,430.00,'2017-05-07 16:23:54',1,NULL,NULL,NULL);
INSERT INTO "Payments" VALUES (24,NULL,2,1000.00,'2017-05-07 16:25:20',1,NULL,NULL,NULL);
INSERT INTO "Payments" VALUES (25,1,2,3000.00,'2017-05-07 16:28:12',2,'820172','02070921','payment for hostel');
INSERT INTO "Payments" VALUES (26,1,2,720.00,'2017-05-07 16:28:56',2,NULL,NULL,'purpose');
INSERT INTO "Payments" VALUES (27,2,1,1000.00,'2017-05-07 16:30:34',2,NULL,NULL,'purpose');
INSERT INTO "Payments" VALUES (28,2,1,810.00,'2017-05-07 16:31:18',2,'820172','02070921','simple transfer');
INSERT INTO "Payments" VALUES (29,3,2,300.00,'2017-05-09 15:18:44',2,NULL,NULL,'purpose');

--
-- Table structure for table "PaymentsTypes"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "PaymentsTypes" (
  "type_id" int(11) NOT NULL,
  "payment_name" varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  "payment_rate" double NOT NULL,
  "fixed_rate" decimal(13,2) NOT NULL,
  PRIMARY KEY ("type_id")
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table "PaymentsTypes"
--

INSERT INTO "PaymentsTypes" VALUES (1,'refill',0,0.00);
INSERT INTO "PaymentsTypes" VALUES (2,'transfer within this bank',0.05,1.00);
INSERT INTO "PaymentsTypes" VALUES (3,'transfer to another bank\'s card',0.15,5.00);
INSERT INTO "PaymentsTypes" VALUES (4,'transfer to another card of one user',0,0.00);

--
-- Table structure for table "User_has_cards"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "User_has_cards" (
  "id" int(11) NOT NULL,
  "user_id" bigint(20) NOT NULL,
  "card_id" bigint(20) NOT NULL,
  PRIMARY KEY ("id"),
  KEY "fk_User_has_cards_Users1_idx" ("user_id"),
  KEY "fk_User_has_cards_Cards1_idx" ("card_id"),
  CONSTRAINT "fk_User_has_cards_Cards1" FOREIGN KEY ("card_id") REFERENCES "Cards" ("card_id") ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT "fk_User_has_cards_Users1" FOREIGN KEY ("user_id") REFERENCES "Users" ("user_id") ON DELETE NO ACTION ON UPDATE NO ACTION
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table "User_has_cards"
--

INSERT INTO "User_has_cards" VALUES (11,6,3);
INSERT INTO "User_has_cards" VALUES (12,3,2);
INSERT INTO "User_has_cards" VALUES (13,2,1);

--
-- Table structure for table "Users"
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE "Users" (
  "user_id" bigint(20) NOT NULL,
  "name" varchar(45) NOT NULL,
  "surname" varchar(45) NOT NULL,
  "cellphone" varchar(45) NOT NULL,
  "password" varchar(45) NOT NULL,
  "birthDate" date NOT NULL,
  "role" enum('user','admin') NOT NULL DEFAULT 'user',
  PRIMARY KEY ("user_id"),
  UNIQUE KEY "cellphone_UNIQUE" ("cellphone")
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table "Users"
--

INSERT INTO "Users" VALUES (1,'Andriy','Iwanyuk','0958016294','andriy','1997-04-25','admin');
INSERT INTO "Users" VALUES (2,'John','Doe','0631016341','john','1987-01-15','user');
INSERT INTO "Users" VALUES (3,'Anna','Svift','0958026341','anna','1991-01-10','user');
INSERT INTO "Users" VALUES (6,'Brad','Johnson','0958085183','brad','1995-01-13','user');
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-13 17:13:48
