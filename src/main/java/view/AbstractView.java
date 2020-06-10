package view;

import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractView implements View {

    @Override
    public void render(HttpRequest request, HttpResponse response) {
        byte[] content = read(request, response);
        response.setBody(content);
        if (content.length > 0) {
            response.addHeader("Content-Length", String.valueOf(content.length));
        }
        response.write();
    }

    abstract byte[] read(HttpRequest request, HttpResponse response);
}
