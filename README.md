# servlet单点登录和单点注销demo概要说明  
  - ### 1. 使用技术(依赖环境):maven,servlet,filter,jsp,tomcat,jdk1.8  
  - ### 2. 包括三个模块:两个client，一个server  
    ```
    sso-client和sso-client2是单点登录客户端,代码基本一致，**注释看clien1**，页面非常简陋  
    sso-server是单点登录服务端  
    client和server之间的通信用的是apache的HttpClient  
    为了图方便，服务端使用的tomcat的HttpSession作为会话对象，因为tomcat会自动帮我们写一个cookie即jssesionid回浏览器，  
    当然也可以自己用实现session机制(缓存，redis等)
    ```
## demo测试:  
  - ### 1. hosts文件修改(保证不同域名，这样cookie(这里指的jssessionid)不会覆盖，都是localhost会覆盖)  
    ```
    127.0.0.1 sso-client  
    127.0.0.1 sso-client2   
    127.0.0.1 sso-server
    ```
  - ### 2. 在ide使用tomcat运行项目(我使用的是idea，请读者自行配置),tomcat域名和端口可以自己配置   
      - **client1地址:**
      ```http://sso-client:8080/```
      - **client2地址:**
      ```http://sso-client2:8082/```
      - **sso地址:**
      ```http://sso-server:8081/```
  + ### 3. 在浏览器访问项目，注意访问路径 
    
    ![image](https://github.com/donglight/sso/wiki/client.jpg)
    ![image](https://github.com/donglight/sso/wiki/client2.jpg)
