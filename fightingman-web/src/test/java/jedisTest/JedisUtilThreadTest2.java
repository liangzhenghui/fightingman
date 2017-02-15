package jedisTest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;
import util.JedisUtil;

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
