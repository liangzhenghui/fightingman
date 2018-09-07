/**
 * 2018年8月31日
 * liangzhenghui
 */
package testspringaop;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import proxy.LogService;
@ContextConfiguration(locations = {
        "classpath:spring/fightingman-aop.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSpringAop {
	private static final Logger logger = Logger.getLogger(TestSpringAop.class);
	@Resource
	private LogService logService;
	@Test
	public void TestAop() {
		logger.info("here ");
		logService.whoIam();
	}

}
