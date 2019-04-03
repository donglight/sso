package sso.server;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String token = (String) request.getSession().getAttribute("token");

        String returnUrl = request.getParameter("returnUrl");
        Map<String, Map<String, String>> tokenMap = TokenMap.getTokenMap();
        Map<String, String> addressMap = null;
        if (token != null) {
            addressMap = tokenMap.remove(token);
        }
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = null;
        HttpPost httpPost;
        try {
            if (addressMap != null) {
                httpClient = HttpClientBuilder.create().build();
                for (Map.Entry<String, String> entry : addressMap.entrySet()) {
                    httpPost = new HttpPost(entry.getKey() + "logout?");
                    httpPost.setHeader("Cookie", "JSESSIONID=" + entry.getValue());
                    httpClient.execute(httpPost);
                }

            }
        } catch (ClientProtocolException e) {
            System.err.println("Http协议出现问题");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("解析错误");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO异常");
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        request.getSession().invalidate();
        response.sendRedirect("http://sso-server:8081/login.jsp?returnUrl="+returnUrl);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
