#### Week05
##### 周三作业
+ homework-01
```
1.(选做)使 Java 里的动态代理，实现一个简单的 AOP。
  /src/test/java/
  JdkProxyTest
  CglibProxyTest
2.(必做)写代码实现 Spring Bean 的装配，方式越多越好(XML、Annotation 都可以)
  /src/main/resources/applicationContext.xml
  /src/main/java/org/easley/spring/config/BeanConfig
3.(选做)实现一个 Spring XML 自定义配置，配置一组 Bean
  参见2及，/src/test/java/SpringBeanWireTest     
```
##### 周日作业
+ homework-02
```
3.(必做)实现自动配置和Starter
  参见 datasource-autoconf-spring-boot-starter、autoconf-demo
  运行 autoconf-demo的DemoBootstrap即可
6.(必做)研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法
  - 使用 JDBC 原生接口，实现数据库的增删改查操作
  - 使用事务，PrepareStatement 方式，批处理方式，改进上述操作
  - 配置 Hikari 连接池，改进上述操作 
  参见 spring-boot-jdbc-demo，测试用例：JdbcTest（使用了线上数据库，可以直接运行）
```
##### 超级挑战
+ 尝试使用ByteBuddy与Instrument实现一个简单JavaAgent实现无侵入下的AOP
```
代理工程：method-profiler-agent
测试工程：method-profiler-agent-demo

IDEA代理配置，目录请自行替换为本地编译的method-profiler-agent-1.0.jar位置
IDEA VM OPTIONS:
-javaagent:/Users/easley_ray/my_repo/JAVA-01/Week_05/method-profiler-agent/target/method-profiler-agent-1.0.jar

控制台运行结果输出：
>>> Method profiler agent start.
>>> ----------------------------
唱
>>> private void org.easley.demo.Kunkun.sing() throws java.lang.InterruptedException: took 10ms
跳
>>> private void org.easley.demo.Kunkun.jump() throws java.lang.InterruptedException: took 24ms
rap
>>> private void org.easley.demo.Kunkun.rap() throws java.lang.InterruptedException: took 33ms
篮球
>>> private void org.easley.demo.Kunkun.basketball() throws java.lang.InterruptedException: took 75ms
>>> public static void org.easley.demo.Kunkun.main(java.lang.String[]) throws java.lang.InterruptedException: took 151ms
```
