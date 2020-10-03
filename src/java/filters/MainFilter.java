package filters;

import utils.SystemLib;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainFilter implements Filter {
    private static final Set<String> UNAUTHORIZED_URL = new HashSet<String>(
            Arrays.asList("login", "home.jsp", "index.jsp"));
    private static final Set<String> AUTHORIZED_URL = new HashSet<String>(
            Arrays.asList("home.jsp", "profile.jsp", "logout", "post", "cmt", "detail", "delete", "emo", "searching"));
    private static final String HOME = "home.jsp";
    private static final String INDEX = "index.jsp";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        boolean is_login = req.getSession().getAttribute("is_login") != null;
        String target_url = req.getRequestURI().substring(req.getContextPath().length() + 1);
        String url = null;
        SystemLib.updateNotification((HttpServletRequest) servletRequest);
        if (is_login & !AUTHORIZED_URL.contains(target_url)){
            url = HOME;
        }
        if (!is_login & !UNAUTHORIZED_URL.contains(target_url)){
            url = INDEX;
        }
        if (url == null){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            resp.sendRedirect(url);
        }
    }

    @Override
    public void destroy() {

    }
}
