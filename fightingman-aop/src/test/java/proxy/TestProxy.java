/**
 * 2018年8月30日
 * liangzhenghui
 */
package proxy;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
/**
 * proxy详解
 * https://docs.oracle.com/javase/7/docs/api/java/lang/reflect/Proxy.html
 *  @date   2018年8月30日
 *	@author liangzhenghui
 */
public class TestProxy {
	@Test
	public void TestProxy() {
		Map proxyInstance = (Map) Proxy.newProxyInstance(
				HashMap.class.getClassLoader(), 
				  new Class[] { Map.class }, 
				  new ProxyHelloInvocationHandler(new HashMap()));
		proxyInstance.put("key", "123");
		System.out.println("result is "+proxyInstance.get("key"));
	}
	
	//@Test
	public void TestTeacherProxy() {
		ITeacher proxyInstance = (ITeacher) Proxy.newProxyInstance(
				ITeacher.class.getClassLoader(), 
				  new Class[] {ITeacher.class}, 
				  new ProxyHelloInvocationHandler(new Teacher()));
		proxyInstance.teach();
	}
	//@Test
	public void TestHelloProxy() {
		IHello hello = (IHello) new ProxyHelloInvocationHandler().bind(new Hello());//如果我们需要日志功能，则使用代理类
		//IHello hello = new Hello();//如果我们不需要日志功能则使用目标类
		hello.sayHello("hello~~");
	}
}
