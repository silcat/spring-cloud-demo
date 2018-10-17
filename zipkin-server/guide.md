#使用
- 关于 Zipkin 的服务端，在使用 Spring Boot 2.x 版本后，官方就不推荐自行定制编译了，反而是直接提供了编译好的 jar 包来给我们使用，并且以前的@EnableZipkinServer也已经被打上了@Deprecated
- 下载地址：https://search.maven.org/remote_content?g=io.zipkin.java&a=zipkin-server&v=LATEST&c=exec
或者https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/
- 下载完jar包命令行运行（java -jar zipkin-server-2.11.7-exec.jar --logging.level.zipkin2=DEBUG --RABBIT_ADDRESSES=localhost:5672 --RABBIT_USER=guest --RABBIT_PASSWORD=guest --STORAGE_TYPE=mysql --MYSQL_DB=test --MYSQL_HOST=127.0.0.1 --MYSQL_TCP_PORT=3306 --MYSQL_USER=root --MYSQL_PASS=root
）
#环境变量信息
- 环境变量：https://github.com/openzipkin/zipkin/tree/master/zipkin-server#configuration-for-the-ui

- ui配置：
QUERY_PORT：监听http api和web ui的端口; 默认为9411
QUERY_ENABLED：false禁用查询api和UI资产。如果不需要，也可以对存储后端禁用搜索; 默认为true
SEARCH_ENABLED：false禁用存储后端上的跟踪搜索请求。不禁用ID或依赖项查询的跟踪。使用其他服务（如日志）查找跟踪ID时禁用此功能; 默认为true
QUERY_LOG_LEVEL：写入控制台的日志级别; 默认为INFO
QUERY_LOOKBACK：从endTs回顾多少毫秒的查询; 默认为24小时（每天两个桶：今天一个，昨天一个）
STORAGE_TYPE：SpanStore执行：一mem，mysql，cassandra，elasticsearch
COLLECTOR_SAMPLE_RATE：要保留的跟踪百分比，默认为始终采样（1.0）。

- MySQL Storage
*`MYSQL_DB`: The database to use. Defaults to "zipkin".
 `MYSQL_USER` and `MYSQL_PASS`: MySQL authentication, which defaults to empty string.
 `MYSQL_HOST`: Defaults to localhost
 `MYSQL_TCP_PORT`: Defaults to 3306
 `MYSQL_MAX_CONNECTIONS`: Maximum concurrent connections, defaults to 10
 `MYSQL_USE_SSL`: Requires `javax.net.ssl.trustStore` and `javax.net.ssl.trustStorePassword`, defaults to false
 `STORAGE_TYPE`:mysql

- rabbitMq
RABBIT_ADDRESSES=localhost:5672 --RABBIT_USER=guest --RABBIT_PASSWORD=guest 