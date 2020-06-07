package web;

import org.junit.jupiter.api.Test;
import web.servlet.ViewResolver;
import web.servlet.ViewResolverComposite;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class DefaultHttpRequestHandlerTest {

    @Test
    public void initTest() {
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping(new LinkedHashMap<>());
        ViewResolver viewResolver = new ViewResolverComposite(new LinkedHashSet<>());

        DefaultHttpRequestHandler httpRequestHandler = new DefaultHttpRequestHandler(annotationHandlerMapping, viewResolver);
    }
}
