package jedisTest;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import util.JedisUtil;

public class JedisUtilTest {
	private static Logger logger=Logger.getLogger(JedisUtilTest.class);
	
     public static Jedis jedis;
     JedisPool pool;
     @Before
     public void setUp() {
    	 
     }

	//@Test
	public void testGet(){
		JedisUtil.set("hello", "helloworld");
		System.out.println(JedisUtil.getStr("hello"));
	}
	/*@Test
	 * junit不支持多线程
	public void testThread(){
		new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
	}*/
	
}
