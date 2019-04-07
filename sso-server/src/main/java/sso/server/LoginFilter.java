package sso.server;

import org.apache.http.client.utils.URIBuilder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 登录拦截器
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
        HttpSession session = req.getSession(false);
        String returnUrl = req.getParameter("returnUrl");
        String token = req.getParameter("token");

        String url = req.getRequestURL().toString();

        //去注销
        if (url.contains("logout")) {
            chain.doFilter(request, response);
            return;
        }
        //判断用户浏览器是否已经登陆过sso
        //如果已经登陆过就不需要重新登录，重定向到returnUrl
        if(session != null){
            String sessionToken = (String) session.getAttribute("token");
            if (sessionToken != null) {
                if (returnUrl != null) {
                    Map<String, String> registryMap = TokenMap.getTokenMap().get(sessionToken);
                    URIBuilder uri;
                    try {
                        uri = new URIBuilder(returnUrl);
                        //处理token
                        if (!registryMap.containsKey(uri.getScheme()+"://"+uri.getHost()+":"+uri.getPort()+"/")) {
                            resp.sendRedirect(returnUrl + "?token=" + sessionToken);
                        } else {
                            resp.sendRedirect(returnUrl);
                        }
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } finally {
                        uri = null;
                    }

                    return;
                }
                chain.doFilter(request, response);
                return;
            }
        }


        if (url.contains("login")) {
            //如果是去登录servlet，放行
            chain.doFilter(request, response);
            return;
        }
        if (token != null) {
            //去VerifyServlet校验token，放行
            chain.doFilter(request, response);
            return;
        }

        resp.sendRedirect("http://sso-server:8081/login.jsp");
    }

    @Override
    public void destroy() {

    }
}
