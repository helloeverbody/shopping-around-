package qiniu;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;



public class getUptoken extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	private String getToken() {
		// 前一个参数是从七牛网站上得到的AccessKey,后一个参数是SecretKey，建议定期更新
		Auth auth = Auth.create("9Gqgj6TMPbNMAoMwnKTnnunUHYepYAavd5oQYRsu",
				"dxAM7sNx3mUvAjs8YLvt4bs3RNDIYvOBi2VHICzb");
		String bucket = Constant.QINIU_BUCKET; // 七牛上的空间名
		StringMap putPolicy = new StringMap();
		putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
		String uptoken = auth.uploadToken(bucket, null, 1000*60*60,putPolicy);
		//String token = auth.uploadToken(bucket, null, 1000*60*60,
		// new StringMap().put("insertOnly", "0")); //
		// 通过此方法获取的token只能上传指定key的图片，并且不允许修改，默认有效时间是1小时
		// 生成返回数据
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("uptoken", uptoken);
		return JSON.toJSONString(result);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		String resultJson = "";
		String key = req.getParameter("message");
		// String key = jsonObject.getString("key"); // 获得上传到七牛图片的唯一名称（包括路径）
		resultJson = getToken(); // 返回结果
		//System.out.print(resultJson);
		out.print(resultJson);
		out.flush();
		out.close();
	}


}
