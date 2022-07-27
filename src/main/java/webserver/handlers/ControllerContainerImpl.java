package webserver.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.RequestLine;
import webserver.ui.Controller;

import java.util.ArrayList;
import java.util.List;

public class ControllerContainerImpl implements ControllerContainer {
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

    @Override
    public ControllerContainerImpl addController(Controller controller) {
        if (!controllers.contains(controller)) {
            controllers.add(controller);
        }

        return this;
    }
}
