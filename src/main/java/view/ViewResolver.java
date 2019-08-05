package view;

import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ViewResolver {
    View toView(final HttpResponse response, final String path) throws IOException, URISyntaxException;
}
