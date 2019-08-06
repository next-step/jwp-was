package webserver.handler;

import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static utils.FileIoUtils.loadFileFromClasspath;

public class StaticResourceHandler implements Handler {

    private static final String DEFAULT_PATH = "./static";

    public StaticResourceHandler() {}

    @Override
    public HttpResponse doHandle(HttpRequest httpRequest) throws Exception {
        ResourceType resource = ResourceType.parse(httpRequest.getPath());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", resource.contentType.getValue());
        byte[] body = loadFileFromClasspath(DEFAULT_PATH + httpRequest.getPath());
        return HttpResponse.ok(headers, body);
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return ResourceType.anyMatchExtensionAtEnd(httpRequest.getPath());
    }

    private enum ResourceType {

        CSS(".css", ContentType.TEXT_CSS),
        JS(".js", ContentType.TEXT_JAVASCRIPT);

        private String extension;
        private ContentType contentType;

        ResourceType(String extension, ContentType contentType) {
            this.extension = extension;
            this.contentType = contentType;
        }

        public static boolean anyMatchExtensionAtEnd(String path) {
            return Arrays.stream(ResourceType.values())
                    .anyMatch(resourceType -> resourceType.containsExtensionAtEnd(path));
        }

        public static ResourceType parse(String path) {
            return Arrays.stream(values())
                    .filter(resourceType -> resourceType.containsExtensionAtEnd(path))
                    .findFirst()
                    .orElseThrow(() -> new UnsupportedOperationException("지원하지 않는 타입입니다."));
        }

        public boolean containsExtensionAtEnd(String path) {
            return path.endsWith(extension);
        }
    }
}
