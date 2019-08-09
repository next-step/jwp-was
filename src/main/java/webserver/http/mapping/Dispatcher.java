package webserver.http.mapping;

import webserver.http.controller.Controller;
import webserver.http.request.Request;
import webserver.http.response.Response;

public class Dispatcher {
    private HandlerMapping handlerMapping;

    public Dispatcher() {
        this.handlerMapping = new HandlerMapping();
    }

    public void dispatch(Request request, Response response) {

        Controller controller = getController(request);
        controller.service(request, response);
    }

    private Controller getController(Request request) {
        return handlerMapping.getController(request);
    }
}
