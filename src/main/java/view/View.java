package view;

import http.HttpRequest;
import http.HttpResponse;

public interface View {
    void render(final HttpRequest httpRequest, final HttpResponse httpResponse);
}
