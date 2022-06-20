package webserver.http.service;

import utils.FileIoUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ViewService implements Service {
    private static final List<String> applyFileExtension = List.of(".html", ".css", ".ico", ".css ", ".js");

    @Override
    public boolean find(HttpRequest httpRequest) {
        return applyFileExtension.stream()
                          .anyMatch(extension -> httpRequest.getPath().endsWith(extension));
    }

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setBody(read(httpRequest.getPath()));
    }

    private byte[] read(String path) {
        try {
            return FileIoUtils.loadFileFromClasspath("./templates" + path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
