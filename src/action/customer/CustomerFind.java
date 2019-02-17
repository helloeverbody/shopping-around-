package action.customer;

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
import pojo.Customer;
import service.CustomerService;
import service.impl.CustomerServiceImpl;



/**
 * Title: CustomerFind
 * Description: 用户查找
 * @author yhfu
 * @date 2018年10月22日/下午8:31:30
 */
@WebServlet("/CustomerFind")
public class CustomerFind extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private CustomerService customerService = new CustomerServiceImpl();
	private Customer customer = new Customer();

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
		String condition = request.getParameter("condition"); // condition 示例为：and +
		System.out.println("page:" + page);
		System.out.println("limit:"+limit);
		String conditionLimt = "";
		if (page != null && limit != null) {
			int limitInt = Integer.parseInt(limit);
			int start = (Integer.parseInt(page) - 1) * limitInt;
			conditionLimt = " limit " + start + "," + limitInt; // condition
		}

		condition = condition + conditionLimt;
		condition = condition.replace("null", ""); // condition为null替换为："";


		customer.setCondition(condition);
		// 执行查询，返回结果
		List<Customer> customerList = new ArrayList<Customer>();
		BasePojo<Customer> basePojo = new BasePojo<Customer>();

		customerList = customerService.customerFind(customer);
		if (customerList != null && !customerList.isEmpty()) {
			basePojo.setSuccess(true);
			basePojo.setMsg("查找成功！");
			basePojo.setList(customerList);
			condition = request.getParameter("condition"); // 重新设置查询条件
			if (condition == null) {
				condition = "";
			}
			condition = condition.replace(conditionLimt,"");
			customer.setCondition(condition);
			int total = customerService.customerCount(customer); // 获取总数据数
			basePojo.setTotal(total);

		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("数据为空！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
