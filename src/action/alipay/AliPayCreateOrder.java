package action.alipay;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.google.gson.Gson;

import util.alipay.AliPayUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "AliPayCreateOrder", urlPatterns = "/AliPayCreateOrder")
public class AliPayCreateOrder extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter(); // 用PrintWriter对象将返回结果写入服务器
        // 获取参数
        String orderInfo = request.getParameter("orderInfo");
        System.out.println("modelString:"+orderInfo);
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        Map<String,String> orderMap = new Gson().fromJson(orderInfo,Map.class);
        System.out.println("orderMap:"+orderMap);

        model.setBody(orderMap.get("body"));
        model.setSubject(orderMap.get("subject"));
        model.setOutTradeNo(orderMap.get("out_trade_no"));

        model.setTotalAmount(orderMap.get("total_amount"));
        model.setProductCode(orderMap.get("product_code"));

        System.out.println("model.Subject:"+model.getSubject().toString());
        System.out.println("model:"+model.toString());
        //生成订单信息
        String newOrderInfo = AliPayUtil.createOrder(model);
        System.out.println("orderInfo:"+newOrderInfo);

        out.print(newOrderInfo);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
