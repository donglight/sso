package sso.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "VerifyServlet", urlPatterns = "/verify")
public class VerifyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String token = request.getParameter("token");
        String jsessionid = request.getParameter("JSESSIONID");
        String returnUrl = request.getParameter("returnUrl");
        if(token == null){
            response.getWriter().write("false");
            return;
        }
        //token是否是登录时服务端生成的
        Map<String, Map<String, String>> tokenMap = TokenMap.getTokenMap();
        if (tokenMap.containsKey(token)) {
            Map<String, String> registryMap = tokenMap.get(token);
            if(registryMap == null){
                registryMap = new ConcurrentHashMap<>();

            }
            registryMap.put(returnUrl,jsessionid);
            response.getWriter().write("true");
            return;
        }
        response.getWriter().write("false");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
