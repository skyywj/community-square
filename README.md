## 技术栈

- Spring-Boot
- Shiro
- MyBatis-Plus
- Bootstrap
- MySQL

## 数据库初始化
1、创建名为community-square的数据库

2、修改 application.yml 里的数据库连接配置

3、修改community-square.sql相关信息

（1）修改redis连接信息：本地host：127.0.0.1，密码建议自己给redis配上

（2）修改文件上传位置信息

（3）修改其他配置信息

4、在community-square数据库执行sql脚本，创建数据表

## 关于头像相关逻辑

### 头像逻辑主要有两种处理
- 一种是将图片直接存储到数据库中，这样读取慢，对数据库压力大，但是数据迁移容易，直接拷贝数据库就OK了。
- 另一个是将图片存储到服务器，数据库中只存储图片地址，这样更加灵活，数据库压力小

本系统采用的是第二种方式，[nginx配置本地图片服务器](http://www.carryjey.club/article/16)

对于上线业务，最好的方式就是将静态资源存储到oss上，利用CDN进行加速。

## [关于接口限频的说明](/information/rate_limit.md)


