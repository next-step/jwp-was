package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //TODO: ElementType.TYPE 지원
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String path() default "";
}
