package action.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import pojo.Admin;
import pojo.BasePojo;
import service.AdminService;
import service.impl.AdminServiceImpl;

/**
 * @Title: ShopLogin
 * @Description: 管理员登录
 * @author yhfu
 * @date 2018年10月24日 下午4:16:09
 */
@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {

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

		// 查询条件
		String admi_account = request.getParameter("admi_account");
		String admi_password = request.getParameter("admi_password");
		String condition = "and admi_account='" + admi_account + "' and admi_password='" + admi_password+"'";
		HttpSession session = request.getSession(); // 获取session
		if(session==null) {
			System.out.println("session为空");
		}
		admin.setCondition(condition);
		//执行查询，返回结果
		List<Admin> adminList = new ArrayList<Admin>();
		BasePojo<Admin> basePojo = new BasePojo<Admin>();

		adminList = adminService.adminFind(admin);
		if (adminList != null && !adminList.isEmpty()) {
			Gson gson = new Gson();
			String json = gson.toJson(adminList);
			System.out.println(json);
			session.setAttribute("admi_id", adminList.get(0).getAdmi_id());
			session.setAttribute("admi_name", adminList.get(0).getAdmi_name());
			System.out.println(session.getAttribute("admi_name"));
			basePojo.setSuccess(true);
			basePojo.setMsg("登录成功！");
			basePojo.setList(adminList);
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("账号或密码有误！");
		}
		System.out.println("我又登录了");
		out.print(new Gson().toJson(basePojo)); // 写入服务器
		out.flush();
		out.close();

	}

}
