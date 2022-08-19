package mvc.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        String path = request.getPath();
        logger.debug("request path: {}", path);
        render(request, response, removeSuffix(request.getPath()));
    }

   private String removeSuffix(String viewName) {
        return viewName.replace(".html", "");
   }
}
