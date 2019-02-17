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
 * 用户注册
 * 
 * @Title: CustomerRegist Description:
 * @author yhfu
 * @date 2018年10月22日/下午8:01:57
 */
@WebServlet("/CustomerRegist")
public class CustomerRegist extends HttpServlet {

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
		// 获取参数
		String cust_account = request.getParameter("cust_account");
		String cust_password = request.getParameter("cust_password");

		customer.setCust_account(cust_account);
		customer.setCust_password(cust_password);
		customer.setCust_sex(0);
		// 执行添加，返回结果
		int cust_id = customerService.customerAdd(customer); // 添加新用户，并得到id

		BasePojo<Customer> basePojo = new BasePojo<Customer>();

		if (cust_id > 0) {

			List<Customer> customerList = new ArrayList<Customer>();
			String condition = " and cust_id =" + cust_id;
			customer.setCondition(condition);
			customerList = customerService.customerFind(customer); // 查询新插入的用户

			basePojo.setSuccess(true);
			basePojo.setMsg("注册成功！");
			basePojo.setList(customerList);

		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("注册失败！");
		}

		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
