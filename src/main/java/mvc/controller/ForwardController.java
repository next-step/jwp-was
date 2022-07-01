package mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.http.HttpRequest;
import was.http.HttpResponse;

public class ForwardController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(ForwardController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        String path = getDefaultPath(request.getPath());
        logger.debug("request path: {}", path);
        render(request, response, removeSuffix(path));
    }

    private String getDefaultPath(String path) {
        if (path.equals("/")) {
            return "/index.html";
        }
        return path;
    }

    private String removeSuffix(String viewName) {
        return viewName.replace(".html", "");
    }
}
