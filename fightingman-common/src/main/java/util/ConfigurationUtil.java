package util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
/**
 * 获取配置文件config属性值
 * @author liangzhenghui
 *
 */
public class ConfigurationUtil {
	public static Properties getConfiguration(){
		org.springframework.core.io.Resource resource= new ClassPathResource("config.properties");
		Properties props=null;
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return props;
	}
}
