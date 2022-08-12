package mvc.view;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface View {

    void render(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
