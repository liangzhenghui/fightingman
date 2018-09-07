package proxy;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
/**
 * 记录接口访问日记
 * @author liangzhenghui
 *
 */
public class LogInterceptor {
	private static final Logger logger = Logger.getLogger(LogInterceptor.class);
	
    public void before(){ 
    	logger.info("before");
    }  
      
    public void after(JoinPoint joinPoint){  
    	logger.info("after");
    }  
}
