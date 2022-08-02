package webserver.view;

import webserver.http.HttpResponse;

import java.util.Map;

public interface View {

    void render(Map<String, ?> models, HttpResponse httpResponse);

}
