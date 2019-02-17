package action.shop;

import com.google.gson.Gson;

import pojo.BasePojo;
import pojo.Shop;
import service.ShopService;
import service.impl.ShopServiceImpl;

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
 * @Description: 商家登录
 * @author yhfu
 * @date 2018年10月24日 下午4:16:09
 */
@WebServlet("/ShopLogin")
public class ShopLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ShopService shopService = new ShopServiceImpl();
	private Shop shop = new Shop();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter(); // 用PrintWriter对象将返回结果写入服务器

		// 查询条件
		String shop_account = request.getParameter("shop_account");
		String shop_password = request.getParameter("shop_password");
		String condition = " and shop_account='" + shop_account + "' and shop_password='" + shop_password+"'";
		HttpSession session = request.getSession(); // 获取session
		shop.setCondition(condition);
		//执行查询，返回结果
		List<Shop> shopList = new ArrayList<Shop>();
		BasePojo<Shop> basePojo = new BasePojo<Shop>();

		shopList = shopService.shopFind(shop);
		if (shopList != null && !shopList.isEmpty()) {
			session.setAttribute("shop_id", shopList.get(0).getShop_id());
			session.setAttribute("shop_name", shopList.get(0).getShop_name());
			System.out.println("ShopLogin 55："+session.getAttribute("shop_name"));
			basePojo.setSuccess(true);
			basePojo.setMsg("登录成功！");
			basePojo.setList(shopList);
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("账号或密码有误！");
		}

		out.print(new Gson().toJson(basePojo)); // 写入服务器
		out.flush();
		out.close();

	}

}
