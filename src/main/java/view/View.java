package view;

import http.HttpRequest;
import http.HttpResponse;

public interface View {
    void draw(final HttpRequest httpRequest, final HttpResponse httpResponse);
}
