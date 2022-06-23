package webserver.http.service.get;

import utils.FileIoUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.service.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewService extends GetService {
    private static final List<String> applyFileExtension = List.of(".html", ".css", ".ico", ".js");
    private static final Map<String, String> resourcePath = new HashMap<>();

    static {
        resourcePath.put("html", "./templates");
        resourcePath.put("css", "./static");
        resourcePath.put("ico", "./templates");
        resourcePath.put("js", "./static");
    }

    @Override
    protected boolean pathMatch(HttpRequest httpRequest) {
        return applyFileExtension.stream()
                                 .anyMatch(extension -> httpRequest.getPath().endsWith(extension));
    }

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.ok(read(httpRequest.getPath()));
    }

    private byte[] read(String path) {
        try {
            return FileIoUtils.loadFileFromClasspath(resourcePath.get(path.substring(path.lastIndexOf(".") + 1)) + path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
