# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 118.89.46.26 (MySQL 5.6.38)
# Database: ess
# Generation Time: 2017-11-13 09:23:19 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table r_exam_student
# ------------------------------------------------------------

DROP TABLE IF EXISTS `r_exam_student`;

CREATE TABLE `r_exam_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(20) NOT NULL COMMENT '考生邮箱',
  `examId` varchar(15) NOT NULL COMMENT '考试ID',
  `enable` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考生参与考试基本信息';

LOCK TABLES `r_exam_student` WRITE;
/*!40000 ALTER TABLE `r_exam_student` DISABLE KEYS */;

INSERT INTO `r_exam_student` (`id`, `email`, `examId`, `enable`)
VALUES
	(25,'1282976747@qq.com','13',1),
	(26,'1483809252@qq.com','13',1),
	(27,'123@qq.com','26',1),
	(28,'456@qq.com','26',1),
	(29,'1282976747@qq.com','26',1),
	(30,'1483809252@qq.com','26',1),
	(31,'1282976747@qq.com','27',1),
	(32,'1483809252@qq.com','27',1);

/*!40000 ALTER TABLE `r_exam_student` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table r_user_course
# ------------------------------------------------------------

DROP TABLE IF EXISTS `r_user_course`;

CREATE TABLE `r_user_course` (
  `user` int(11) NOT NULL,
  `course` int(11) NOT NULL,
  `enable` tinyint(4) NOT NULL DEFAULT '1',
  UNIQUE KEY `user` (`user`,`course`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-课程关联表';

LOCK TABLES `r_user_course` WRITE;
/*!40000 ALTER TABLE `r_user_course` DISABLE KEYS */;

INSERT INTO `r_user_course` (`user`, `course`, `enable`)
VALUES
	(6,9,1),
	(6,10,1),
	(6,11,1),
	(6,12,1),
	(7,9,1),
	(7,10,0);

/*!40000 ALTER TABLE `r_user_course` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_course
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_course`;

CREATE TABLE `t_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `grade` tinyint(4) NOT NULL COMMENT '年级, 1~4代表大一~大四',
  `class` varchar(255) DEFAULT '' COMMENT '班级, 以逗号分隔, 为空表示全年级。示例: 1,2,3',
  `year` int(11) NOT NULL COMMENT '年份, 2016表示2016~2017学年',
  `term` int(11) NOT NULL COMMENT '学期, 可取1~3',
  `password` varchar(255) NOT NULL COMMENT '选课密码',
  `enable` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程表';

LOCK TABLES `t_course` WRITE;
/*!40000 ALTER TABLE `t_course` DISABLE KEYS */;

INSERT INTO `t_course` (`id`, `name`, `grade`, `class`, `year`, `term`, `password`, `enable`)
VALUES
	(9,'计算机网络',2,'1,2,3,4',2017,2,'grade14',1),
	(10,'操作系统',2,'1,2,3',2017,2,'grade14',1),
	(11,'test',1,'1',2014,1,'asd',1),
	(12,'tes',1,'1,2',2017,2,'t',1);

/*!40000 ALTER TABLE `t_course` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_exam
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_exam`;

CREATE TABLE `t_exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseId` int(11) NOT NULL COMMENT '试卷关联课程ID',
  `name` varchar(30) NOT NULL COMMENT '考试名称',
  `password` varchar(20) NOT NULL COMMENT '考试密码',
  `startTime` datetime NOT NULL COMMENT '考试开始时间',
  `endTime` datetime NOT NULL COMMENT '考试结束时间',
  `num` varchar(255) NOT NULL COMMENT '各难度出的试题数目, 各难度试题数目,以逗号分隔 2,5,6,10,5,2',
  `enable` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考试基本信息';

LOCK TABLES `t_exam` WRITE;
/*!40000 ALTER TABLE `t_exam` DISABLE KEYS */;

INSERT INTO `t_exam` (`id`, `courseId`, `name`, `password`, `startTime`, `endTime`, `num`, `enable`)
VALUES
	(13,9,'数据结构','QSWW573ETDQTUCI','2018-01-01 00:00:00','2018-01-02 00:00:00','1,1,0,1',0),
	(14,9,'操作系统','U68R01ZWAW6M5Y2','2018-01-01 00:00:00','2018-01-02 02:00:00','1,1,0',1),
	(15,9,'数据结构','P40HCCCE70NXP6Y','2018-01-01 00:00:00','2018-01-02 00:00:00','1,1,0',1),
	(16,9,'数据结构','KX6G38USI7MSRYB','2018-01-01 00:00:00','2018-01-02 00:00:00','1,1,0',0),
	(17,9,'数据结构','B9IRYHL56268755','2018-01-01 00:00:00','2018-01-02 00:00:00','1,1,0',0),
	(18,9,'数据结构','89BYY01NNL1BWBB','2018-01-01 00:00:00','2018-01-02 00:00:00','1,1,0',0),
	(19,9,'数据结构','89ZGXXG3N21WZHC','2018-01-01 00:00:00','2018-01-02 00:00:00','1,1,0',0),
	(20,9,'数据结构','479VKOAEQ9IO4QN','2018-01-01 00:00:00','2018-01-02 00:00:00','1,1,0',0),
	(21,9,'数据结构','OZMEJY327NXQYGY','2018-01-01 00:00:00','2018-01-02 00:00:00','1,1,0',0),
	(22,9,'数据结构','9X7A4RUE4TJY8XY','2018-01-01 00:00:00','2018-01-02 00:00:00','1,1,0',0),
	(23,9,'数据结构','8VZTUCDS3TUL226','2018-01-01 00:00:00','2018-01-02 00:00:00','1,1,0',0),
	(26,9,'数据结构','NYH3ICLDM6ZATRE','2018-01-01 00:00:00','2018-01-02 00:00:00','1,1,0',0),
	(27,9,'数据结构','VL0K8T2LLWFGX46','2018-01-01 00:00:00','2018-01-02 00:00:00','1,1,0',0);

/*!40000 ALTER TABLE `t_exam` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_level
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_level`;

CREATE TABLE `t_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseId` int(11) NOT NULL COMMENT '等级关联课程ID',
  `level` int(11) NOT NULL COMMENT '试题等级',
  `mark` float NOT NULL COMMENT '等级对应的分数',
  `examId` int(11) NOT NULL COMMENT '等级对应的考试',
  PRIMARY KEY (`id`),
  UNIQUE KEY `courseId` (`courseId`,`examId`,`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='等级基本信息';

LOCK TABLES `t_level` WRITE;
/*!40000 ALTER TABLE `t_level` DISABLE KEYS */;

INSERT INTO `t_level` (`id`, `courseId`, `level`, `mark`, `examId`)
VALUES
	(49,9,1,10,14),
	(50,9,2,10,14),
	(51,9,3,10,14),
	(52,9,4,10,14),
	(53,9,1,2,15),
	(54,9,2,4,15),
	(55,9,3,4,15),
	(56,9,4,5,15),
	(57,9,1,10,26),
	(58,9,2,10,26),
	(59,9,3,10,26),
	(60,9,4,10,26),
	(61,9,1,10,27),
	(62,9,2,10,27),
	(63,9,3,10,27),
	(64,9,4,10,27);

/*!40000 ALTER TABLE `t_level` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_paper
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_paper`;

CREATE TABLE `t_paper` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '试卷ID',
  `examId` int(11) DEFAULT NULL COMMENT '考试ID',
  `studentEmail` varchar(20) DEFAULT NULL COMMENT '学生邮箱',
  `mark` float DEFAULT NULL COMMENT '考试分数',
  `enable` tinyint(4) DEFAULT '1' COMMENT '是否已经删除',
  `content` text COMMENT 'Json文本,描述考生作答情况[{‘questionId’: 1, ‘answer’:‘1，2，3’}，…]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考卷基本信息';



# Dump of table t_question
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_question`;

CREATE TABLE `t_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseId` int(11) NOT NULL COMMENT '试题关联课程ID',
  `content` text NOT NULL COMMENT '试题内容',
  `md5Value` varchar(255) NOT NULL COMMENT '文件内容MD5的校验值，避免重复上传文件',
  `level` int(11) NOT NULL DEFAULT '0' COMMENT '选择题难度，可以动态设置',
  `answer` varchar(255) NOT NULL COMMENT '选择题答案，多选题以逗号分隔',
  `enable` tinyint(4) NOT NULL DEFAULT '1',
  `optionJson` text NOT NULL COMMENT '选择题选项，以JSON格式存储',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问题基本信息';

LOCK TABLES `t_question` WRITE;
/*!40000 ALTER TABLE `t_question` DISABLE KEYS */;

INSERT INTO `t_question` (`id`, `courseId`, `content`, `md5Value`, `level`, `answer`, `enable`, `optionJson`)
VALUES
	(32,9,'数据结构','b5f4c117fb4ebb941a0c4c1cd88c1c30',1,'1',1,'[{\"optionId\":1,\"content\":\"a\"},{\"optionId\":2,\"content\":\"b\"},{\"optionId\":3,\"content\":\"c\"}]'),
	(33,9,'操作系统','b5f4c117fb4ebb941a0c4c1cd88c1c30',2,'1,2',1,'[{\"optionId\":1,\"content\":\"a\"},{\"optionId\":2,\"content\":\"b\"},{\"optionId\":3,\"content\":\"c\"},{\"optionId\":4,\"content\":\"d\"},{\"optionId\":5,\"content\":\"e\"}]'),
	(34,9,'数据库','b5f4c117fb4ebb941a0c4c1cd88c1c30',3,'2',1,'[{\"optionId\":1,\"content\":\"a\"},{\"optionId\":2,\"content\":\"d\"},{\"optionId\":3,\"content\":\"c\"},{\"optionId\":4,\"content\":\"e\"}]');

/*!40000 ALTER TABLE `t_question` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_student
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_student`;

CREATE TABLE `t_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseId` int(11) NOT NULL COMMENT '课程ID',
  `email` varchar(20) NOT NULL COMMENT '考生邮箱',
  `name` varchar(15) NOT NULL COMMENT '考生姓名',
  `md5Value` varchar(100) NOT NULL COMMENT '考生名单文件md5校验值,用于防止文件重复上传',
  `class` int(11) NOT NULL COMMENT '考生班级, 可选值为1,2,3,4',
  `enable` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`,`courseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考生基本信息';

LOCK TABLES `t_student` WRITE;
/*!40000 ALTER TABLE `t_student` DISABLE KEYS */;

INSERT INTO `t_student` (`id`, `courseId`, `email`, `name`, `md5Value`, `class`, `enable`)
VALUES
	(1,9,'123@qq.com','test','f040a21fb789f603e422b2baf35a261a',1,1),
	(2,9,'456@qq.com','test1','f040a21fb789f603e422b2baf35a261a',2,1);

/*!40000 ALTER TABLE `t_student` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` tinyint(4) NOT NULL COMMENT '角色, 1 教师, 2 学生',
  `verified` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否激活, 0 未激活, 1 激活',
  `enable` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表，包括教师和学生';

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;

INSERT INTO `t_user` (`id`, `name`, `email`, `password`, `role`, `verified`, `enable`)
VALUES
	(6,'anson','1282976747@qq.com','9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08',1,1,1),
	(7,'student','1483809252@qq.com','9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08',2,1,1);

/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
