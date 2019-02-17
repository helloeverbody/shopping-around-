package action.shop;

import com.google.gson.Gson;

import pojo.BasePojo;
import pojo.Shop;
import service.ShopService;
import service.impl.ShopServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ShopUpdate
 * @Description: 商家修改
 * @author yhfu
 * @date 2018年10月24日 下午4:22:24
 */
@WebServlet("/ShopUpdate")
public class ShopUpdate extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ShopService shopService = new ShopServiceImpl();
	private Shop shop = new Shop();

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
			shop = new Gson().fromJson(paras, Shop.class); // 解析Json获得对象
		}

		// 执行修改，返回结果
		BasePojo<Shop> basePojo = new BasePojo<Shop>();

		if (shopService.shopSave(shop)) {
			basePojo.setSuccess(true);
			basePojo.setMsg("修改成功");
			shop.setCondition(" and shop_id =" + shop.getShop_id());
			shopService.shopFind(shop); // 获取修改后的实体类
			List<Shop> shopList = new ArrayList<Shop>();
			shopList.add(shop);
			basePojo.setList(shopList);

		} else {
			basePojo.setSuccess(false);
			basePojo.setMsg("修改失败！");
		}
		out.print(new Gson().toJson(basePojo)); // 写入服务器

	}

}
