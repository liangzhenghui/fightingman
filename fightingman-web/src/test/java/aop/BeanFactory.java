package aop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Bean工厂，负责产生代理类或目标类的实例
 */
public class BeanFactory<T> {
	
	private Properties props = new Properties();
	
	/**
	 * 初始化bean工厂，加载bean的配置文件，该配置文件是一个标准的Properties文件,该文件由Key=Value的格式组成
	 * @param inStream bean的配置文件
	 */
	public BeanFactory(InputStream inStream) {
		try {
			props.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取一个Bean的实例
	 * @param beanName bean javabean的名称
	 * @return 根据配置文件,返回目标类或代理类的实例
	 * @throws ClassNotFoundException
	 */
	public T getBean(String beanName) throws ClassNotFoundException {
		String className = props.getProperty(beanName);
		Class clazz = Class.forName(className);
		T bean = null;
		try {
			bean = (T)clazz.newInstance();
			if (bean instanceof ProxyFactoryBean) {
				ProxyFactoryBean proxyFactoryBean = (ProxyFactoryBean) bean;
				Advice advice = (Advice) Class.forName(props.getProperty(beanName + ".advice")).newInstance();
				Object target = Class.forName(props.getProperty(beanName + ".target")).newInstance();
				proxyFactoryBean.setAdvice(advice);
				proxyFactoryBean.setTarget(target);
				bean = (T)proxyFactoryBean.getProxy();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return bean;
	}
}