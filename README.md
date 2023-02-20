# spring-cloud
spring-cloud组件工程
nacos部分
启动 sh startup.sh -m standalone 已单机模式启动
http://192.168.0.100:8848/nacos/index.html 访问地址
账号密码 nacos
spring-boot 集成nacos注意点: 非public空间的需要指定namespace
使用：http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=spring-cloud&group=DEFAULT_GROUP 接口进行配置验证
http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=spring-cloud-test.yml&group=DEFAULT_GROUP&tenant=5e1b94a2-2fb4-425e-8058-27341eddf452
从项目启动过程中 读取日志查看哪些配置项是否配置好

注意版本问题 vpn 之间调用
loadbalancer 负载均衡
ReactorServiceInstanceLoadBalancer 实现了轮询 和随机两种负载均衡策略
轮询 通过一个自增数与存活服务进行取余获取
轮询则是通过ThreadLocalRandom获取在服务中的随机数
