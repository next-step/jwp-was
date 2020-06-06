package handler;

import http.HttpResponse;
import http.request.HttpRequest;
import http.response.StaticResourceHttpResponse;
import java.io.File;

public class StaticResourceHandler {

    HttpResponse get(HttpRequest httpRequest) {
        String filePath = "./templates" + httpRequest.getPath();
        return new StaticResourceHttpResponse(new File(filePath));
    }
}
