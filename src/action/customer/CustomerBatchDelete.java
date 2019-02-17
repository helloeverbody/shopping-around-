package action.customer;

import com.google.gson.Gson;

import pojo.BasePojo;
import pojo.Customer;
import service.CustomerService;
import service.impl.CustomerServiceImpl;
import util.QiniuUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: CustomerDelete Description: 用户删除
 * 
 * @author yhfu
 * @date 2018年10月22日/下午9:25:51
 */
@WebServlet("/CustomerBatchDelete")
public class CustomerBatchDelete extends HttpServlet {

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
		// 获取id和图片key
		String cust_id=request.getParameter("cust_id");
		String cust_head=request.getParameter("cust_head");


		// 执行删除，返回结果
		BasePojo<Customer> basePojo = new BasePojo<Customer>();

		int[] cust_ids = new Gson().fromJson(cust_id,int[].class);

		if (cust_head != null && !cust_head.isEmpty()) {
			System.out.println(cust_head);
			String[] cust_heads = new Gson().fromJson(cust_head,String[].class);
			if(cust_heads.length>0){
				// 删除七牛原图像
				QiniuUtil.delBatchFile(cust_heads);
			}
		}
		if (customerService.customerBatchDel(cust_ids)) {
			basePojo.setSuccess(true);
			basePojo.setMsg("删除成功！");
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("删除失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
