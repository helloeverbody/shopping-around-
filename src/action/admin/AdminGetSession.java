package action.admin;

import com.google.gson.Gson;

import pojo.Admin;
import pojo.BasePojo;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Title: ShopGetSession
 * @Description: 获取管理员Session判断是否登录
 * @author yhfu
 * @date 2018年10月25日 下午12:37:33
 */
@WebServlet("/AdminGetSession")
public class AdminGetSession extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter(); // 用PrintWriter对象将返回结果写入服务器
		System.out.println("获取session！！");
		// 查询条件
		HttpSession session = request.getSession(false); // 获取session
		BasePojo<Admin> basePojo = new BasePojo<>();
		Admin admin = new Admin();
		List<Admin> list = new ArrayList<>();
		if(session.getAttribute("anmi_name") == null) {
			System.out.println("session: 为空");
		}
		if (session != null && session.getAttribute("admi_id") != null) {
			admin.setAdmi_name(session.getAttribute("admi_name").toString());
			list.add(admin);
			basePojo.setMsg("管理员已登录");
			basePojo.setSuccess(true);
			basePojo.setList(list);
			System.out.println("session: " + session.getAttribute("admi_name"));
		}else{
			basePojo.setMsg("管理员未登录");
			basePojo.setSuccess(false);
			System.out.println("未登录");
		}

		out.print(new Gson().toJson(basePojo)); // 写入服务器
		out.flush();
		out.close();
	}

}
