package handler;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.view.StaticResourceView;
import java.util.Objects;

public class StaticResourceHandler implements Handler {

    private final String resourcePath;

    public StaticResourceHandler(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        return new HttpResponse(new StaticResourceView(resourcePath + httpRequest.getPath()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StaticResourceHandler that = (StaticResourceHandler) o;
        return Objects.equals(resourcePath, that.resourcePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourcePath);
    }
}
