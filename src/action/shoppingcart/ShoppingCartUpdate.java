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
 * @Title: ShoppingCartUpdate
 * @Description: 购物车修改
 * @author yhfu
 * @date 2018年10月23日 下午3:34:50
 */
@WebServlet("/ShoppingCartUpdate")
public class ShoppingCartUpdate extends HttpServlet {

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
		// 获取参数
		String paras = request.getParameter("paras");
		// paras = URLDecoder.decode(paras); // 解码url
		if (paras != null) {
			shoppingCart = new Gson().fromJson(paras, ShoppingCart.class); // 解析Json获得对象
		}

		// 执行修改，返回结果
		BasePojo<ShoppingCart> basePojo = new BasePojo<ShoppingCart>();

		if (shoppingCartService.shoppingCartSave(shoppingCart)) {
			basePojo.setSuccess(true);
			basePojo.setMsg("修改成功");
			shoppingCart.setCondition(" and cart_id =" + shoppingCart.getCart_id());
			shoppingCartService.shoppingCartFind(shoppingCart); // 获取修改后的实体类
			List<ShoppingCart> shoppingCartList = new ArrayList<ShoppingCart>();
			shoppingCartList.add(shoppingCart);
			basePojo.setList(shoppingCartList);

		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("修改失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
