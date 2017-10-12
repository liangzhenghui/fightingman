package redisson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
	public static void main(String[] args) throws InterruptedException {
		RedissonManager.init("", "");
		ExecutorService threadPool = Executors.newCachedThreadPool();// 线程池里面的线程数会动态变化，并可在线程线被移除前重用
		for (int i = 1; i <= 100; i++) {
			final int task = i; // 10个任务
			//TimeUnit.SECONDS.sleep(1);
			threadPool.execute(new Runnable() { // 接受一个Runnable实例
				public void run() {
					String key = "test123";
					DistributedRedisLock.acquire(key);
					Long result = RedissonManager.nextID();
					DistributedRedisLock.release(key);
					System.out.println("线程名字： " + Thread.currentThread().getName() + " 结果为" + result);
				}
			});
		}
	}
}