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

@WebServlet(name = "LoginServlet",urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String returnUrl = request.getParameter("returnUrl");
        if("zdd".equals(username)){
            String token = UUID.randomUUID().toString();
            request.getSession().setAttribute("token",token);
            Map<String, Map<String, String>> tokenMap = TokenMap.getTokenMap();
            //jssessionid还没有，token校验的时候会put
            Map<String, String> registryMap = new ConcurrentHashMap<>();

            tokenMap.put(token,registryMap);
            response.sendRedirect(returnUrl+"?token="+token);
            return;
        }
        response.sendRedirect(returnUrl);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
