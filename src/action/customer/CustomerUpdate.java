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
import util.QiniuUtil;

/**
 * Title: CustomerUpdate
 * @Description: 用户修改
 * @author yhfu
 * @date 2018年10月22日/下午9:39:06
 */
@WebServlet("/CustomerUpdate")
public class CustomerUpdate extends HttpServlet {

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
		String key = request.getParameter("key"); // 获取key参数
		// paras = URLDecoder.decode(paras); // 解码url

		System.out.println("key：" + key);
		System.out.println("paras：" + paras);
		if (paras != null) {
			customer = new Gson().fromJson(paras, Customer.class); // 解析Json获得对象
			System.out.println("customer: " + customer);
		}

		// 执行修改，返回结果
		BasePojo<Customer> basePojo = new BasePojo<Customer>();

		if (customer != null && customerService.customerSave(customer)) {
			if (key != null) {
				// 删除七牛原图像
				QiniuUtil.delFile(key);
			}
			basePojo.setSuccess(true);
			basePojo.setMsg("修改成功");
			customer.setCondition(" and cust_id =" + customer.getCust_id());
			customerService.customerFind(customer); // 获取修改后的实体类
			List<Customer> customerList = new ArrayList<Customer>();
			customerList.add(customer);
			basePojo.setList(customerList);

		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("修改失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
