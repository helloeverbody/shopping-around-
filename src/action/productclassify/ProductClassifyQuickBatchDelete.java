package action.productclassify;

import com.google.gson.Gson;

import pojo.BasePojo;
import pojo.ProductClassify;
import service.ProductClassifyService;
import service.impl.ProductClassifyServiceImpl;
import util.QiniuUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Title: ProductDelete Description: 商品分类删除
 * @author yhfu
 * @date 2018年10月22日/下午9:25:51
 */
@WebServlet(name = "ProductClassifyQuickBatchDelete",urlPatterns ="/ProductClassifyQuickBatchDelete")
public class ProductClassifyQuickBatchDelete extends HttpServlet {

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
		String pc_id = request.getParameter("pc_id");
		String pc_head=request.getParameter("pc_head");
		// 执行删除，返回结果
		BasePojo<ProductClassify> basePojo = new BasePojo<ProductClassify>();
		System.out.println("pc_id:"+pc_id);
		String condition = " and pc_id in" + pc_id; // 查询条件
		productClassify.setCondition(condition);

		if (pc_head != null && !pc_head.isEmpty()) {
			System.out.println(pc_head);
			String[] pc_heads = new Gson().fromJson(pc_head,String[].class);
			if(pc_heads.length>0){
				// 删除七牛原图像
				QiniuUtil.delBatchFile(pc_heads);
			}
		}
		if (productClassifyService.productClassifyDelByCondition(productClassify)) {
			basePojo.setSuccess(true);
			basePojo.setMsg("删除成功！");
		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("删除失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
