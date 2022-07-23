package webserver.header.request.requestline;

import webserver.header.exception.InvalidUriException;

public class Index {
    private final String contents;

    public Index(String contents) {
        validate(contents);
        this.contents = contents;
    }

    @Override
    public String toString() {
        return contents;
    }

    private void validate(String index) {
        if (!index.startsWith("/")) {
            throw new InvalidUriException("유효한 index가 아닙니다.");
        }
    }
}
