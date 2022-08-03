package webserver.http.config;

import webserver.http.domain.controller.Controller;
import webserver.http.domain.controller.HttpRequestProcessor;
import webserver.http.domain.controller.RequestProcessor;
import webserver.http.domain.controller.RootController;
import webserver.http.domain.controller.SessionCompositeRequestProcessor;
import webserver.http.domain.controller.StaticResourceController;
import webserver.http.domain.session.SessionStorage;
import webserver.http.domain.session.UUIDSessionIdGenerator;
import webserver.http.view.request.RequestReader;
import webserver.http.view.response.ResponseWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class WebServerConfig {
    public static RequestReader requestReader() {
        return new RequestReader();
    }

    public static ResponseWriter responseWriter() {
        return new ResponseWriter();
    }

    public static RequestProcessor sessionCompositeRequestProcessor(List<Controller> controllers) {
        return new SessionCompositeRequestProcessor(
                httpRequestProcessor(controllers),
                sessionStorage()
        );
    }

    private static RequestProcessor httpRequestProcessor(List<Controller> controllers) {
        List<Controller> totalControllers = new ArrayList<>(List.of(
                new RootController(),
                new StaticResourceController()
        ));

        totalControllers.addAll(controllers);

        return new HttpRequestProcessor(totalControllers);
    }

    private static SessionStorage sessionStorage() {
        return new SessionStorage(
                new ConcurrentHashMap<>(),
                sessionIdGenerator()
        );
    }

    private static UUIDSessionIdGenerator sessionIdGenerator() {
        return new UUIDSessionIdGenerator();
    }
}
