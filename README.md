## 技术栈

- Spring-Boot
- Shiro
- MyBatis-Plus
- Bootstrap
- MySQL
- redis
- fremwork(轻量级前端框架)

## 数据库初始化
1、创建名为community-square的数据库

2、修改 application.yml 里的数据库连接配置

3、修改community-square.sql相关信息

（1）修改redis连接信息：本地host：127.0.0.1，密码建议自己给redis配上

（2）修改文件上传位置信息

（3）修改其他配置信息

4、在community-square数据库执行sql脚本，创建数据表

5、配置nginx图片服务器（本地nginx root 路径对应到文件上传路径）

## 关于头像相关逻辑
git 
### 头像逻辑主要有两种处理
- 一种是将图片直接存储到数据库中，这样读取慢，对数据库压力大，但是数据迁移容易，直接拷贝数据库就OK了。
- 另一个是将图片存储到服务器，数据库中只存储图片地址，这样更加灵活，数据库压力小

本系统采用的是第二种方式，[nginx配置本地图片服务器](http://www.carryjey.club/article/16)

对于上线业务，最好的方式就是将静态资源存储到oss上，利用CDN进行加速。

## [关于接口限频的说明](/information/rate_limit.md)

## 邮箱配置使用
配置发送邮箱，建议使用163邮箱作为主体邮箱，需要去申请授权码

见地址：https://github.com/skyywj/MessageService/blob/master/Introduce/mail.md

配置数据库

       mail.username   //邮箱地址
       mail.auth.code   //授权码
       mail.host        //邮箱的smtp服务器地址，163邮箱为：smtp.163.com



