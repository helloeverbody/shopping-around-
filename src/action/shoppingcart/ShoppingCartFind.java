package action.shoppingcart;

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

import pojo.BasePojo;
import pojo.ShoppingCart;
import service.ShoppingCartService;
import service.impl.ShoppingCartServiceImpl;


/**
 * @Title: ShoppingCartFind
 * @Description: 购物车查询
 * @author yhfu
 * @date 2018年10月23日 下午3:31:36
 */
@WebServlet("/ShoppingCartFind")
public class ShoppingCartFind extends HttpServlet {

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
		// 查询条件
		String condition = request.getParameter("condition"); //condition 示例为：and + 查询条件

		System.out.println("condition: " + condition);
		shoppingCart.setCondition(condition);
		// 执行查询，返回结果
		List<ShoppingCart> shoppingCartList = new ArrayList<ShoppingCart>();
		BasePojo<ShoppingCart> basePojo = new BasePojo<ShoppingCart>();

		shoppingCartList = shoppingCartService.shoppingCartFind(shoppingCart);
		if (shoppingCartList != null && !shoppingCartList.isEmpty()) {
			basePojo.setSuccess(true);
			basePojo.setMsg("查找成功！");
			basePojo.setList(shoppingCartList);
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("查找失败！");
		}
		// System.out.println("shoppingCartList: " +
		// shoppingCartList.get(0));
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
