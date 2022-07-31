package webserver.view;

import webserver.http.Request;
import webserver.http.Response;

import java.util.Map;

public interface View {

    void render(Map<String, ?> models, Request request, Response response);

}
