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
 * @Title: ProductClassifyUpdate
 * @Description: 商品类别修改
 * @author yhfu
 * @date 2018年10月23日 下午3:34:50
 */
@WebServlet("/ProductClassifyUpdate")
public class ProductClassifyUpdate extends HttpServlet {

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
		// paras = URLDecoder.decode(paras); // 解码url
		if (paras != null) {
			productClassify = new Gson().fromJson(paras, ProductClassify.class); // 解析Json获得对象
		}

		// 执行修改，返回结果
		BasePojo<ProductClassify> basePojo = new BasePojo<ProductClassify>();

		if (productClassifyService.productClassifySave(productClassify)) {
			basePojo.setSuccess(true);
			basePojo.setMsg("修改成功");
			productClassify.setCondition(" and pc_id =" + productClassify.getPc_id());
			productClassifyService.productClassifyFind(productClassify); // 获取修改后的实体类
			List<ProductClassify> productClassifyList = new ArrayList<ProductClassify>();
			productClassifyList.add(productClassify);
			basePojo.setList(productClassifyList);

		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("修改失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
