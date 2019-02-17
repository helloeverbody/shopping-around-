package util;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;
import qiniu.Constant;
import java.util.List;

public class QiniuUtil {

	// 在七牛注册后获得的accessKey和secretKey（改为自己的）
	private static String accessKey = "9Gqgj6TMPbNMAoMwnKTnnunUHYepYAavd5oQYRsu";
	private static String secretKey = "dxAM7sNx3mUvAjs8YLvt4bs3RNDIYvOBi2VHICzb";
	private static String bucket = Constant.QINIU_BUCKET; // 七牛空间名（改为自己的）
	
	public static String getToken(){
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket); // 生成普通上传的Token
		return upToken;
	}
	
	public static void delFile(String key){
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		Auth auth = Auth.create(accessKey, secretKey);
		BucketManager bucketManager = new BucketManager(auth, cfg);
		try {
		    bucketManager.delete(bucket, key);
		    System.out.println(key);
			System.out.println("删除成功！");
		} catch (QiniuException ex) {
		    //如果遇到异常，说明删除失败
			System.err.println(key);
		    System.err.println(ex.code());
		    System.err.println(ex.response.error.toString());
		    System.out.println("删除图片失败");
		}
	}
	public static void delBatchFile(String[] keyList){
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		Auth auth = Auth.create(accessKey, secretKey);
		BucketManager bucketManager = new BucketManager(auth, cfg);

		try {
			//单次批量请求的文件数量不得超过1000
			BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
			batchOperations.addDeleteOp(bucket, keyList);
			Response response = bucketManager.batch(batchOperations);
			BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
			for (int i = 0; i < keyList.length; i++) {
				BatchStatus status = batchStatusList[i];
				String key = keyList[i];
				System.out.print(key + "\t");
				if (status.code == 200) {
					System.out.println("delete success");
				} else {
					System.out.println(status.data.error);
				}
			}
		} catch (QiniuException ex) {
			System.err.println(ex.response.toString());
		}
	}

	public static void main(String[] args) {
		String[] imgs= new String[]{
				"del-1.jpg",
				"del-2.jpg",
				"del-3.jpg",
				"del-4.jpg",
				"del-5.jpg",
				"del-6.jpg",
				"del-7.jpg",
				"del-8.jpg"
		} ;
		String out = "";
		for (int i = 0; i < imgs.length; i++) {
			out += "\""+imgs[i]+"\",";

		}
		System.out.println(out);
		//QiniuUtil.delBatchFile(imgs);
	}





}
