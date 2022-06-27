package webserver.http.service.get;

import utils.FileIoUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceService extends GetService {
    private static final List<String> applyFileExtension = List.of(".html", ".css", ".ico", ".js");

    @Override
    protected boolean pathMatch(HttpRequest httpRequest) {
        return applyFileExtension.stream()
                                 .anyMatch(extension -> httpRequest.getPath().endsWith(extension));
    }

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(httpRequest.getPath());
    }
}
