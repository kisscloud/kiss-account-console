# 统一账户中心控制台

## 1. 程序运行

```
$ git clone https://github.com/kisscloud/kiss-account-console.git
$ cd kiss-account-console
$ mvn install -Dmaven.test.skip=true
$ mvn package -Dmaven.test.skip=true
$ java -jar -Dspring.config.location=/opt/configs/kiss-account-console/application.yml /opt/apps/kiss-account-console/target/kiss-account-console-0.0.1-SNAPSHOT.jar

```

## 2. 配置文件

编辑配置文件：

```
$ vim /opt/configs/kiss-account-console/application.yml
```

配置文件内容：

```
spring:
  application:
    name: kiss-account-console
server:
  port: 8021
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
ribbon:
  ConnectTimeout: 60000
  ReadTimeout: 60000
```

