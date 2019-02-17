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
 * @Title: ProductClassifyFind
 * @Description: 商品查询
 * @author yhfu
 * @date 2018年10月23日 下午3:31:36
 */
@WebServlet("/ProductClassifyFind")
public class ProductClassifyFind extends HttpServlet {

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

			productClassify.setPages(Integer.parseInt(page));
			productClassify.setLimit(Integer.parseInt(limit));

			conditionLimt = " limit " + start + "," + limitInt; // condition
		}

		condition = condition + conditionLimt;
		condition = condition.replace("null", ""); // condition为null替换为："";

		System.out.println("condition: " + condition);
		productClassify.setCondition(condition);
		// 执行查询，返回结果
		List<ProductClassify> productClassifyList = new ArrayList<ProductClassify>();
		BasePojo<ProductClassify> basePojo = new BasePojo<ProductClassify>();

		productClassifyList = productClassifyService.productClassifyFind(productClassify);
		for (ProductClassify productClassify:productClassifyList) {
			System.out.println(productClassify);

		}
		if (productClassifyList != null && !productClassifyList.isEmpty()) {
			basePojo.setSuccess(true);
			basePojo.setMsg("查找成功！");
			basePojo.setList(productClassifyList);

			//获取数据总数的条件
			if (condition == null) {
				condition = "";
			}
			condition = condition.replace(conditionLimt,"");
			productClassify.setCondition(condition);
			int total = productClassifyService.productClassifyCount(productClassify); // 获取总数据数
			basePojo.setTotal(total);


			if(page !=null){
				basePojo.setPage(Integer.valueOf(page));
			}
			if(limit != null){
				basePojo.setLimit(Integer.valueOf(limit));
			}
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("未查到相关数据！");
		}
		// System.out.println("productClassifyList: " +
		// productClassifyList.get(0));
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
