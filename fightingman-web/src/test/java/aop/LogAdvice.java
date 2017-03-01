package aop;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 日志功能切入类
 * @author
 *
 */
public class LogAdvice implements Advice {

	long beginTime = System.currentTimeMillis();
	
	@Override
	public void doBefore(Object target, Method method, Object[] args) {
		System.out.println(target.getClass().getSimpleName() +
				"." + method.getName() + "方法被调用，参数值：" + Arrays.toString(args));

	}
	
	@Override
	public void doAfter(Object target, Method method, Object[] args, Object retVal) {
		long endTime = System.currentTimeMillis();
		System.out.println(target.getClass().getSimpleName() +
				"." + method.getName() + "方法运行结束，返回值：" + retVal + "，耗时" + (endTime - beginTime) + "毫秒。");

	}

	@Override
	public void doThrow(Object target, Method method, Object[] args,
			Exception e) {
		System.out.println("调用" + target.getClass().getSimpleName() +
				"." + method.getName() + "方法发生异常，异常消息：");
		e.printStackTrace();
	}

	@Override
	public void doFinally(Object target, Method method, Object[] args) {
		System.out.println("doFinally...");
		
	}

}