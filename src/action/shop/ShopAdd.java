package action.shop;

import com.google.gson.Gson;

import pojo.Admin;
import pojo.BasePojo;
import service.AdminService;
import service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Title: ShopAdd
 * @Description: 管理员添加
 * @author yhfu
 * @date 2018年10月24日 下午3:54:26
 */
@WebServlet("/ShopAdd")
public class ShopAdd extends HttpServlet {

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
		// paras = URLDecoder.decode(paras); //解码url
		System.out.println(paras);
		if (paras != null) {
			admin = new Gson().fromJson(paras, Admin.class); // 解析Json获得对象
		}
		// 执行添加，返回结果
		int admi_id = adminService.adminAdd(admin); // 添加新用户，并得到id
		BasePojo<Admin> basePojo = new BasePojo<Admin>();

		if (admi_id > 0) {
			basePojo.setSuccess(true);
			basePojo.setMsg("添加成功！ 新用户id为" + admi_id);
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("注册失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器
		
		


	}

}
