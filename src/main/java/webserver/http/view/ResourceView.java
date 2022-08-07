package webserver.http.view;

import utils.FileIoUtils;
import webserver.http.HttpContentType;
import webserver.http.HttpResponse;

public class ResourceView implements View {

    private String resourcePath;

    public ResourceView(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public void render(HttpResponse response) throws Exception {
        HttpContentType contentType = HttpContentType.of(getFileExtension(resourcePath));
        byte[] body = FileIoUtils.loadFileFromClasspath(contentType.getResourcePath() + resourcePath);
        response.addHeader("Content-Type", contentType.getValue());
        response.addHeader("Content-Length", String.valueOf(body.length));

        response.response(body);
    }

    private String getFileExtension(String path) {
        String[] split = path.split("\\.");
        String fileExtention = split[split.length - 1];
        return fileExtention;
    }
}
