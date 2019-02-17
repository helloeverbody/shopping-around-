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
 * @Title: ProductAdd
 * @Description: 商品添加
 * @author yhfu
 * @date 2018年10月23日 下午3:22:15
 */
@WebServlet("/ProductAdd")
public class ProductAdd extends HttpServlet {

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
		// 获取参数
		String paras = request.getParameter("paras");
		// paras = URLDecoder.decode(paras); //解码url
		System.out.println(paras);
		if (paras != null) {
			product = new Gson().fromJson(paras, Product.class); // 解析Json获得对象
		}
		System.out.println("product:"+product);
		// 执行添加，返回结果
		int prod_id = productService.productAdd(product); // 添加新商品，并得到id
		BasePojo<Product> basePojo = new BasePojo<Product>();

		if (prod_id > 0) {
			basePojo.setSuccess(true);
			basePojo.setMsg("添加成功！ 新商品id为" + prod_id);

			product.setProd_id(prod_id);
			List<Product> list = new ArrayList<>();
			list.add(product);

			basePojo.setList(list);
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("注册失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
