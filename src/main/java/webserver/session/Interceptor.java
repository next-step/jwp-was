package webserver.session;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Interceptor {

    void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
