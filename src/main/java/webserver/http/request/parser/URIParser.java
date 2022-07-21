package webserver.http.request.parser;

import webserver.http.request.URI;

public class URIParser {

    public URI parse(String message) {
        return new URI(message);
    }
}
