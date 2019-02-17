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
import util.QiniuUtil;

/**
 * Title: OrderUpdate
 * 
 * @Description: 订单修改
 * @author yhfu
 * @date 2018年10月22日/下午9:39:06
 */
@WebServlet("/OrderUpdate")
public class OrderUpdate extends HttpServlet {

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
		String key = request.getParameter("key"); // 获取key参数
		// paras = URLDecoder.decode(paras); // 解码url
		System.out.println("key：" + key);
		System.out.println("paras：" + paras);
		if (paras != null) {
			order = new Gson().fromJson(paras, Order.class); // 解析Json获得对象
			System.out.println("order: " + order);
		}

		// 执行修改，返回结果
		BasePojo<Order> basePojo = new BasePojo<Order>();

		if (order != null && orderService.orderSave(order)) {
			if (key != null) {
				// 删除七牛原图像
				QiniuUtil.delFile(key);
			}
			basePojo.setSuccess(true);
			basePojo.setMsg("修改成功");
			order.setCondition(" and orde_id =" + order.getOrde_id());
			orderService.orderFind(order); // 获取修改后的实体类
			List<Order> orderList = new ArrayList<Order>();
			orderList.add(order);
			basePojo.setList(orderList);

		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("修改失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
