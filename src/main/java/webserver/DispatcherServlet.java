package webserver;

import controller.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet {

    private DispatcherServlet() {
    }

    private static final Map<String, Controller> mapper = new HashMap<>();

    static {
        mapper.put("GET /", new IndexController());
        mapper.put("POST /user/create", new CreatUserController());
        mapper.put("GET /user/login", new LoginController());
        mapper.put("POST /user/login", new LoginController());
        mapper.put("GET /user/list.html", new UserListController());
    }

    public static Response match(Request request) throws IOException, URISyntaxException {

        String path = toPath(request);

        if (mapper.containsKey(path)) {
            return mapper.get(path)
                    .serving(request);
        }

        try {
            return new StaticResourceController()
                    .serving(request);
        } catch (IllegalArgumentException iae) {
            return new Response(HttpStatus.NOT_FOUND, "", "", null);
        }

    }

    private static String toPath(Request request) {
        return request.getRequestLine().getMethod() + " " + request.getRequestLine()
                .getPath();
    }
}
