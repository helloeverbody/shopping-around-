package action.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import service.impl.AdminServiceImpl;
import service.AdminService;
import pojo.Admin;
import pojo.BasePojo;;

/**
 * @Title: ShopRegist
 * @Description: 管理员注册
 * @author yhfu
 * @date 2018年10月24日 下午4:18:59
 */
@WebServlet("/AdminRegist")
public class AdminRegist extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private AdminService AdminService = new AdminServiceImpl();
	private Admin Admin = new Admin();
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter(); // 用PrintWriter对象将返回结果写入服务器
		// 获取参数
		String admi_account = request.getParameter("admi_account");
		String admi_password = request.getParameter("admi_password");

		Admin.setAdmi_account(admi_account);
		Admin.setAdmi_password(admi_password);
		// 执行添加，返回结果
		int admi_id = AdminService.adminAdd(Admin); // 添加新用户，并得到id

		BasePojo<Admin> basePojo = new BasePojo<Admin>();

		if (admi_id > 0) {
			basePojo.setSuccess(true);
			basePojo.setMsg("注册成功！ 新管理员id为" + admi_id);
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("注册失败！");
		}

		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
