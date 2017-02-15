
/**
 * 
 * @author 作者
 * @date 制作日期
 * @说明：该测试用例用于说明 测试用例的基本结构
 */
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import model.Person;
import util.MapToBeanUtil;

/**
 * 若该测试用例 在测试过程中 无需使用数据源 则不用继承任何类 若需使用开发库数据源 请继承 DBTestCaseInDevDb 若需使用测试库数据源
 * 请继承 DBTestCaseInUtDb
 */
public class TestMapToBean { // 单元测试用例命名规范 被测类名+Test
	/****************测试数据初始化****************/
	//HelloJunit hello = new HelloJunit(); // 无需注入的类 可以在声明时直接初始化
	//FormFactory formFactory; // 对于需要反转注入的类 则必须在before标签下的setUp方法里完成 初始化
	

	@Before
	// before 标签下的方法 将在测试方法运行之前运行 用于测试数据的初始化
	public void setUp() {
		String str = "OK";
		//hello.setMessage(str);
	}
	/****************初始化结束****************/

	@Test
	// 测试方法 注意命名规则 test+被测方法名
	public void test() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("NAME","你好");
		map.put("AGE", "10");
		map.put("TIME", new Date());
		Person person=MapToBeanUtil.mapToBean(map, Person.class);
		System.out.println(person.getName());
		System.out.println(person.getAge());
		System.out.println(person.getTime());
	}
}
