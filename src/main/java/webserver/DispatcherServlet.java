package webserver;

import controller.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DispatcherServlet {

    private DispatcherServlet() {
    }

    private static final List<RequestMappingControllerAdapter> container = new LinkedList<>();

    static {
        container.add(new IndexMappingController());
        container.add(new CreatUserMappingController());
        container.add(new LoginMappingController());
        container.add(new UserListMappingController());
    }

    public static Response match(Request request) throws IOException, URISyntaxException {
        String url = request.getRequestLine().getPath();

        Optional<RequestMappingControllerAdapter> optionalController = container.stream()
                .filter(controller -> controller.checkUrl(url))
                .findAny();

        if (optionalController.isPresent()) {
            return execute(request, optionalController.get());
        }

        try {
            return new StaticResourceController().doGet(request);
        } catch (IllegalArgumentException iae) {
            return new Response(HttpStatus.NOT_FOUND, MediaType.TEXT_HTML_UTF8, "", null);
        }
    }

    private static Response execute(Request request, RequestController controller) throws IOException, URISyntaxException {
        HttpMethod method = request.getRequestLine().getMethod();

        if (method == HttpMethod.POST) {
            return controller.doPost(request);
        }

        return controller.doGet(request);
    }
}
