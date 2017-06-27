package fileDirectory;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class MyTest {
	@Test
	public void create(){
		org.springframework.core.io.Resource resource= new ClassPathResource("config.properties");
		Properties props;
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
			String filePath = props.get("filepath").toString();
			File file=new File(filePath);
			if(!file.exists()){
				boolean result=file.mkdirs();
				System.out.println(result);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
