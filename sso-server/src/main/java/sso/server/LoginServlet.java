package sso.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SSO登录Servlet
 * @author donglight
 */
@WebServlet(name = "LoginServlet",urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String returnUrl = request.getParameter("returnUrl");
        //登录逻辑
        //这里应该是访问数据库，为了方便，直接硬编码
        if("zdd".equals(username)){
            //token或ticket用uuid
            String token = UUID.randomUUID().toString();

            //把token放到session中，证明该用户已经在当前访问的系统登录了SSO，
            // 那么登录其他子系统的时候也无需登录，校验逻辑请看LoginFilter
            request.getSession().setAttribute("token",token);

            //保存token到map中，map作用请参考TokenMap的注释
            Map<String, Map<String, String>> tokenMap = TokenMap.getTokenMap();
            //jsessionid还没有，token校验的时候会put
            Map<String, String> registryMap = new ConcurrentHashMap<>();
            tokenMap.put(token,registryMap);
            //重定向到回调地址
            response.sendRedirect(returnUrl+"?token="+token);
            return;
        }
        response.sendRedirect(returnUrl);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
