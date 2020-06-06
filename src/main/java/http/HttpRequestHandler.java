package http;

import http.response.StaticResourceHttpResponse;
import java.io.File;

public class HttpRequestHandler {

    public static HttpResponse handle(HttpRequest httpRequest) {
        String filePath = "./templates" + httpRequest.getPath();
        return new StaticResourceHttpResponse(new File(filePath));
    }
}
