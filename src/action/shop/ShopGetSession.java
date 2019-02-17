package action.shop;

import com.google.gson.Gson;

import pojo.BasePojo;
import pojo.Shop;

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
 * @Title: ShopGetSession
 * @Description: 获取商家Session判断是否登录
 * @author yhfu
 * @date 2018年10月25日 下午12:37:33
 */
@WebServlet("/ShopGetSession")
public class ShopGetSession extends HttpServlet {

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
		BasePojo<Shop> basePojo = new BasePojo<>();
		Shop shop = new Shop();
		List<Shop> list = new ArrayList<>();
		if (session != null && session.getAttribute("shop_id") != null) {
			shop.setShop_id(Integer.getInteger(session.getAttribute("shop_id").toString()));
			shop.setShop_name(session.getAttribute("shop_id").toString());
			list.add(shop);
			basePojo.setMsg("商家已登录");
			basePojo.setSuccess(true);
			basePojo.setList(list);
			System.out.println("session: " + session.getAttribute("shop_name"));
		}else{
			basePojo.setMsg("商家未登录");
			basePojo.setSuccess(false);
		}

		out.print(new Gson().toJson(basePojo)); // 写入服务器
		out.flush();
		out.close();
	}

}
