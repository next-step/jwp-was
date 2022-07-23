package webserver.ui;

import webserver.domain.HttpRequest;
import webserver.domain.RequestLine;
import webserver.domain.Response;

import java.util.ArrayList;
import java.util.List;

public class FrontController {
    private final List<Controller> controllers = new ArrayList<>();

    public static FrontController newInstance() {
        return new FrontController();
    }

    public boolean support(RequestLine requestLine){
        return controllers.stream()
                .anyMatch(controller -> controller.support(requestLine));
    }

    public Response execute(HttpRequest httpRequest) {
        Controller controller = find(httpRequest.getRequestLine());

        return controller.execute(httpRequest);
    }

    private Controller find(RequestLine requestLine) {
        return controllers.stream()
                .filter(controller -> controller.support(requestLine))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public FrontController addController(Controller controller) {
        if (!controllers.contains(controller)) {
            controllers.add(controller);
        }

        return this;
    }
}
