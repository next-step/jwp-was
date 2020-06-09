package http.view;

import http.requests.HttpRequest;
import http.responses.HttpResponse;

import java.util.Optional;

/**
 * fonts, css, js, images 등의 리소스를 가짐
 */
public class StaticView implements View {

    private final byte[] bytes;

    public StaticView(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public void render(HttpRequest request, HttpResponse response) throws Exception {
        final Optional<byte[]> viewToRender = Optional.ofNullable(bytes);
        if (viewToRender.isPresent()) {
            response.addHeader("Content-Type", determineMimeType(request.getPath()));
            response.responseBody(bytes);
            return;
        }
        response.responseNotFound();
    }

    private String determineMimeType(String path) {
        if (path.endsWith(".css")) {
            return "text/css";
        } else if (path.endsWith(".js")) {
            return "application/javascript";
        } else if (path.endsWith("png")) {
            return "image/png";
        }
        return "";
    }
}
