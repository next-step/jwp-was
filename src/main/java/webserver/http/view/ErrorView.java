package webserver.http.view;

import utils.DefaultPageUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class ErrorView implements View {

    @Override
    public void render(HttpRequest request, HttpResponse response) throws Exception {
        String errorPage = DefaultPageUtils.makeErrorPage(response.getStatus());
        byte[] contents = errorPage.getBytes();
        response.addHeader("Content-Length", String.valueOf(contents.length));

        response.response(contents);
    }

}
