package http.response;

import http.HttpEntity;
import http.HttpHeaders;
import utils.StringUtils;

import java.util.Optional;

public class HttpResponse {
    private final StatusLine statusLine;
    private final HttpEntity httpEntity;

    public HttpResponse(StatusLine statusLine, HttpEntity httpEntity) {
        this.statusLine = statusLine;
        this.httpEntity = httpEntity;
    }

    public String getStatusLine() {
        return this.statusLine.toString();
    }

    public boolean hasHttpHeaders() {
        return Optional.ofNullable(httpEntity)
                .map(HttpEntity::getHttpHeaders)
                .map(HttpHeaders::isNotEmpty)
                .orElse(false);
    }

    public HttpHeaders getHttpHeader() {
        return Optional.ofNullable(httpEntity)
            .map(HttpEntity::getHttpHeaders)
            .orElse(null);
    }

    public String getHttpHeaderString() {
        return Optional.ofNullable(httpEntity)
            .map(HttpEntity::getHttpHeaders)
            .map(HttpHeaders::toString)
            .orElse("");
    }

    public boolean hasHttpBody() {
        return Optional.ofNullable(httpEntity)
            .map(HttpEntity::getBody)
            .filter(StringUtils::isNotEmpty)
            .isPresent();
    }

    public String getHttpBody() {
        return Optional.ofNullable(httpEntity)
            .map(HttpEntity::getBody)
            .orElse("");
    }

    public int getHttpBodyLength() {
        return Optional.ofNullable(httpEntity)
            .map(HttpEntity::getBody)
            .map(String::getBytes)
            .map(body -> body.length)
            .orElse(0);
    }

}
