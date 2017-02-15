package jedisTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;
import util.JedisUtil;
/**
 * 测试redis事务,秒杀系统demo
 * @author liangzhenghui
 *
 */
public class JedisUtilThreadTest2 {

	public static void main(String[] args) {
		final String watchkeys = "watchkeys";
        ExecutorService executor = Executors.newFixedThreadPool(5000);
        Jedis jedis=JedisUtil.getJedis();
        jedis.set(watchkeys, "1000");// 重置watchkeys为0
        jedis.del("userInfo");
        JedisUtil.returnJedis(jedis);
        for (int i = 0; i < 10000; i++) {// 测试一万人同时访问
            executor.execute(new MyRunnable2());
        }
        executor.shutdown();
	}
}
