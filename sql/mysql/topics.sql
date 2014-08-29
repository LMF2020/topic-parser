
/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`topics` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `topics`;

/*Table structure for table `t_topic` */

DROP TABLE IF EXISTS `t_topic`;

CREATE TABLE `t_topic` (
  `doc_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文档标识符(自增序列)',
  `catalog` char(1) NOT NULL COMMENT '题型(枚举型)',
  `content` text NOT NULL COMMENT '题目内容(长文本)',
  `answer` text COMMENT '题目答案(长文本)',
  `score` float NOT NULL COMMENT '题目分数(浮点型)',
  `img_url` text COMMENT '题目图片URL(长文本)',
  `user_id` int(11) DEFAULT NULL COMMENT '上传者ID',
  `hours` int(11) DEFAULT NULL COMMENT '课时',
  `class` int(11) DEFAULT NULL COMMENT '年级',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `course` smallint(1) NOT NULL COMMENT '科目(数字型)',
  PRIMARY KEY (`doc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_topic` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
