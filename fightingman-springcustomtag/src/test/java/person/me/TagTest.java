package person.me;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import person.me.model.User;

public class TagTest {

    @Test
    public void test(){
        ApplicationContext beans=new ClassPathXmlApplicationContext("classpath:application-customtag.xml");
        User user=(User)beans.getBean("id");
        System.out.println(user);
    }
}