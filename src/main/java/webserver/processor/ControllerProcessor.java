package webserver.processor;

import controller.Controller;
import controller.LoginController;
import controller.UserController;
import http.HttpRequest;
import http.HttpResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ControllerProcessor implements Processor {
    private static final List<? extends Controller> CONTROLLERS = Arrays.asList(
            new UserController(),
            new LoginController()
    );

    private static final Map<String, ? extends Controller> PATH_AND_CONTROLLER =
            CONTROLLERS.stream()
                    .collect(Collectors.toMap(Controller::getPath, Function.identity()));

    @Override
    public boolean isMatch(final HttpRequest httpRequest) {
        return PATH_AND_CONTROLLER.containsKey(httpRequest.getPath());
    }

    @Override
    public HttpResponse process(final HttpRequest httpRequest) {
        Controller controller = PATH_AND_CONTROLLER.get(httpRequest.getPath());

        return controller.process(httpRequest);
    }
}
