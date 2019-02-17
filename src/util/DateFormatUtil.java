package util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Date类格式化工具
 * @ClassName DateFormat
 * @Description TDOD
 * @Author yhfu
 * @Date 2018/12/6 19:01
 * @Version 1.0
 **/
public class DateFormatUtil {

    /**
     * 去掉数据库取出的datetime数据的“.0” 如：2018-12-06 16:08:42.0
     * @param timestamp rs.getTimestamp(5)) 得到的是一个到毫秒的值
     * @return 2018-12-06 16:08:42
     */
    public static String getDateFormat(Timestamp timestamp){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(timestamp);
        return date;
    }
}
