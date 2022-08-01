package webserver.http.config;

import webserver.http.domain.controller.Controller;
import webserver.http.domain.controller.RequestProcessor;
import webserver.http.domain.controller.RootController;
import webserver.http.domain.controller.StaticResourceController;
import webserver.http.view.request.RequestReader;
import webserver.http.view.response.ResponseWriter;

import java.util.ArrayList;
import java.util.List;

public class WebServerConfig {
    public static RequestReader requestReader() {
        return new RequestReader();
    }

    public static ResponseWriter responseWriter() {
        return new ResponseWriter();
    }

    public static RequestProcessor requestProcessor(List<Controller> controllers) {
        List<Controller> totalControllers = new ArrayList<>(List.of(
                new RootController(),
                new StaticResourceController()
        ));

        totalControllers.addAll(controllers);

        return new RequestProcessor(totalControllers);
    }
}
