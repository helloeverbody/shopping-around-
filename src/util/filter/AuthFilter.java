package util.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author yhfu
 * @Title: AuthFilter
 * @Description: 访问权限控制
 * @date 2018年10月24日 下午10:29:03
 */
public class AuthFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String currentURL = request.getRequestURI();
        // 取得根目录所对应的绝对路径:
        String targetURL = currentURL.substring(currentURL.indexOf("/", 1), currentURL.length());
        // 截取到当前文件名用于比较
        HttpSession session = request.getSession(false);


        System.out.println("进入过滤器");
        if (currentURL.contains(".html") && !currentURL.equals("/xgg/admin/login.html")) {// 判断当前页是否是重定向以后的登录页面页面，如果是就不做session的判断，防止出现死循环
            if (session == null || session.getAttribute("admi_name") == null) {
                // *用户登录以后需手动添加session
                System.out.println("跳转到登录页面！");
                response.sendRedirect(request.getContextPath() + "/admin/login.html");
                return;
            }
        }
        // 加入filter链继续向下执行
        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁！");
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO 自动生成的方法存根
        System.out.println("过滤器初始化！");
    }

}
