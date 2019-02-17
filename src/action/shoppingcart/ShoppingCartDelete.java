package action.shoppingcart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import pojo.BasePojo;
import pojo.ShoppingCart;
import service.ShoppingCartService;
import service.impl.ShoppingCartServiceImpl;

/**
 * @Title: ShoppingCartDelete
 * @Description: 购物车删除
 * @author yhfu
 * @date 2018年10月23日 下午3:28:11
 */
@WebServlet("/ShoppingCartDelete")
public class ShoppingCartDelete extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
	private ShoppingCart shoppingCart = new ShoppingCart();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter(); // 用PrintWriter对象将返回结果写入服务器
		// 获取id
		int cart_id = Integer.parseInt(request.getParameter("cart_id"));

		shoppingCart.setCart_id(cart_id);

		// 执行删除，返回结果
		BasePojo<ShoppingCart> basePojo = new BasePojo<ShoppingCart>();

		if (shoppingCartService.shoppingCartDel(shoppingCart)) {
			basePojo.setSuccess(true);
			basePojo.setMsg("删除成功！");
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("删除失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
