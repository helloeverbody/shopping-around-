package action.product;

import com.google.gson.Gson;

import pojo.BasePojo;
import pojo.Product;
import service.ProductService;
import service.impl.ProductServiceImpl;
import util.QiniuUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Title: ProductDelete Description: 商品删除
 * @author yhfu
 * @date 2018年10月22日/下午9:25:51
 */
@WebServlet("/ProductQuickBatchDelete")
public class ProductQuickBatchDelete extends HttpServlet {

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
		String prod_id = request.getParameter("prod_id"); //(1,2,3,4,5,6...)
		String prod_head=request.getParameter("prod_head");
		// 执行删除，返回结果
		BasePojo<Product> basePojo = new BasePojo<Product>();
		System.out.println("prod_id:"+prod_id);
		String condition = " and prod_id in" + prod_id; // 查询条件
		product.setCondition(condition);

		if (prod_head != null && !prod_head.isEmpty()) {
			System.out.println(prod_head);
			String[] prod_heads = new Gson().fromJson(prod_head,String[].class);
			if(prod_heads.length>0){
				// 删除七牛原图像
				QiniuUtil.delBatchFile(prod_heads);
			}
		}
		if (productService.productDelByCondition(product)) {
			basePojo.setSuccess(true);
			basePojo.setMsg("删除成功！");
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("删除失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
