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
import qiniu.Constant;
import service.ProductService;
import service.impl.ProductServiceImpl;
import util.QiniuUtil;

/**
 * @Title: ProductDelete
 * @Description: 商品删除
 * @author yhfu
 * @date 2018年10月23日 下午3:28:11
 */
@WebServlet("/ProductDelete")
public class ProductDelete extends HttpServlet {

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
		// 获取id
		int prod_id = Integer.parseInt(request.getParameter("prod_id"));

		product.setProd_id(prod_id);
		String condition = " and prod_id=" + prod_id; // 查询条件
		product.setCondition(condition);
		List<Product> productList = new ArrayList<Product>();
		productList = productService.productFind(product);
		String prod_head = productList.get(0).getProd_head();
		if (prod_head != null && !prod_head.isEmpty()) {
			String key = prod_head.replace(Constant.QINIU_DOMAIN, "");
			// 删除七牛原图像
			QiniuUtil.delFile(key);
		}
		// 执行删除，返回结果
		BasePojo<Product> basePojo = new BasePojo<Product>();

		if (productService.productDel(product)) {
			basePojo.setSuccess(true);
			basePojo.setMsg("删除成功！");
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("删除失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
