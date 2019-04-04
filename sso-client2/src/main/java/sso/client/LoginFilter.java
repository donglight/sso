package sso.client;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 〈一句话功能简述〉<br>
 * 客户端登录拦截器
 *
 * @author donglight
 * @date 2019/4/2
 * @since 1.0.0
 */
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();


        if (req.getRequestURL().toString().contains("logout")) {
            chain.doFilter(request, response);
            return;
        }

        if (session.getAttribute("isLogin") != null) {
            if ((boolean) session.getAttribute("isLogin")) {
                chain.doFilter(request, response);
                return;
            }
        }



        String token = req.getParameter("token");
        if (token != null) {
            boolean verifyResult = this.verify("http://sso-server:8081/verify?returnUrl=http://sso-client2:8082/&token=" + token + "&JSESSIONID=" + session.getId());
            if (verifyResult) {
                session.setAttribute("isLogin", true);
                resp.sendRedirect(req.getRequestURL().toString());
                return;
            }
        }

        resp.sendRedirect("http://sso-server:8081/login.jsp?returnUrl=" + req.getRequestURL());
    }

    private boolean verify(String verifyUrl) {

        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建Post请求
        HttpPost httpPost = new HttpPost(verifyUrl);

        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        //httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            /*System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }*/
            String s = EntityUtils.toString(responseEntity);
            return Boolean.valueOf(s);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
