# spring-cloud
spring-cloud组件工程
nacos部分
启动 sh startup.sh -m standalone 已单机模式启动
http://192.168.0.100:8848/nacos/index.html 访问地址
账号密码 nacos
spring-boot 集成nacos注意点: 非public空间的需要指定namespace
使用：http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=spring-cloud&group=DEFAULT_GROUP 接口进行配置验证
从项目启动过程中 读取日志查看哪些配置项是否配置好
