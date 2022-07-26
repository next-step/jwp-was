package webserver.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.ContentType;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatus;
import webserver.domain.RequestLine;
import webserver.domain.ResponseBody;
import webserver.ui.Controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static webserver.domain.HttpHeaders.CONTENT_TYPE;

public class ControllerContainerImpl implements ControllerContainer{
    private static final Logger logger = LoggerFactory.getLogger(ControllerContainerImpl.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final List<Controller> controllers = new ArrayList<>();

    public static ControllerContainerImpl newInstance() {
        return new ControllerContainerImpl();
    }

    @Override
    public boolean support(RequestLine requestLine) {
        return controllers.stream()
                .anyMatch(controller -> controller.support(requestLine));
    }

    @Override
    public Controller findController(RequestLine requestLine) {
        return controllers.stream()
                .filter(controller -> controller.support(requestLine))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public HttpResponse execute(HttpRequest httpRequest) {
        Controller controller = findController(httpRequest.getRequestLine());

        try {
            Method method = controller.getExecutableMethod(httpRequest.getRequestLine());
            Object result = method.invoke(controller, httpRequest);

            if (method.getDeclaredAnnotation(ResponseBody.class) != null) {
                HttpResponse response = new HttpResponse(HttpStatus.OK, null, objectMapper.writeValueAsString(result));
                response.addHeader(CONTENT_TYPE, ContentType.JSON.getContentType());

                return response;
            }

            return (HttpResponse) result;
        } catch (IllegalAccessException | InvocationTargetException | JsonProcessingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new HttpResponse(HttpStatus.NOT_FOUND, null, e.getMessage());
        }
    }

    @Override
    public ControllerContainerImpl addController(Controller controller) {
        if (!controllers.contains(controller)) {
            controllers.add(controller);
        }

        return this;
    }
}
