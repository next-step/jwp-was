package webserver.http.request.header;

import webserver.http.request.header.exception.InvalidUriException;

public class Uri {
    private final String contents;

    Uri(String contents) {
        validate(contents);
        this.contents = contents;
    }

    @Override
    public String toString() {
        return contents;
    }

    private void validate(String uri) {
        if (!uri.startsWith("/")) {
            throw new InvalidUriException("유효한 uri가 아닙니다.");
        }
    }
}
