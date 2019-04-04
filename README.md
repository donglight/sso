# sso
## servlet单点登录demo说明  
  使用技术(依赖环境):maven,servlet,jsp,tomcat,jdk1.8  
  包括三个模块:  
  sso-client和sso-client2是单点登录客户端,页面非常简陋  
  sso-server是单点登录服务端  
  client和server之间的通信用的是apache的HttpClient  
  为了图方便，服务端使用的tomcat的HttpSession作为会话对象，因为tomcat会自动帮我们写一个cookie即jssesionid回浏览器，  
  当然也可以自己用实现session机制(缓存，redis等)  
## demo测试:  

### hosts文件修改  
  127.0.0.1 sso-client  
  127.0.0.1 sso-client2   
  127.0.0.1 sso-server  
