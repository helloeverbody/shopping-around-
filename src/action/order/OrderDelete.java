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
 * Title: OrderDelete Description: 订单删除
 * 
 * @author yhfu
 * @date 2018年10月22日/下午9:25:51
 */
@WebServlet("/OrderDelete")
public class OrderDelete extends HttpServlet {

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
		// 获取id
		int orde_id = Integer.parseInt(request.getParameter("orde_id"));
		order.setOrde_id(orde_id);

		// 执行删除，返回结果
		BasePojo<Order> basePojo = new BasePojo<Order>();

		String condition = " and orde_id=" + orde_id; // 查询条件
		order.setCondition(condition);
		List<Order> orderList = new ArrayList<Order>();
		orderList = orderService.orderFind(order);
		// String orde_head = orderList.get(0).getOrde_head();
		// if (orde_head != null && !orde_head.isEmpty()) {
		// String key = orde_head.replace(Constant.QINIU_DOMAIN, "");
		// // 删除七牛原图像
		// QiniuUtil.delFile(key);
		// }
		if (orderService.orderDel(order)) {
			basePojo.setSuccess(true);
			basePojo.setMsg("删除成功！");
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("删除失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
