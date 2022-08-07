package webserver.http.view;

import webserver.http.HttpResponse;

public interface View {
    void render(HttpResponse response) throws Exception;
}
