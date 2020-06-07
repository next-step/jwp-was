package view;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface View {
    void render(final HttpRequest httpRequest, final HttpResponse httpResponse);
}
