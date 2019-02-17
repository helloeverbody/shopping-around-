package action.order;

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
import pojo.Order;
import service.OrderService;
import service.impl.OrderServiceImpl;


/**
 * Title: OrderFind Description: 订单查找
 * 
 * @author yhfu
 * @date 2018年10月22日/下午8:31:30
 */
@WebServlet("/OrderFind")
public class OrderFind extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private OrderService orderService = new OrderServiceImpl();
	private Order order = new Order();

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
		// 示例为：and +

		String conditionLimt = "";
		order.setPages(null);
		order.setLimit(null);
		if (page != null && limit != null) {
			int limitInt = Integer.parseInt(limit);
			int start = (Integer.parseInt(page) - 1) * limitInt;
			//conditionLimt = " limit " + start + "," + limitInt; // condition
			//测试
			order.setPages(start);
			order.setLimit(limitInt);
		}

		condition = condition + conditionLimt;
		condition = condition.replace("null", ""); // condition为null替换为："";

		System.out.println("condition: " + condition);

		order.setCondition(condition);
		// 执行查询，返回结果
		List<Order> orderList = new ArrayList<Order>();
		BasePojo<Order> basePojo = new BasePojo<Order>();

		orderList = orderService.orderFind(order);
		if (orderList != null && !orderList.isEmpty()) {
			basePojo.setSuccess(true);
			basePojo.setMsg("查找成功！");
			basePojo.setList(orderList);
			condition = request.getParameter("condition"); // 重新设置查询条件
			if (condition == null) {
				condition = "";
			}
			order.setCondition(condition);
			int total = orderService.orderCount(order); // 获取总数据数
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
