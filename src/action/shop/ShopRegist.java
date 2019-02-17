package action.shop;

import com.google.gson.Gson;

import pojo.BasePojo;
import pojo.Shop;
import service.impl.ShopServiceImpl;
import service.ShopService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @Title: ShopRegist
 * @Description: 管理员注册
 * @author yhfu
 * @date 2018年10月24日 下午4:18:59
 */
@WebServlet("/ShopRegist")
public class ShopRegist extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ShopService ShopService = new ShopServiceImpl();
	private Shop shop = new Shop();
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter(); // 用PrintWriter对象将返回结果写入服务器
		// 获取参数
		String shop_account = request.getParameter("shop_account");
		String shop_password = request.getParameter("shop_password");

		shop.setShop_account(shop_account);
		shop.setShop_password(shop_password);
		// 执行添加，返回结果
		int admi_id = ShopService.shopAdd(shop); // 添加新用户，并得到id

		BasePojo<Shop> basePojo = new BasePojo<Shop>();

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
