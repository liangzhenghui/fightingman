
/**
 * 
 * @author 作者
 * @date 制作日期
 * @说明：该测试用例用于说明 测试用例的基本结构
 */
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;

/**
 * 上下文环境准备 
 * ContextConfiguration
 *是否必录： 可选 
 *作用： 为该测试用例创建上下文 
 *参数：
 * locations		将配置文件载入上下文环境
 * 
 * inheritLocations 是否继承父类的上下文环境 默认为true 若设置为false 将仅使用locations里的配置文件创建上下文
 * 
 * loader 			使用指定上下文加载器加载配置文件 XbeanContextLoader.class 可以读取上下文配置文件中的自定义标签
 */
/*@ContextConfiguration(locations = {
		"classpath:spring/applicationContext-form.xml",
		"classpath:spring/applicationContext-form-ums.xml",
		"classpath:spring/applicationContext-service-business.xml" })
@RunWith(SpringJUnit4ClassRunner.class)*/
/**
 * 若该测试用例 在测试过程中 无需使用数据源 则不用继承任何类 若需使用开发库数据源 请继承 DBTestCaseInDevDb 若需使用测试库数据源
 * 请继承 DBTestCaseInUtDb
 */
public class JunitTest { // 单元测试用例命名规范 被测类名+Test
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
	public void testGreet() {
		//Assert.assertEquals("OK", hello.greet()); // 通过断言判断 方法是否通过测试
	}
}
