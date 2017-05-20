package config;
import java.io.IOException;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;


public class TestConfig {
     @Before
     public void setUp() {
    	
      //   jedis.auth("password");
     }

	@Test
	public void testConfig() throws ParseException, IOException{
		Resource resource = new ClassPathResource("config.properties");
		try {
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			System.out.println(props.get("jdbc.password").toString().length());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

