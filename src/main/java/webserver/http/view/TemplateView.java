package webserver.http.view;

import utils.HandlebarsUtils;
import webserver.http.HttpResponse;

public class TemplateView implements View {

    private String resourcePath;

    public TemplateView(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public void render(HttpResponse response) throws Exception {
        String body = HandlebarsUtils.template(resourcePath, response.getAttributes());

        byte[] contents = body.getBytes();
        response.addHeader("Content-Type", "text/html;charset=utf-8");
        response.addHeader("Content-Length", String.valueOf(contents.length));

        response.response(contents);
    }

}
