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
import java.util.ArrayList;
import java.util.List;


/**
 * @Title: ShopFind
 * @Description: 商家查询
 * @author yhfu
 * @date 2018年10月24日 下午4:08:39
 */
@WebServlet("/ShopFind")
public class ShopFind extends HttpServlet {

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
		// 查询条件 page=1&limit=30
		String page = request.getParameter("page"); //获取页码
		String limit = request.getParameter("limit"); //获取每页条数
		String condition = request.getParameter("condition"); // condition
		

		String conditionLimt = "";
		if (page != null && limit != null) {
			int limitInt = Integer.parseInt(limit);
			int start = (Integer.parseInt(page) - 1) * limitInt;
			conditionLimt = " limit " + start + "," + limitInt; // condition
		}

		condition = condition + conditionLimt;
		condition = condition.replace("null", ""); // condition为null替换为："";

		

		System.out.println("condition: " + condition);
		shop.setCondition(condition);
		// 执行查询，返回结果
		List<Shop> shopList = new ArrayList<Shop>();
		BasePojo<Shop> basePojo = new BasePojo<Shop>();

		shopList = shopService.shopFind(shop);
		if (shopList != null && !shopList.isEmpty()) {
			basePojo.setSuccess(true);
			basePojo.setMsg("查找成功！");
			basePojo.setList(shopList);


			condition = request.getParameter("condition"); // 重新设置查询条件
			if (condition == null) {
				condition = "";
			}
			shop.setCondition(condition);
			int total = shopService.shopCount(shop); // 获取总数据数
			basePojo.setTotal(total);

			if(page !=null){
				basePojo.setPage(Integer.valueOf(page));
			}
			if(limit != null){
				basePojo.setLimit(Integer.valueOf(limit));
			}
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("未查找到相关数据！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
