package webserver.ui;

import webserver.domain.HttpRequest;
import webserver.domain.Path;
import webserver.domain.RequestLine;
import webserver.domain.RequestMapping;
import webserver.domain.Response;

import java.util.Arrays;

public interface Controller {
    Response execute(HttpRequest httpRequest);

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
