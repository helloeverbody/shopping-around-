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
 * @Title: ShopUpdate
 * @Description: 管理员修改
 * @author yhfu
 * @date 2018年10月24日 下午4:22:24
 */
@WebServlet("/AdminUpdate")
public class AdminUpdate extends HttpServlet {

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
		// 获取参数
		String paras = request.getParameter("paras");
		// paras = URLDecoder.decode(paras); // 解码url
		if (paras != null) {
			admin = new Gson().fromJson(paras, Admin.class); // 解析Json获得对象
		}

		// 执行修改，返回结果
		BasePojo<Admin> basePojo = new BasePojo<Admin>();

		if (adminService.adminSave(admin)) {
			basePojo.setSuccess(true);
			basePojo.setMsg("修改成功");
			admin.setCondition(" and admi_id =" + admin.getAdmi_id());
			adminService.adminFind(admin); // 获取修改后的实体类
			List<Admin> adminList = new ArrayList<Admin>();
			adminList.add(admin);
			basePojo.setList(adminList);

		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("修改失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
