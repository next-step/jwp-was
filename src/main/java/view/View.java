package view;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface View {
    void render(HttpRequest request, HttpResponse response, String location) throws Exception;
}
