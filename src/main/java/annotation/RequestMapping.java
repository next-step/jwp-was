package annotation;

import model.http.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //TODO: ElementType.TYPE 지원
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    HttpMethod method() default HttpMethod.GET;
    String path() default "";
}
