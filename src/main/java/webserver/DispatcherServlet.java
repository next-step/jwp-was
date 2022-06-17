package webserver;

import controller.Controller;
import controller.CreatUserController;
import controller.StaticResourceController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet {

    private DispatcherServlet() {
    }

    private static final Map<String, Controller> mapper = new HashMap<>();

    static {
        mapper.put("/user/create", new CreatUserController());
    }

    public static Response match(Request request) throws IOException, URISyntaxException {
        String path = request.getRequestLine()
                .getPath();

        if (mapper.containsKey(path)) {
            return mapper.get(path)
                    .serving(request);
        }

        return new StaticResourceController()
                .serving(request);
    }
}
