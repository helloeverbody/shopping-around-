package action.product;

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
import pojo.Product;
import service.ProductService;
import service.impl.ProductServiceImpl;

/**
 * @Title: ProductFind
 * @Description: 商品店铺查询
 * @author yhfu
 * @date 2018年10月23日 下午3:31:36
 */
@WebServlet("/ProductShopFind")
public class ProductShopFind extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ProductService productService = new ProductServiceImpl();
	private Product product = new Product();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter(); // 用PrintWriter对象将返回结果写入服务器
		// 查询条件
		String condition = request.getParameter("condition"); //condition 示例为：and + 查询条件

		System.out.println("condition: " + condition);
		product.setCondition(condition);
		// 执行查询，返回结果
		List<List<Product>> dataList = new ArrayList<List<Product>>();
		BasePojo<List<Product>> basePojo = new BasePojo<List<Product>>();

		dataList = productService.productShopFind(product);
		if (dataList != null && !dataList.isEmpty()) {
			basePojo.setSuccess(true);
			basePojo.setMsg("查找成功！");
			basePojo.setList(dataList);



		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("查找为空！");
			basePojo.setList(dataList);
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
