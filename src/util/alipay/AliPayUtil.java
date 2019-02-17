package util.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

/**
 * @ClassName CreateAliPayOrder
 * @Description TDOD
 * @Author yhfu
 * @Date 2018/12/5 9:21
 * @Version 1.0
 **/
public class AliPayUtil {

    public final static String APP_ID = "2016092000557509";
    public final static String CHARSET = "UTF-8";
    public final static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCWU2+0AY6QmdF6jIfOv9crjk8VHN6Vhd3fe7NdI0hHWO8+nSjoIPOCKn9ml8jGqTBr4wpVz268QCZnz5yxx2OpTpQ3VPIXWpe6yohM+4C0nciyeHOyay3WG6GhE5FkyqL1LG3zQkgaKZLMmOFnftomypRrGbuutSWo6b/bBPzsq2LXLUo1UyWk0G/xph/ywzqtesHFduMiMwWsI6VTqmvvWYLBtlXIBfxIp7shw/S/Rvdaya2L6EU4S3OlVDvmeCXiyXAmlp38ckNEZtfv+fv+1nhvVejzmPz4ibjzAI0Nohh/g18unDNIvNW/6J8JTQqBdt3q4xIzxhecOC2O6QN/AgMBAAECggEAMb1355LgTl5nHuqjATkfBE15Q14jFEyQJqNyrO3Au8Lkns43u98zU62mLBcrGvjeicQ9JjmVxXpgetcKXRTBmELk7g4QcMPMEsVzJi3N33WZ89vBRJcMOp039OlZoYUfOIqz8trKqsV4ynQHmUwy+MTtKScBoARWara1gc37tn9Xdzl8r1oL7KtaQVdBurkonKvLfrM58dqo+koYpgmOmUb1+X0D2KSATGSp4AXnlmwLfY9enJ/1w5tRmDVCFtr+Em2YfH7eCBf5yR+5q1gDCNK9znOsIFsmoNP3X5PgBiW7jZqO5B0o8ELaDYOV2IIRES1t8IEO2qWKol4bF0BfEQKBgQDn6kVdY+SmR+B++T/O8+7qu8UPHMezFau66usp7K/hts7jc/FFIqNCYulWOeiAe/lYYQI+YX000IqILK4m5nvLRgP7socbAZ/AFa/ruNsGu3uN56xnidzuXSNBjkXaxhhRIn0M8cjxse7TGPz20YlVWckmCegWZk/jgIf9IdSv9wKBgQCl8AXsdDswIOpVqYKN1zY14T+MKuClChnqwP7WR1NzXhmDBknKBLXKJJWUag/Bs9MkHBxcN9WOU6XlFxQzAgbxunEzZ5D5pefwzmNm6HEVb0wm5oNI3WaM/u+zpExkOM7VAaZNV5xua8bLJoqHpUi/ZjGwfUuxX4Bpci15czZ2uQKBgAiA44g4GHMsYYPmWbuZ4CHFPsV+FahwW/qfkH3aCtzo2g8m+irByWCuxcV+nBLR+OOPswhchL3CA8JYeQ4+Fz3t1EmPUzrfRV9CrE+lWIqXp0EdxImLWYl36soFkHQkHVIkRJeL6gVKs+oTmXrnVfTGNQTrChKAL9+0n8fdTLIjAoGATWMXICNExzlSkAix3jyUB6S44kHM6qs/UwAW2RAClzc3Zqe0GgcAUPmm2MRt/MREn/6MK2XOrsXMBH8y6Gb1nh55VKPkNMlu1hsquH+G3Zw+Q5YVfBHlFbR/TaQ+DC+CJkzSphdJ2SViXLPov0ISH1Els01704jadlLNamqU5+ECgYEAuvtqC5Hz/Oe2NoWezWA8sXm0yQB82Sfjx4YKdE1vt66OZQfzVBM2jXnWO4L7b1MPn1C5L7MEbYEOz8M+yRW5bUccklCchodVqly4qMdoMonMlFsXml2ILSo1Od13bwlWhRmWMnN1ky4ib5fnVTPriS8hSuoBDxXoaJ5QNyzIf74=";
    public final static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvwYbPsaJJXK9+qBQtUljFEtCyC6oHtz0Jb9/g7sqWvVir2Q0C8zZp29sYnONNYs2zPaldcNWpz/VRT4MHwqEBR47kKINzplIpTloHb/5kGSoD4J5WWLpfqcr60hbIgxu+sWlR2Cm8hYoKn3Bvk4fm+okCp36qmqyFjlC6zaDwWJWUaF8ZZ4yiQDSKozVzfrCrcU2dtyLOoPZV7mjgXtbeknWs1U/xmh0Elb294YqCYRBk+VCKNP/lhPjun9zhP8UWeUZApPwKus4f28DfDtr7aldwunHpRBw61nZnL5Os569o2B9MY9r/ICU5zgkCCpim3VcEnIQqtj5uoXz7/9zpQIDAQAB";

    public static String createOrder(AlipayTradeAppPayModel model) {



        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        /*AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("App支付测试Java");
        model.setOutTradeNo("2018120510084831");
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");*/
        request.setBizModel(model);
        request.setNotifyUrl("http://xgg.nat300.top/xgg/AsyncNotice");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//orderString 可以直接给客户端请求，无需再做处理。
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }


    }



}
