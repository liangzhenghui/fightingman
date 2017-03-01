package aop;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AopFrameworkTest {

	public static void main(String[] args) throws ClassNotFoundException {
		InputStream inStream = AopFrameworkTest.class.getResourceAsStream("config.properties");
		/*BeanFactory<ArrayList> beanFactory = new BeanFactory<ArrayList>(inStream);
		List list = beanFactory.getBean("ArrayList");
		System.out.println(list.getClass().getName());
		list.add("zhangsan");*/
		
		BeanFactory<HashMap> beanFactory = new BeanFactory<HashMap>(inStream);
		Map map = beanFactory.getBean("hashMap");
		System.out.println(map.getClass().getName());
		map.put("name", "zhangsan");
		System.out.println(map.size());
	}

}
