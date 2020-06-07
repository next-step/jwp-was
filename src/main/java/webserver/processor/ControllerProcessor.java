package webserver.processor;

import controller.AbstractController;
import controller.LoginController;
import controller.UserController;
import controller.UserListController;
import http.HttpRequest;
import http.response.HttpResponse;
import view.HandlebarEngine;
import view.View;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ControllerProcessor implements Processor {
    private static final View handleBarEngine = new HandlebarEngine();

    private static final List<? extends AbstractController> CONTROLLERS = Arrays.asList(
            new UserController(),
            new LoginController(),
            new UserListController()
    );

    private static final Map<String, ? extends AbstractController> PATH_AND_CONTROLLER =
            CONTROLLERS.stream()
                    .collect(Collectors.toMap(AbstractController::getPath, Function.identity()));

    @Override
    public boolean isMatch(final HttpRequest httpRequest) {
        return PATH_AND_CONTROLLER.containsKey(httpRequest.getPath());
    }

    @Override
    public void process(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        AbstractController controller = PATH_AND_CONTROLLER.get(httpRequest.getPath());

        controller.process(httpRequest, httpResponse);

        render(httpRequest, httpResponse);
    }

    private void render(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        if (httpResponse.isForward()) {
            handleBarEngine.render(httpRequest, httpResponse);
        }
    }
}
