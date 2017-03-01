package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactoryBean {
	
	/**
	 * 被代理的目标对象
	 */
	private Object target;
	
	/**
	 * 目标对象要插入的业务逻辑
	 */
	private Advice advice;

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public Advice getAdvice() {
		return advice;
	}

	public void setAdvice(Advice advice) {
		this.advice = advice;
	}

	/**
	 * 获得一个代理类对象
	 * loader - the class loader to define the proxy class
		interfaces - the list of interfaces for the proxy class to implement
		h - the invocation handler to dispatch method invocations to 

	 * @return
	 */
	public Object getProxy() {
		return Proxy.newProxyInstance(
				target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(),
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						Object retVal = null;
						try {
							advice.doBefore(target, method, args);
							retVal = method.invoke(target, args);
							advice.doAfter(target, method, args, retVal);
						} catch (Exception e) {
							advice.doThrow(target, method, args, e);
						} finally {
							advice.doFinally(target, method, args);
						}
						return retVal;
					}}
				);
	}
}