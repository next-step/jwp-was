package controller.common;


import controller.Controller;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class TemplateFileController implements Controller {

    private static final String TEMPLATE_PATH = "./templates";

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        String path = httpRequest.getRequestLine().getUrl().getPath();
        return HttpResponse.getView(addTemplatePath(path));
    }

    public static boolean checkIsExistFile(String path) {
        ClassLoader classLoader = StaticFileController.class.getClassLoader();
        return classLoader.getResource(addTemplatePath(path)) != null;
    }

    private static String addTemplatePath(String path) {
        return TEMPLATE_PATH + path;
    }
}
