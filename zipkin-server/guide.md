#使用
- 关于 Zipkin 的服务端，在使用 Spring Boot 2.x 版本后，官方就不推荐自行定制编译了，反而是直接提供了编译好的 jar 包来给我们使用，并且以前的@EnableZipkinServer也已经被打上了@Deprecated
- 下载地址：https://search.maven.org/remote_content?g=io.zipkin.java&a=zipkin-server&v=LATEST&c=exec
或者https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/
- 下载完jar包命令行运行（java -jar zipkin-server-2.11.7-exec.jar --logging.level.zipkin2=DEBUG --RABBIT_ADDRESSES=localhost:5672 --RABBIT_USER=guest --RABBIT_PASSWORD=guest --STORAGE_TYPE=mysql --MYSQL_DB=test --MYSQL_HOST=127.0.0.1 --MYSQL_TCP_PORT=3306 --MYSQL_USER=root --MYSQL_PASS=root
）
#lockback日志参数
"service": "${springAppName:-}",服务名称
"trace": "%X{X-B3-TraceId:-}",打印traceid
"span": "%X{X-B3-SpanId:-}",
"parent": "%X{X-B3-ParentSpanId:-}",

#环境变量信息

环境变量文档：https://github.com/openzipkin/zipkin/tree/master/zipkin-server#configuration-for-the-ui

###  UI环境变量


Property | Description | Description
--- | --- | ---
QUERY_PORT|监听http api和web ui的端口; 默认为9411
QUERY_ENABLED|false禁用查询api和UI， 默认为true
SEARCH_ENABLED|false禁用存储后端上的跟踪搜索请求。不禁用ID或依赖项查询的跟踪。使用其他服务（如日志）查找跟踪ID时禁用此功能; 默认为true
QUERY_LOG_LEVEL|写入控制台的日志级别; 默认为INFO
QUERY_LOOKBACK|从endTs回顾多少毫秒的查询; 默认为24小时
STORAGE_TYPE|SpanStore执行：一mem，mysql，cassandra，elasticsearch
COLLECTOR_SAMPLE_RATE|要保留的跟踪百分比，默认为始终采样（1.0）。


###  MySQL Storage环境变量

Property | Description | Description
--- | --- | ---
`MYSQL_DB`| 数据库名称. Defaults to "zipkin".
 `MYSQL_USER` and `MYSQL_PASS`:| MySQL authentication, which defaults to empty string.
 `MYSQL_HOST`:|Defaults to localhost
 `MYSQL_TCP_PORT`| Defaults to 3306
 `MYSQL_MAX_CONNECTIONS`| Maximum concurrent connections, defaults to 10
 `MYSQL_USE_SSL`|Requires `javax.net.ssl.trustStore` and `javax.net.ssl.trustStorePassword`, defaults to false
 `STORAGE_TYPE`|mysql




### RabbitMQ Collector环境变量

Property | Environment Variable | Description
--- | --- | ---
`zipkin.collector.rabbitmq.concurrency` | `RABBIT_CONCURRENCY` | 初始化几个消费者消费zipkin队列，默认为`1`
`zipkin.collector.rabbitmq.connection-timeout` | `RABBIT_CONNECTION_TIMEOUT` | Milliseconds to wait establishing a connection. Defaults to `60000` (1 minute)
`zipkin.collector.rabbitmq.queue` | `RABBIT_QUEUE` | 消费队列，默认 队列为`zipkin`
`zipkin.collector.rabbitmq.uri` | `RABBIT_URI` | [RabbitMQ URI spec](https://www.rabbitmq.com/uri-spec.html)-compliant URI, ex. `amqp://user:pass@host:10000/vhost`

如果设置RABBIT_URI发送span,以下配置将忽略

Property | Environment Variable | Description
--- | --- | ---
`zipkin.collector.rabbitmq.addresses` | `RABBIT_ADDRESSES` | Comma-separated list of RabbitMQ addresses, ex. `localhost:5672,localhost:5673`
`zipkin.collector.rabbitmq.password` | `RABBIT_PASSWORD`| Password to use when connecting to RabbitMQ. Defaults to `guest`
`zipkin.collector.rabbitmq.username` | `RABBIT_USER` | Username to use when connecting to RabbitMQ. Defaults to `guest`
`zipkin.collector.rabbitmq.virtual-host` | `RABBIT_VIRTUAL_HOST` | RabbitMQ virtual host to use. Defaults to `/`
`zipkin.collector.rabbitmq.use-ssl` | `RABBIT_USE_SSL` | Set to `true` to use SSL when connecting to RabbitMQ





