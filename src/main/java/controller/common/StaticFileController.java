package controller.common;

import controller.Controller;
import webserver.http.Url;
import webserver.http.request.HttpRequest;
import webserver.http.response.ContentType;
import webserver.http.response.HttpResponse;

public class StaticFileController implements Controller {

    private static final String STATIC_PATH = "./static";

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        Url url = httpRequest.getRequestLine().getUrl();
        ContentType contentType = ContentType.from(url.getFileExtension());

        return HttpResponse.getStaticFile(contentType, addStaticPath(url.getPath()));
    }

    public static boolean checkIsExistFile(String path) {
        ClassLoader classLoader = StaticFileController.class.getClassLoader();
        return classLoader.getResource(addStaticPath(path)) != null;
    }

    private static String addStaticPath(String path) {
        return STATIC_PATH + path;
    }
}
