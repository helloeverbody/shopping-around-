package action.admin;

import com.google.gson.Gson;

import pojo.Admin;
import service.AdminService;
import service.impl.AdminServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ShopLogin
 * @Description: 管理员退出登录
 * @author yhfu
 * @date 2018年10月24日 下午4:16:09
 */
@WebServlet("/AdminLogout")
public class AdminLogout extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private AdminService adminService = new AdminServiceImpl();
	private Admin admin = new Admin();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter(); // 用PrintWriter对象将返回结果写入服务器

		HttpSession session = request.getSession(); // 获取session
		//移除session
		session.invalidate();

	}

}
