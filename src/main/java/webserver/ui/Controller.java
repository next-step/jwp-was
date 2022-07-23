package webserver.ui;

import webserver.domain.Path;
import webserver.domain.RequestLine;
import webserver.domain.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public interface Controller {

    default Method getExecutableMethod(RequestLine requestLine) throws NoSuchMethodException {
        return Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method-> {
                    RequestMapping annotation = method.getDeclaredAnnotation(RequestMapping.class);
                    return Objects.nonNull(annotation)
                            && supportMethod(method.getDeclaredAnnotation(RequestMapping.class), requestLine);
                }).findFirst()
                .orElseThrow(NoSuchMethodException::new);
    }

    default boolean support(RequestLine requestLine){
        return Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method-> method.getDeclaredAnnotation(RequestMapping.class) != null)
                .anyMatch(method -> supportMethod(method.getDeclaredAnnotation(RequestMapping.class), requestLine));
    }

    private boolean supportMethod(RequestMapping annotation, RequestLine requestLine) {
        boolean matchMethod = Arrays.stream(annotation.method())
                .anyMatch(method -> method.equals(requestLine.getMethod()));

        boolean matchPath = Path.from(annotation.value()).containsPath(requestLine.getPath());

        return matchPath && matchMethod;
    }
}
