package qiniu;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;


public class del extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter(); // 用PrintWriter对象将返回结果写入服务器
		
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		// ...其他参数参考类注释

		String accessKey = "9Gqgj6TMPbNMAoMwnKTnnunUHYepYAavd5oQYRsu";
		String secretKey = "dxAM7sNx3mUvAjs8YLvt4bs3RNDIYvOBi2VHICzb";

		String bucket = Constant.QINIU_BUCKET;
		String key = req.getParameter("key");

		Auth auth = Auth.create(accessKey, secretKey);
		BucketManager bucketManager = new BucketManager(auth, cfg);
		try {
		    bucketManager.delete(bucket, key);
		    out.print("success");
		    
		} catch (QiniuException ex) {
			// 如果遇到异常，说明删除失败
		    System.err.println(ex.code());
		    System.err.println(ex.response.toString());
		    out.print("faile");
		}

	}


}
