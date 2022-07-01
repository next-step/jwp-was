package mvc.controller;

import was.http.HttpRequest;
import was.http.HttpResponse;

public class ForwardController extends AbstractController {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        String path = getDefaultPath(request.getPath());
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
