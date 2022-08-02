package webserver.http;

import java.util.List;

public class ResourceHandler {
    private static final List<Resource> RESOURCES = List.of(
            new StaticResource(),
            new TemplateResource()
    );

    private ResourceHandler() {
    }

    public static byte[] handle(String url, Headers headers) {
        return RESOURCES.stream()
                .filter(it -> it.match(url))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .handle(url, headers);
    }
}
