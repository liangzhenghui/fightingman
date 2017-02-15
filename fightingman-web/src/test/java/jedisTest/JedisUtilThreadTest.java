package jedisTest;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;
import util.JedisUtil;

public class JedisUtilThreadTest {

	public static void main(String[] args) {
		new MyThread1().start();
		new MyThread1().start();
		new MyThread1().start(); 
		new MyThread1().start();
		//MyThread1().start(); new MyThread1().start(); new
		//MyThread1().start(); new MyThread1().start(); new
		//MyThread1().start(); new MyThread1().start();
	}
}

class MyThread1 extends Thread {
	private Jedis jedis;

	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		while (true) {

			try {
				jedis = JedisUtil.getJedis();
				// jedis.set(threadName, "0");
				int stock = Integer.parseInt(jedis.get("mykey"));
				System.out.println("还有库存=" + stock);
				if (stock > 0) {
					String storeStr = jedis.get(threadName);
					System.out.println(threadName + "有库存" + storeStr);
					jedis.watch("mykey");
					Transaction transaction = jedis.multi();
					transaction.set("mykey", String.valueOf(stock - 1));
					if (StringUtils.isBlank(storeStr)) {
						storeStr = "0";
					}
					int store = Integer.parseInt(storeStr);
					storeStr = String.valueOf((store + 1));
					transaction.set(threadName, storeStr);
					System.out.println(threadName + "有库存" + storeStr);
					List<Object> result = transaction.exec();
					if (result == null || result.isEmpty()) {
						System.out.println("Transaction error..." + threadName);// 可能是watch-key被外部修改，或者是数据操作被驳回
					}
				} else {
					System.out.println(
							Thread.currentThread().getName() + "抢到" + jedis.get(Thread.currentThread().getName()));
					System.out.println(Thread.currentThread().getName() + "访问库存，发现为0结束访问");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				JedisUtil.returnJedis(jedis);
			} finally {
				JedisUtil.returnJedis(jedis);
			}
		}

	}
}
