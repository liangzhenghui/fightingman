import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import model.Person;
import util.JsonUtil;
import util.MapToBeanUtil;

public class TestJsonToBean {
	@Test
	// 测试方法 注意命名规则 test+被测方法名
	public void test() {
		/*Map<String,Object> map = new HashMap<String,Object>();
		map.put("NAME","你好");
		map.put("AGE", "10");
		map.put("TIME", new Date());*/
		String personStr="{'name':'你好','age':'10','time':'2017-02-08'}";
		Person person=JsonUtil.json2Bean(personStr, Person.class);
		System.out.println(person.getName());
		System.out.println(person.getAge());
		System.out.println(person.getTime());
	}
}
