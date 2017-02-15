package util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.Map;

/**
 * map转成成javaBean
 * 
 * @author liangzhenghui
 *
 */
public class MapToBeanUtil {
	
	/**
	 * map转成成javaBean
	 *  Class<T> Class 首先是个Class对象，描述的意思是构建T这个类型的模板
		T 是个T对象，描述的意思是这个对象是T这个类型。
	 * @param map
	 * @param clz
	 * @return
	 */
	public static <T> T mapToBean(Map<String, Object> map, Class<T> clz) {
	    T obj = null;
	    try {
	        obj = clz.newInstance();
	        BeanInfo beanInfo = Introspector.getBeanInfo(clz);
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
	        for (PropertyDescriptor property : propertyDescriptors) {
	            String key = property.getName();
	            key = key.toUpperCase();
	            if (map.containsKey(key)) {
	            	//String type=map.get(key).getClass().getSimpleName();
	            	Object value=null;
	            	/*if(type.equals("Date")){
	            		Date date = (Date)map.get(key);
	            		//value=DateUtils.DateToStr(date, "yyyy-MM-dd HH:mm:ss");
	            		value=String.valueOf(date.getTime());//for angular
	            	}else{
	            		value = map.get(key);
		                // property对应的setter方法
		                //调用obj对象对应属性property的set方法，值为value,比如调用name的setName方法值为xxx,达到了赋值的作用
	            	}*/
	            	value = map.get(key);
	            	property.getWriteMethod().invoke(obj, value);
	                
	            }
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
	    return obj;
	}
}
/**propertyDescriptors对应的值
 *[java.beans.PropertyDescriptor[name=age; propertyType=class java.lang.String; readMethod=public java.lang.String model.Person.getAge(); writeMethod=public void model.Person.setAge(java.lang.String)], java.beans.PropertyDescriptor[name=class; propertyType=class java.lang.Class; readMethod=public final native java.lang.Class java.lang.Object.getClass()], java.beans.PropertyDescriptor[name=name; propertyType=class java.lang.String; readMethod=public java.lang.String model.Person.getName(); writeMethod=public void model.Person.setName(java.lang.String)]]
 *
 */
