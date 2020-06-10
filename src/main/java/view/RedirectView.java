package view;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class RedirectView extends AbstractView {

    private final String url;

    public RedirectView(String url) {
        this.url = url;
    }

    public byte[] read(HttpRequest request, HttpResponse response) {
        return new byte[0];
    }
}
