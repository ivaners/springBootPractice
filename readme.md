## 项目简介

基于spring boot 框架的项目实践

## 主要功能

    1. 数据库：Druid数据库连接池，监控数据库访问性能，统计SQL的执行性能。
    2. 持久层：mybatis持久化，使用MyBatis-Plus优化，减少sql开发量；
    3. MVC： 基于spring mvc注解,Rest风格Controller。
    4. 日志：log4j2打印日志，业务日志和调试日志分开打印。同时基于时间和文件大小分割日志文件。

## 提供demo

1. 使用Shiro管理安全验证，支持多数据源管理。数据传输动态秘钥加密,`jwt`过期刷新,用户操作监控等 加固应用安全。
2. 订单秒杀实践.

## 技术选型
    ● 核心框架：Spring boot
    ● 安全框架：Apache Shiro
    ● 持久层框架：MyBatis + MyBatis-Plus
    ● 数据库连接池：Alibaba Druid
    ● 缓存框架：Redis
    ● 会话管理：Spring-Shiro + redis
    ● 日志管理：SLF4J、Log4j2