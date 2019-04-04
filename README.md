# servlet单点登录和单点注销demo概要说明  
  - ### 1. 使用技术(依赖环境):jdk1.8,maven,tomcat,servlet,filter,jsp
  - ### 2. 包括三个模块:两个client，一个server  
    ```
    sso-client和sso-client2是单点登录客户端,代码基本一致，注释看clien1，测试页面非常简单  
    sso-server是单点登录服务端  
    client和server之间的通信用的是apache的HttpClient  
    为了图方便，服务端使用的tomcat的HttpSession作为会话对象，因为tomcat会自动帮我们写一个cookie即jssesionid回浏览器，  
    当然也可以自己用实现session机制(缓存，redis等)
    ```
## demo测试:  
  - ### 1. hosts文件修改(保证不同域名，这样cookie(这里指的jssessionid)不会覆盖，都是localhost的话，三个模块的cookie会互相覆盖，看不到单点登录的效果)
    ```
    127.0.0.1 sso-client  
    127.0.0.1 sso-client2   
    127.0.0.1 sso-server
    ```
  - ### 2. 在ide使用tomcat运行项目(我使用的是idea，请读者自行配置),访问tomcat的域名和端口可以自己配置   
      - **client1地址:**
      ```http://sso-client:8080/```
      - **client2地址:**
      ```http://sso-client2:8082/```
      - **sso地址:**
      ```http://sso-server:8081/```
  + ### 3. 在浏览器访问项目，注意操作和访问路径 
    - 访问**sso-client:8080/index.jsp**,被filter拦截，发现没登录，跳转到sso-server的login.jsp页面
    ![image](https://github.com/donglight/sso/wiki/client.jpg)
    - 访问**sso-client2:8080/index.jsp**,被filter拦截，发现没登录，跳转到sso-server的login.jsp页面
    ![image](https://github.com/donglight/sso/wiki/client2.jpg)
    - 输入**zdd**，点击提交去sso-server的LoginServlet登录
    ![image](https://github.com/donglight/sso/wiki/zdd.jpg)
    - SSO校验用户名通过，在tomcat自动创建的会话中保存token，这个会话称作全局会话，在浏览器中会有一个该域名下的jsessionid标识这个session。
    回调returnUrl，客户端Filter拦截到此请求发现token不为空，于是带着token(或ticket)去sso校验，token有效则创建**局部会话**,且放行，访问到的对应的页面
    ![image](https://github.com/donglight/sso/wiki/login.jpg)
    ![image](https://github.com/donglight/sso/wiki/login2.jpg)
