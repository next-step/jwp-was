package webserver.view;

import webserver.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

class RedirectView implements View {

    private final String url;

    RedirectView(String url) {
        this.url = url;
    }

    String getUrl() {
        return url;
    }

    @Override
    public void render(Map<String, ?> models, HttpResponse httpResponse) throws IOException {
        httpResponse.sendRedirect(url);
    }
}
