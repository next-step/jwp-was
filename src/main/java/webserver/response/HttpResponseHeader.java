package webserver.response;

import lombok.Getter;

@Getter
public enum HttpResponseHeader {
    CONTENT_TYPE(";"),
    CONTENT_LENGTH(""),
    ACCEPT_ENCODING(", "),
    LOCATION(""),
    SET_COOKIE(";");

    private String delimiter;

    HttpResponseHeader(String delimiter) {
        this.delimiter = delimiter;
    }
}
