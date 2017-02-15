package redisTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {
		"classpath:spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedisTemplate {
	
	  @Resource(name="redisTemplate")
	  private ListOperations<String, String> listOps;
	  
	  @Resource
	  private RedisTemplate<String, String> redisTemplate;

     @Before
     public void setUp() {
    	 //redisTemplate.setKeySerializer(new StringRedisSerializer());
    	
      //   jedis.auth("password");
     }

	//@Test
	public void testOperationalViews(){
		addLink("test26666", "1");
		//addLink("test26666", "2");
		//addLink("test26666", "3");
		list();
	}
	@Test
	public void testTrans() throws InterruptedException, ExecutionException{
		 //execute a transaction
		/*List<Object> txResults = redisTemplate.execute(new SessionCallback<List<Object>>() {
		    public List<Object> execute(RedisOperations operations) throws DataAccessException {
		    	operations.watch("www");
		        operations.multi();
		        try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        //operations.opsForValue().set("key", "value1111111");
		        operations.opsForValue().set("www", "11111111111111111111111");
		        // This will contain the results of all ops in the transaction

		        return operations.exec();
		    }
		});
		System.out.println("Number of items added to set: " + txResults);
		String test=redisTemplate.opsForValue().get("key");
		System.out.println("111111"+test);*/
		 final String key = "test-cas-1";
	        ValueOperations<String, String> strOps = redisTemplate.opsForValue();
	        strOps.set(key, "hello");
	        ExecutorService pool  = Executors.newCachedThreadPool();
	        List<Callable<Object>> tasks = new ArrayList<>();
	        for(int i=0;i<5;i++){
	            final int idx = i;
	            tasks.add(new Callable() {
	                @Override
	                public Object call() throws Exception {
	                   return redisTemplate.execute(new SessionCallback() {
	                        @Override
	                        public Object execute(RedisOperations operations) throws DataAccessException {
	                            operations.watch(key);
	                            try {
									Thread.sleep(6000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                            String origin = (String) operations.opsForValue().get(key);
	                            operations.multi();
	                            operations.opsForValue().set(key, origin + idx);
	                            Object rs = operations.exec();
	                            System.out.println("set:"+origin+idx+" rs:"+rs);
	                            return rs;
	                        }
	                    });
	                
	                }
	            });
	        }
	        List<Future<Object>> futures = pool.invokeAll(tasks);
	        for(Future<Object> f:futures){
	            System.out.println(f.get());
	        }
	        pool.shutdown();
	        pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
	}
	
	 public void addLink(String userId, String value) {
	    listOps.leftPush(userId, value);
	  }
	 public void list(){
		  List<String> list =listOps.range("test26666", 0, -1);
	    for(String test:list){
	    	System.out.println(test);
	    }
	 }

}

