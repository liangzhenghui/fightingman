package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: liangzhenghui
 * @blog: http://my.oschina.net/liangzhenghui/blog
 * @email:715818885@qq.com
 * 2015年5月9日 上午10:42:01
 */
public class DateUtils {
	/**日期转换成字符串
	 * yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String DateToStr(Date date,String pattern) {
	  
	   SimpleDateFormat format = new SimpleDateFormat(pattern);
	   String str = format.format(date);
	   return str;
	} 

	/**字符串转换成日期
	 * yyyy-MM-dd HH:mm:ss
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Date StrToDate(String str,String pattern) {
	  
	   SimpleDateFormat format = new SimpleDateFormat(pattern);
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date;
	}
}
