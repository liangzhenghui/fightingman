/**
 * 2018年8月31日
 * liangzhenghui
 */
package proxy;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LogService {
	private static final Logger logger = Logger.getLogger(LogService.class);

	public void whoIam() {
		logger.info("whoIam");
	}

	public void whereIam() {
		logger.info("whereIam");
	}
}
