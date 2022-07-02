package webserver;

import controller.*;
import webserver.http.HttpRequest;
import webserver.http.RequestController;
import webserver.http.RequestMappingControllerAdapter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class RequestControllerContainer {

    private RequestControllerContainer() {
    }

    private static final List<RequestMappingControllerAdapter> container = new LinkedList<>();

    private static RequestController staticController = new StaticResourceController();

    static {
        container.add(new IndexMappingController());
        container.add(new CreatUserMappingController());
        container.add(new LoginMappingController());
        container.add(new UserListMappingController());
    }

    public static RequestController match(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String url = httpRequest.getPath();

        Optional<RequestMappingControllerAdapter> optionalController = container.stream()
                .filter(controller -> controller.checkUrl(url))
                .findAny();

        if (optionalController.isPresent()) {
            return optionalController.get();
        }

        return staticController;
    }
}
