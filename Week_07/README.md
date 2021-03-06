#### Week_07
##### 周三
+ （必做）按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率
```
见测试包下的 SimpleDynamicDsTests：
1.原生JDBC：
  testRawJDBC()
2.MyBatisPlus
  testMybatisPlus()
  
速度排序（由快到慢）：
1.原生JDBC以 executeBatch() + clearBatch的方法批量插入；
2.原生JDBC以 循环execute()单条插入；
3.MybatisPlus saveBatch()；
4.MybatisPlus 循环save()单条插入。

结论：
1.JDBC的效率远高于MyBatisPlus框架，但非常的不方便；
2.就实际使用场景而言，订单都是单条插的，在无法使用batch的情况下
  ，原生JDBC与使用MybatisPlus差距也不大，可见框架还是香的；
3.把订单号的生成方式从UUID改为Snowflake实现，生成ID的同步方法耗费了更多时间
  ，可见ID预生成的重要性。   
```
+ 6.（选做）尝试自己做一个 ID 生成器（可以模拟 Seq 或 Snowflake）
```
见 /utils/IdGenerator
```
##### 周日
+ （必做）读写分离 - 动态切换数据源版本 1.0
```
其实就是丐版的dynamic-datasource-spring-starter
1.动态数据源见：DynamicDataSource和DynamicDataSourceContextHolder；
2.读写分离见：DynamicDataSourceInterceptor（判断是否是select，select没带主键条件走从库）；
3.周三的例子里都是写到主库（默认数据源是从库），testReadFromSlave()会自动从从库读取。
```
+ （必做）读写分离 - 数据库框架版本 2.0
```
新增：/autoconf/DynamicDataSourceAutoConfigure.java
@Configuration
@Import(value = {DataSourceConfig.class})
@EnableConfigurationProperties(value = {DynamicDataSourceProperties.class})
@ConditionalOnProperty(prefix = DynamicDataSourceProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class DynamicDataSourceAutoConfigure {
}

resource下新增 /META-INF/spring.factories
org.springframework.boot.autoconfigure.EnableAutoConfiguration=org.easley.dynamic.autoconf.DynamicDataSourceAutoConfigure

复制粘贴改了下之前写的starter的内容，差不多这么改就行了。
当然读写分离也有另一种做法，AOP拦截获取注解，优先级：方法注解 > 类注解，有时间再补充吧。
```
