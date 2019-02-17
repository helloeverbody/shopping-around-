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
 * @Description: 商品查询
 * @author yhfu
 * @date 2018年10月23日 下午3:31:36
 */
@WebServlet("/ProductFind")
public class ProductFind extends HttpServlet {

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

		product.setCondition(condition);

		// 执行查询，返回结果
		List<Product> productList = new ArrayList<Product>();
		BasePojo<Product> basePojo = new BasePojo<Product>();

		productList = productService.productFind(product);
		if (productList != null && !productList.isEmpty()) {
			basePojo.setSuccess(true);
			basePojo.setMsg("查找成功！");
			basePojo.setList(productList);
			//获取数据总数的条件
			if (condition == null) {
				condition = "";
			}
			condition = condition.replace(conditionLimt,"");
			product.setCondition(condition);
			int total = productService.productCount(product); // 获取总数据数
			basePojo.setTotal(total);
			if(page != null){

				basePojo.setPage(Integer.parseInt(page));
			}
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("查找失败！");
		}
		// System.out.println("productList: " + productList.get(0));
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
