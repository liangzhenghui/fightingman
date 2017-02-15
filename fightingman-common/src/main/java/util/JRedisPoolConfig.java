package util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class JRedisPoolConfig {
	protected static Logger log = Logger.getLogger(JRedisPoolConfig.class);
	public static String REDIS_IP;
	public static int REDIS_PORT;
	public static String REDIS_PASSWORD;
	public static int MAX_ACTIVE;
	public static int MAX_IDLE;
	public static long MAX_WAIT;
	public static boolean TEST_ON_BORROW;
	public static boolean TEST_ON_RETURN;

	static {
		init();
	}

	public static void init() {
		Resource resource = new ClassPathResource("redis.properties");
		Properties props;
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
			REDIS_IP = props.get("redis.ip").toString();
			REDIS_PORT = Integer.parseInt(props.get("redis.port").toString());
			//REDIS_PASSWORD = props.get("redis.password").toString();
			MAX_ACTIVE = Integer.parseInt(props.get("redis.pool.maxActive").toString());
			MAX_IDLE = Integer.parseInt(props.get("redis.pool.maxIdle").toString());
			MAX_WAIT = Integer.parseInt(props.get("redis.pool.maxWait").toString());
			TEST_ON_BORROW = Boolean.parseBoolean(props.get("redis.pool.testOnBorrow").toString());
			TEST_ON_RETURN = Boolean.parseBoolean(props.get("redis.pool.testOnReturn").toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
