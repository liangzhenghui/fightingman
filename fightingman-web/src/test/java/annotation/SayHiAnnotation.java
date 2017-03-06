package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 表示注解在运行时依然存在
@Target(ElementType.METHOD) // 表示注解可以被使用于方法上
public @interface SayHiAnnotation {
    String paramValue() default "johness"; // 表示我的注解需要一个参数 名为"paramValue" 默认值为"johness"
}