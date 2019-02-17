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
import qiniu.Constant;
import service.CustomerService;
import service.impl.CustomerServiceImpl;
import util.QiniuUtil;

/**
 * Title: CustomerDelete Description: 用户删除
 * 
 * @author yhfu
 * @date 2018年10月22日/下午9:25:51
 */
@WebServlet("/CustomerDelete")
public class CustomerDelete extends HttpServlet {

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
		// 获取id
		int cust_id = Integer.parseInt(request.getParameter("cust_id"));
		customer.setCust_id(cust_id);

		// 执行删除，返回结果
		BasePojo<Customer> basePojo = new BasePojo<Customer>();

		String condition = " and cust_id=" + cust_id; // 查询条件
		customer.setCondition(condition);
		List<Customer> customerList = new ArrayList<Customer>();
		customerList = customerService.customerFind(customer);
		String cust_head = customerList.get(0).getCust_head();
		if (cust_head != null && !cust_head.isEmpty()) {
			String key = cust_head.replace(Constant.QINIU_DOMAIN, "");
			// 删除七牛原图像
			QiniuUtil.delFile(key);
		}
		if (customerService.customerDel(customer)) {
			basePojo.setSuccess(true);
			basePojo.setMsg("删除成功！");
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("删除失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
