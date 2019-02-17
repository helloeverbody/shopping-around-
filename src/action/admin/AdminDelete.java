package action.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import pojo.Admin;
import pojo.BasePojo;
import service.AdminService;
import service.impl.AdminServiceImpl;


/**
 * @Title: ShopDelete
 * @Description: 管理员删除
 * @author yhfu
 * @date 2018年10月24日 下午4:06:59
 */
@WebServlet("/AdminDelete")
public class AdminDelete extends HttpServlet {

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
		// 获取id
		int admi_id = Integer.parseInt(request.getParameter("admi_id"));

		admin.setAdmi_id(admi_id);

		// 执行删除，返回结果
		BasePojo<Admin> basePojo = new BasePojo<Admin>();

		if (adminService.adminDel(admin)) {
			basePojo.setSuccess(true);
			basePojo.setMsg("删除成功！");
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("删除失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
