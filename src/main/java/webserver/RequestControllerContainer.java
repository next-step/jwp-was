package webserver;

import controller.*;
import http.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class RequestControllerContainer {

    private RequestControllerContainer() {
    }

    private static final List<RequestMappingControllerAdapter> container = new LinkedList<>();

    static {
        container.add(new IndexMappingController());
        container.add(new CreatUserMappingController());
        container.add(new LoginMappingController());
        container.add(new UserListMappingController());
    }

    public static HttpResponse match(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String url = httpRequest.getRequestLine().getPath();

        Optional<RequestMappingControllerAdapter> optionalController = container.stream()
                .filter(controller -> controller.checkUrl(url))
                .findAny();

        if (optionalController.isPresent()) {
            return execute(httpRequest, optionalController.get());
        }

        try {
            return new StaticResourceController().doGet(httpRequest);
        } catch (IllegalArgumentException iae) {
            return new HttpResponse(HttpStatus.NOT_FOUND, MediaType.TEXT_HTML_UTF8, "", null);
        }
    }

    private static HttpResponse execute(HttpRequest httpRequest, RequestController controller) throws IOException, URISyntaxException {
        RequestMethod method = httpRequest.getRequestLine().getMethod();

        if (method == RequestMethod.POST) {
            return controller.doPost(httpRequest);
        }

        return controller.doGet(httpRequest);
    }
}
