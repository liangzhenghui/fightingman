package redisTemplate;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class Example {
	 // inject the actual template 
	  @Autowired
	  private RedisTemplate<String, String> template;

	  // inject the template as ListOperations
	  @Resource(name="redisTemplate")
	  private ListOperations<String, String> listOps;

	  public void addLink(String userId, String value) {
	    listOps.leftPush(userId, value);
	  }
}
