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
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Title: ShopDelete
 * @Description: 商家删除
 * @author yhfu
 * @date 2018年10月24日 下午4:06:59
 */
@WebServlet("/ShopDelete")
public class ShopDelete extends HttpServlet {

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
		// 获取id
		int shop_id = Integer.parseInt(request.getParameter("shop_id"));

		shop.setShop_id(shop_id);

		// 执行删除，返回结果
		BasePojo<Shop> basePojo = new BasePojo<Shop>();

		if (shopService.shopDel(shop)) {
			basePojo.setSuccess(true);
			basePojo.setMsg("删除成功！");
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("删除失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
