# servlet单点登录demo说明  
  ### 1. 使用技术(依赖环境):maven,servlet,filter,jsp,tomcat,jdk1.8  
  ### 2. 包括三个模块:两个client，一个server  
    sso-client和sso-client2是单点登录客户端,页面非常简陋  
    sso-server是单点登录服务端  
    client和server之间的通信用的是apache的HttpClient  
    为了图方便，服务端使用的tomcat的HttpSession作为会话对象，因为tomcat会自动帮我们写一个cookie即jssesionid回浏览器，  
    当然也可以自己用实现session机制(缓存，redis等)  
# demo测试:  
  ### 1. hosts文件修改(保证不同域名，这样cookie(这里指的jssessionid)不会覆盖，都是localhost会覆盖)  
    127.0.0.1 sso-client  
    127.0.0.1 sso-client2   
    127.0.0.1 sso-server  
  ### 2. 在ide使用tomcat运行项目  
  ### 3. 在浏览器访问项目，注意访问路径
- 主列表1
- 主列表2
  1. 次列表1
  2. 次列表2
+ 主列表3
