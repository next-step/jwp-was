package web.sevlet;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.util.Map;

public interface View {
    void render(Map<String, ?> model, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
