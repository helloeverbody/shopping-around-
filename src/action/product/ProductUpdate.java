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
import util.QiniuUtil;

/**
 * @Title: ProductUpdate
 * @Description: 商品修改
 * @author yhfu
 * @date 2018年10月23日 下午3:34:50
 */
@WebServlet("/ProductUpdate")
public class ProductUpdate extends HttpServlet {

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
		String key = request.getParameter("key"); // 获取key参数
		// paras = URLDecoder.decode(paras); // 解码url
		if (paras != null) {
			product = new Gson().fromJson(paras, Product.class); // 解析Json获得对象
		}

		System.out.println("ProductUpdata:"+product);

		// 执行修改，返回结果
		BasePojo<Product> basePojo = new BasePojo<Product>();

		if (productService.productSave(product)) {
			if (key != null) {
				// 删除七牛原图像
				QiniuUtil.delFile(key);
			}
			basePojo.setSuccess(true);
			basePojo.setMsg("修改成功");
			product.setCondition(" and prod_id =" + product.getProd_id());
			productService.productFind(product); // 获取修改后的实体类
			List<Product> productList = new ArrayList<Product>();
			productList.add(product);
			basePojo.setList(productList);

		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("修改失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
