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

import com.google.gson.Gson;

import pojo.Admin;
import pojo.BasePojo;
import service.AdminService;
import service.impl.AdminServiceImpl;


/**
 * @Title: ShopFind
 * @Description: 管理员查询
 * @author yhfu
 * @date 2018年10月24日 下午4:08:39
 */
@WebServlet("/AdminFind")
public class AdminFind extends HttpServlet {

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
		String condition = request.getParameter("condition"); //condition 示例为：and + 查询条件

		System.out.println("condition: " + condition);
		admin.setCondition(condition);
		// 执行查询，返回结果
		List<Admin> adminList = new ArrayList<Admin>();
		BasePojo<Admin> basePojo = new BasePojo<Admin>();

		adminList = adminService.adminFind(admin);
		if (adminList != null && !adminList.isEmpty()) {
			basePojo.setSuccess(true);
			basePojo.setMsg("查找成功！");
			basePojo.setList(adminList);

		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("查找失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
