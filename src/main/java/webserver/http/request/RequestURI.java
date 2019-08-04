package webserver.http.request;

import lombok.Getter;

import java.net.URI;

@Getter
public class RequestURI {

    private final URI uri;
    private final Query query;

    public RequestURI(String requestURI) {
        this.uri = URI.create(requestURI);
        this.query = new Query(uri.getQuery());
    }

    public String path() {
        return uri.getPath();
    }
}
