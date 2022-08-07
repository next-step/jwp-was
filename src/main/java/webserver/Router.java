package webserver;

import controller.Controller;
import controller.common.StaticFileController;
import controller.common.TemplateFileController;
import controller.user.LogInController;
import controller.user.SignUpController;
import controller.user.UserListController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class Router {

    private static Map<String, Controller> requestMappings = new HashMap<>();
    private static StaticFileController staticFileController = new StaticFileController();
    private static TemplateFileController templateFileController = new TemplateFileController();

    static {
        requestMappings.put(LogInController.URL, new LogInController());
        requestMappings.put(SignUpController.URL, new SignUpController());
        requestMappings.put(UserListController.URL, new UserListController());
    }

    public HttpResponse execute(HttpRequest httpRequest){
        String path = httpRequest.getRequestLine().getUrl().getPath();

        if (requestMappings.containsKey(path)) {
            return requestMappings.get(path).service(httpRequest);
        }
        if (StaticFileController.checkIsExistFile(path)) {
            return staticFileController.service(httpRequest);
        }
        if (TemplateFileController.checkIsExistFile(path)) {
            return templateFileController.service(httpRequest);
        }

        return HttpResponse.notFound();
    }
}
