package jedisTest;

import java.util.List;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import util.JedisUtil;

public class MyRunnable implements Runnable {

	String watchkeys = "watchkeys";// 监视keys
	private Jedis jedis;

	public MyRunnable() {
	}

	@Override
	public void run() {
		try {
			jedis=JedisUtil.getJedis();
            String val = jedis.get(watchkeys);
            int valint = Integer.valueOf(val);
            String userifo = UUID.randomUUID().toString();
            if (valint > 0) {
            	jedis.watch(watchkeys);// watchkeys
                Transaction tx = jedis.multi();// 开启事务
                tx.set(watchkeys, String.valueOf(valint - 1));
                List<Object> list = tx.exec();// 提交事务，如果此时watchkeys被改动了，则返回null'
                System.out.println(list);
                if (list != null) {
                    System.out.println("用户：" + userifo + "抢购成功，当前抢购成功人数:"
                            + (valint + 1));
                    /* 抢购成功业务逻辑 */
                    jedis.sadd("setsucc", userifo);
                } else {
                    System.out.println("用户：" + userifo + "抢购失败");
                    /* 抢购失败业务逻辑 */
                    jedis.sadd("setfail", userifo);
                }

            } else {
                System.out.println("用户：" + userifo + "抢购失败");
                jedis.sadd("setfail", userifo);
                // Thread.sleep(500);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

    }

	}

