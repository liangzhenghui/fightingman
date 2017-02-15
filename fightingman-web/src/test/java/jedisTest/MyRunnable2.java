package jedisTest;

import java.util.List;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import util.JedisUtil;

public class MyRunnable2 implements Runnable {

	String watchkeys = "watchkeys";// 监视keys
	private Jedis jedis;

	public MyRunnable2() {
	}

	@Override
	public void run() {
		while(true){
			try {
				
				//System.out.println(Thread.currentThread().getName());
				jedis = JedisUtil.getJedis();
				//watch写在这里很关键
				jedis.watch(watchkeys);
				int stock = Integer.parseInt(jedis.get(watchkeys));
				String userInfo=UUID.randomUUID().toString();
				if (stock > 0) {
					//watch不可以写在这里
					Transaction transaction = jedis.multi();
					transaction.set(watchkeys, String.valueOf(stock - 1));
					List<Object> result = transaction.exec();
					if (result == null || result.isEmpty()) {
						System.out.println("Transaction error...");// 可能是watch-key被外部修改，或者是数据操作被驳回
					}else{
						System.out.println(stock);
						System.out.println(""+Thread.currentThread().getName()+userInfo+"购买成功");
						jedis.sadd("userInfo", userInfo);
					}
				} else {
					//System.out.println("库存为0");
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				JedisUtil.returnJedis(jedis);
			}
		}

	}

}
