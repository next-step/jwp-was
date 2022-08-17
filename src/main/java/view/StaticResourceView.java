package view;

import utils.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class StaticResourceView implements View {
    @Override
    public void render(HttpRequest request, HttpResponse response, String location) throws Exception {
        byte[] responseBody = FileIoUtils.loadFileFromClasspath("./" + getStaticLocation(location));
        response.responseBody(responseBody);
    }

    private String getStaticLocation(String location) {
        String prefix = (location.endsWith(".html") || location.endsWith(".ico")) ? "templates" : "static";
        return prefix + location;
    }
}
