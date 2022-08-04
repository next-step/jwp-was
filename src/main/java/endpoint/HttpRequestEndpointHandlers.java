package endpoint;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class HttpRequestEndpointHandlers implements Iterable<HttpRequestEndpointHandler> {
    private List<HttpRequestEndpointHandler> endpointHandlers;

    public HttpRequestEndpointHandlers(List<HttpRequestEndpointHandler> endpointHandlers) {
        this.endpointHandlers = endpointHandlers.stream()
                .filter(isNotEmptyEndPointPathFilter())
                .collect(Collectors.toList());
    }

    private Predicate<HttpRequestEndpointHandler> isNotEmptyEndPointPathFilter() {
        return this::isNotEmptyEndPointPath;
    }

    private boolean isNotEmptyEndPointPath(HttpRequestEndpointHandler endpointHandler) {
        return endpointHandler.endpoint != null && !endpointHandler.endpoint.isEmptyPath();
    }

    @Override
    public Iterator<HttpRequestEndpointHandler> iterator() {
        return endpointHandlers.iterator();
    }

    public Stream<HttpRequestEndpointHandler> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
