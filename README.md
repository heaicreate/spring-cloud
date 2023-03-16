# spring-cloud
spring-cloud组件工程
nacos部分
启动 sh startup.sh -m standalone 已单机模式启动
http://192.168.0.100:8848/nacos/index.html 访问地址
通过log 日志查看后台访问地址--启动后有可能会变
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

注册过程 引用的client 内部封装了nacos的api进行url 参数等拼装
通过事件 订阅模式进行服务上线 下线通知 服务本地缓存有两种模式 
1:本地文件 2:内存模式
通过namingLoadCacheAtStart 参数设置 默认false
事件相关在DefaultPublisher 类中可查看

单机无法启动永久实例(需要集群模式)
节点实例分为 永久/临时
这样可进行流量保证 永久节点 当健康检测不通过时会设置为不健康 还有概率流量 而临时实例 当健康检测不通过 会直接进行下线处理
临时节点过多的情况下 有可能超过健康阀值 引起健康实例的崩溃

nacos 配置原理
1.x版本 2.0版本开始使用RPC长连接协议
建立两个周期线程池
1个用来默认10ms更新一次配置
1个用来建立长轮询--每3000个key多建立一个线程
内部封装了http请求 去请求api 获取数据返回更新
动态刷新 内部基于事件通知
内容是否一致判断 是通过加密成md5进行判断 如果不一致则在cacheData中发出一个changeEvent事件通知

注册过程中 会去判断节点是否是永久节点 临时节点会开始定时任务去进行心跳检测
C:一致性
A:可用性
P:分区容错性
nacos 默认是AP 模式 可切换成CP 模式
CP模式下 可设置永久节点

feign原理与配置
feign 本质是封装的http请求 异步线程下会有请求头丢失情况出现
解决方案1:重新feign 拦截器 重写apply方法 设置请求头传递
解决方案2:InheritableThreadLocal 设置属性针对子线程可见



elk日志搭建
es kibana 进入目录下直接启动
logstash 进入目录下 bin下使用 ./logstash -f 配置文件(test.conf)

logstash 启动依赖jdk 环境 需要在启动脚本中增加jdk配置
rabbitMq 实现延迟队列有两种方式
1:使用死信队列 TTL和DLX--在正常队列中设置ttl过期时间,到时间转入绑定的死信队列中进行消费
2:使用延迟插件--声明了一种新的交换机类型

lsof -i tcp:7300 mac 查看端口占用pid

Sentinel相关(单机)
https://github.com/alibaba/spring-cloud-alibaba/wiki/Sentinel
启动:java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar
访问地址:http://localhost:8080/#/dashboard/flow/spring-cloud
sentinel进行懒加载方式,资源访问后才会在控制台出现,进行指定资源的设置;sentinel也提供了异常固定返回,在限流等报错情况下可进行自定义错误返回