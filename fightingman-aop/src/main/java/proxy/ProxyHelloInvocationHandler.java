/**
 * 2018年8月29日
 * liangzhenghui
 */
package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.log4j.Logger;

public class ProxyHelloInvocationHandler implements InvocationHandler {
	private static Logger LOGGER = Logger.getLogger(ProxyHelloInvocationHandler.class);
	private Object target;
	
	public ProxyHelloInvocationHandler( ) {
	}
	
	public ProxyHelloInvocationHandler(Object target) {
		this.target = target;
	}

	public Object bind(Object target) {
		this.target = target;
		Class [] test  = this.target.getClass().getInterfaces();
		return Proxy.newProxyInstance(this.target.getClass().getClassLoader(), this.target.getClass().getInterfaces(), this); 
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;
		MyLogger.start();// 添加额外的方法
		// 通过反射机制来运行目标对象的方法
		LOGGER.info("invoke "+method.getName());
		result = method.invoke(this.target, args);
		MyLogger.end();
		return result;
	}

}
