package was.http;

import java.util.ArrayList;
import java.util.List;

public class CachedHttpRequest implements HttpRequestPipe {
    private final HttpRequestPipe origin;
    private final List<HttpRequest> cached = new ArrayList<>(1);

    public CachedHttpRequest(HttpRequestPipe request) {
        this.origin = request;
    }

    @Override
    public HttpRequest request() {
        if (cached.isEmpty()) {
            cached.add(origin.request());
        }
        return cached.get(0);
    }
}
