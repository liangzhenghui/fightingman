package aop;

import java.lang.reflect.Method;

/**
 * aop接口，提供方法运行前、方法运行后、方法运行中产生Exception、方法最终运行代码
 *
 */
public interface Advice {
	
	/**
	 * 方法运行前
	 * @param target 被代理的目标对象
	 * @param method 被调用的方法
	 * @param args 方法的参数
	 */
	public void doBefore(Object target, Method method, Object[] args);
	
	/**
	 * 方法运行后
	 * @param target 被代理的目标对象
	 * @param method 被调用的方法对象
	 * @param args 方法的参数
	 * @param retVal 方法的返回值
	 */
	public void doAfter(Object target, Method method, Object[] args, Object retVal);
	
	/**
	 * 方法运行时产生的异常
	 * @param target 被代理的目标对象
	 * @param method 被调用的方法
	 * @param args 方法参数
	 * @param e 运行时的异常对象
	 */
	public void doThrow(Object target, Method method, Object[] args, Exception e);
	
	/**
	 * 最终要执行的功能(如释放数据库连接的资源、关闭IO流等)
	 * @param target 被代理的目标对象
	 * @param method 被调用的方法
	 * @param args 方法参数
	 */
	public void doFinally(Object target, Method method, Object[] args);
}
