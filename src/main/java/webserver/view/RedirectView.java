package webserver.view;

import webserver.http.Response;

import java.util.Map;

public class RedirectView implements View {

    private final String url;

    public RedirectView(String url) {
        this.url = url;
    }

    @Override
    public void render(Map<String, ?> models, Response response) {
        response.sendRedirect(url);
    }
}
