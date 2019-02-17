package action.productclassify;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import pojo.BasePojo;
import pojo.ProductClassify;
import service.ProductClassifyService;
import service.impl.ProductClassifyServiceImpl;

/**
 * @Title: ProductClassifyDelete
 * @Description: 商品删除
 * @author yhfu
 * @date 2018年10月23日 下午3:28:11
 */
@WebServlet("/ProductClassifyDelete")
public class ProductClassifyDelete extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ProductClassifyService productClassifyService = new ProductClassifyServiceImpl();
	private ProductClassify productClassify = new ProductClassify();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter(); // 用PrintWriter对象将返回结果写入服务器
		// 获取id
		int pc_id = Integer.parseInt(request.getParameter("pc_id"));

		productClassify.setPc_id(pc_id);

		// 执行删除，返回结果
		BasePojo<ProductClassify> basePojo = new BasePojo<ProductClassify>();

		if (productClassifyService.productClassifyDel(productClassify)) {
			basePojo.setSuccess(true);
			basePojo.setMsg("删除成功！");
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("删除失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
