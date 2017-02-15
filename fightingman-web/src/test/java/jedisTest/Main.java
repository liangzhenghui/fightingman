package jedisTest;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class Main {

    public static void main(String[] args) {
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
    }
}

class MyThread extends Thread {
    Jedis jedis = null;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            System.out.println(Thread.currentThread().getName());
            jedis = new Jedis("localhost");
            try {
                int stock = Integer.parseInt(jedis.get("mykey"));
                System.out.println("还有库存="+stock);
                if (stock > 0) {
                    jedis.watch("mykey");
                    Transaction transaction = jedis.multi();
                    transaction.set("mykey", String.valueOf(stock - 1));
                    List<Object> result = transaction.exec();
                    if (result == null || result.isEmpty()) {
                        System.out.println("Transaction error...");// 可能是watch-key被外部修改，或者是数据操作被驳回
                    }
                } else {
                    System.out.println("库存为0");
                    break;
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                jedis.disconnect();
               // RedisUtil.returnResource(jedis);
            }finally{
                //RedisUtil.returnResource(jedis);
            	jedis.disconnect();
            }

        }
    }

}