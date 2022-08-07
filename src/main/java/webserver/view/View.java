package webserver.view;

import webserver.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

public interface View {

    void render(Map<String, ?> models, HttpResponse httpResponse) throws IOException;

}
