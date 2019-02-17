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
 * Title: customerLogin
 * @Description: 用户登录
 * @author yhfu
 * @date 2018年10月22日/下午3:15:46
 */
@WebServlet("/CustomerLogin")
public class CustomerLogin extends HttpServlet {

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
		// 查询条件
		String cust_account = request.getParameter("cust_account");
		String cust_password = request.getParameter("cust_password");
		String condition = " and cust_account='" + cust_account + "' and cust_password='" + cust_password+"'";
		
		customer.setCondition(condition);
		//执行查询，返回结果
		List<Customer> customerList = new ArrayList<Customer>();
		BasePojo<Customer> basePojo = new BasePojo<Customer>();

		customerList = customerService.customerFind(customer);
		if (customerList != null && !customerList.isEmpty()) {
			basePojo.setSuccess(true);
			basePojo.setMsg("登录成功！");
			basePojo.setList(customerList);
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("账号或密码有误！");
		}

		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
