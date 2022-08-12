package mvc.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateController extends AbstractController {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        render(request, response, removeSuffix(request.getPath()));
    }

   private String removeSuffix(String viewName) {
        return viewName.replace(".html", "");
   }
}
