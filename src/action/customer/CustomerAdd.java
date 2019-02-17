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
@WebServlet("/CustomerAdd")
public class CustomerAdd extends HttpServlet {

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
		String paras = request.getParameter("paras");
		// paras = URLDecoder.decode(paras); //解码url
		System.out.println(paras);
		if (paras != null) {
			customer = new Gson().fromJson(paras, Customer.class); // 解析Json获得对象
		}
		// 执行添加，返回结果
		int cust_id = customerService.customerAdd(customer); // 添加新用户，并得到id
		BasePojo<Customer> basePojo = new BasePojo<Customer>();

		if (cust_id > 0) {
			basePojo.setSuccess(true);
			basePojo.setMsg("添加成功！ 新用户id为" + cust_id);
			customer.setCust_id(cust_id);
			List<Customer> custometList = new ArrayList<Customer>();
			custometList.add(customer);
			basePojo.setList(custometList);
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("注册失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
