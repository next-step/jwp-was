package webserver.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import webserver.domain.ContentType;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatus;
import webserver.domain.Path;
import webserver.domain.RequestLine;
import webserver.domain.RequestMapping;
import webserver.domain.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import static webserver.domain.HttpHeaders.CONTENT_TYPE;

public interface Controller {
    ObjectMapper objectMapper = new ObjectMapper();

    default HttpResponse execute(HttpRequest httpRequest){
        try {
            Method method = getExecutableMethod(httpRequest.getRequestLine());
            Object result = method.invoke(this, httpRequest);

            if (method.getDeclaredAnnotation(ResponseBody.class) != null) {
                HttpResponse response = new HttpResponse(HttpStatus.OK, null, objectMapper.writeValueAsString(result));
                response.addHeader(CONTENT_TYPE, ContentType.JSON.getContentType());

                return response;
            }

            return (HttpResponse) result;
        } catch (IllegalAccessException | InvocationTargetException | JsonProcessingException e) {
            e.printStackTrace();
            return new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return new HttpResponse(HttpStatus.NOT_FOUND, null, e.getMessage());
        }
    }

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

        boolean matchPath = new Path(annotation.value()).containsPath(requestLine.getPath());

        return matchPath && matchMethod;
    }
}
