package webserver.ui;

import webserver.domain.HttpRequest;
import webserver.domain.Path;
import webserver.domain.RequestLine;
import webserver.domain.RequestMapping;
import webserver.domain.ResponseEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public interface Controller {

    /**
     * 요청 정보에 적합한 메서드를 찾아 실행 후 결과를 반환한다.
     *
     * @param httpRequest 요청 정보
     * @return 응답 정보
     */
    default ResponseEntity<?> execute(HttpRequest httpRequest) {
        try {
            Method method = getExecutableMethod(httpRequest.getRequestLine());

            return (ResponseEntity<?>) method.invoke(this, httpRequest);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().body(e.getMessage());
        }
    }

    /**
     * 요청 라인을 지원하는 메서드를 찾아 반환한다.
     *
     * @param requestLine 요청 라인
     * @return 메서드
     * @throws NoSuchMethodException 요청 라인을 지원하는 메서드를 찾지 못한 경우 예외를 던진다.
     */
    default Method getExecutableMethod(RequestLine requestLine) throws NoSuchMethodException {
        return Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method -> {
                    RequestMapping annotation = method.getDeclaredAnnotation(RequestMapping.class);
                    return Objects.nonNull(annotation)
                            && supportMethod(method.getDeclaredAnnotation(RequestMapping.class), requestLine);
                }).findFirst()
                .orElseThrow(NoSuchMethodException::new);
    }

    /**
     * 요청 라인을 지원하는지 여부를 반환한다.
     *
     * @param requestLine 요청 라인
     * @return 지원 여부
     */
    default boolean support(RequestLine requestLine) {
        return Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method -> method.getDeclaredAnnotation(RequestMapping.class) != null)
                .anyMatch(method -> supportMethod(method.getDeclaredAnnotation(RequestMapping.class), requestLine));
    }

    private boolean supportMethod(RequestMapping annotation, RequestLine requestLine) {
        boolean matchMethod = Arrays.stream(annotation.method())
                .anyMatch(method -> method.equals(requestLine.getMethod()));

        boolean matchPath = new Path(annotation.value()).containsPath(requestLine.getPath());

        return matchPath && matchMethod;
    }
}
