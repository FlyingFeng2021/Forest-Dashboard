# Forest-Dashboard-New
***
#### ·项目简介
**效果**  
![](./preview.png)  
**使用教程：**
```
https://www.bilibili.com/video/BV1jW4y1o75w
```
**作用**：使用官方API接口，快速地将您所有的Forest专注数据可视化，解决了APP中只能统计某一年数据的问题。  
**PS**：本项目是我第一个springboot项目的改进版，代码上存在着诸多问题，还请各位大佬们多多指教。
#### ·技术栈
前端：Vue3+ECharts+axios+element-plus    
后端：Springboot+Mybatis+MYSQL+Tomcat
***
#### ·使用指南
在正式运行jar包前，需要准备好数据库：  
**创建数据库表结构（存储解析的数据）**
```
DROP TABLE IF EXISTS `tree`;
CREATE TABLE `tree`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `start_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `end_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tag` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `time` int UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1729 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
```  

**接下来你就可以启动应用了~**  
*注意：打包好的项目文件中已指定数据库端口等信息，请前往源代码查看。此外，服务器将占用默认端口8080，您可以使用下方url来访问服务：*
```
localhost:8080
```
*注意：如需切换账号，请重启服务。*

    