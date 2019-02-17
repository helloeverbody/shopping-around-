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
 * Title: orderLogin
 * 
 * @Description: 订单登录
 * @author yhfu
 * @date 2018年10月22日/下午3:15:46
 */
@WebServlet("/OrderAdd")
public class OrderAdd extends HttpServlet {

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
		// 获取参数
		String paras = request.getParameter("paras");
		// paras = URLDecoder.decode(paras); //解码url
		System.out.println(paras);
		if (paras != null) {
			order = new Gson().fromJson(paras, Order.class); // 解析Json获得对象
		}
		// 执行添加，返回结果
		int orde_id = orderService.orderAdd(order); // 添加新订单，并得到id
		BasePojo<Order> basePojo = new BasePojo<Order>();

		if (orde_id > 0) {
			basePojo.setSuccess(true);
			basePojo.setMsg("添加成功！ 新订单id为" + orde_id);
			order.setOrde_id(orde_id);
			List<Order> custometList = new ArrayList<Order>();
			custometList.add(order);
			basePojo.setList(custometList);
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("注册失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
