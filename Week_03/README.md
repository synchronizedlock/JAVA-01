#### Nami网关
##### how to use nami
1. 在 localhost mysql 执行 nami.sql
2. 本地启动 nacos
```
sh startup.sh -m standalone
```
3. 启动 nami-admin
```
后台地址：http://localhost:9001/user/login/page
admin/1234
```
4. 启动两个 nami-example 实例
+ 实例1（模拟灰度机）：
```
nami:
  http:
    app-name: nami
    version: gray_1.0
    context-path: /nami
    port: 8081
    admin-url: 127.0.0.1:9001
 
server:
  port: 8081
 
nacos:
  discovery:
    server-addr: 127.0.0.1:8848
```
+ 实例2（模拟生产机）
```
ship:
  http:
    app-name: nami
    version: prod_1.0
    context-path: /nami
    port: 8082
    admin-url: 127.0.0.1:9001
 
server:
  port: 8082
 
nacos:
  discovery:
    server-addr: 127.0.0.1:8848
```
5. 在 nami-admin 后台添加路由规则配置
   ![route_rule.png](nami/tutorial_images/route_rule.png)
6. 启动 nami-server
7. 配置 postman 请求测试路由
```
请求 url：http://localhost:9000/nami/user/add
header 添加：env=grey
body：
{
    "id": 1,
    "name": "nami"
}
正常会返回body的内容
```
