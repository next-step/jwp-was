package http.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import http.common.HttpEntity;
import http.common.HttpHeaders;
import http.common.HttpStatus;
import http.request.requestline.Protocol;
import lombok.extern.slf4j.Slf4j;
import utils.StringUtils;

import java.io.DataOutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class HttpResponse {
    public static final String NEW_LINE_STRING = "\r\n";

    @JsonIgnore
    private final DataOutputStream dos;
    private StatusLine statusLine;
    private HttpEntity httpEntity;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void setStatusLine(Protocol protocol, HttpStatus httpStatus) {
        this.statusLine = StatusLine.of(protocol, httpStatus);
    }

    public void setHttpEntity(HttpHeaders httpHeaders, String httpBody) {
        this.httpEntity = new HttpEntity(httpHeaders, httpBody);
    }

    public boolean hasHttpHeaders() {
        return Optional.ofNullable(httpEntity)
            .map(HttpEntity::getHttpHeaders)
            .map(HttpHeaders::isNotEmpty)
            .orElse(false);
    }

    public Map<String, String> getHttpHeaderMap() {
        return Optional.ofNullable(httpEntity)
                .map(HttpEntity::getHeaderMap)
                .orElse(Collections.emptyMap());
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

    public void write() {
        try {
            dos.writeBytes(statusLine + NEW_LINE_STRING);

            if (hasHttpHeaders()) {
                dos.writeBytes(getHttpHeaderString());
                dos.writeBytes(NEW_LINE_STRING);
            }

            dos.writeBytes(NEW_LINE_STRING);

            if (hasHttpBody()) {
                dos.write(getHttpBody().getBytes(), 0, getHttpBodyLength());
            }
            dos.flush();
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
