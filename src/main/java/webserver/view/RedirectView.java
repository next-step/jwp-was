package webserver.view;

import webserver.http.Request;
import webserver.http.Response;

import java.util.Map;

public class RedirectView implements View {

    private final String url;

    public RedirectView(String url) {
        this.url = url;
    }

    @Override
    public void render(Map<String, ?> models, Request request, Response response) {
        response.sendRedirect(url);
    }
}
