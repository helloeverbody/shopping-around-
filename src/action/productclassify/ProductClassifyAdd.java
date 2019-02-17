package action.productclassify;

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
import pojo.ProductClassify;
import service.ProductClassifyService;
import service.impl.ProductClassifyServiceImpl;

/**
 * @Title: ProductClassifyAdd
 * @Description: 商品分类添加
 * @author yhfu
 * @date 2018年10月23日 下午3:22:15
 */
@WebServlet("/ProductClassifyAdd")
public class ProductClassifyAdd extends HttpServlet {
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
		// 获取参数
		String paras = request.getParameter("paras");
		// paras = URLDecoder.decode(paras); //解码url
		System.out.println(paras);
		if (paras != null) {
			productClassify = new Gson().fromJson(paras, ProductClassify.class); // 解析Json获得对象
		}
		// 执行添加，返回结果
		int pc_id = productClassifyService.productClassifyAdd(productClassify); // 添加新商品，并得到id
		BasePojo<ProductClassify> basePojo = new BasePojo<ProductClassify>();

		if (pc_id > 0) {
			basePojo.setSuccess(true);
			basePojo.setMsg("添加成功！ 新商品分类id为" + pc_id);

			productClassify.setPc_id(pc_id);

			List<ProductClassify> list = new ArrayList<>();
			list.add(productClassify);
			basePojo.setList(list);

		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("注册失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
