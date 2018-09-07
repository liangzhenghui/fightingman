package person.me.handle;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import person.me.parser.UserBeanDefinitionParser;

public class MyUserNamespaceHandler extends NamespaceHandlerSupport{
	//org.springframework.beans.factory.parsing.BeanDefinitionParsingException: Configuration problem: Cannot locate BeanDefinitionParser for element [user]
	//Offending resource: class path resource [application-customtag.xml]
    @Override
    public void init() {
    	//测试的时候不能注释掉这句话不然会报错
        registerBeanDefinitionParser("user",new UserBeanDefinitionParser());
    }
}