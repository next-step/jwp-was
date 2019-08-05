package webserver.http.response;

import lombok.Getter;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;

import java.util.Objects;

public class StatusLine {

    private static final String SP = " ";
    private static final String CRLF = "\r\n";

    @Getter
    private HttpVersion httpVersion;

    @Getter
    private HttpStatus statusCode;

    public StatusLine(HttpVersion httpVersion) {
        setHttpVersion(httpVersion);
        selectHttpStatus(HttpStatus.OK);
    }

    public StatusLine(HttpVersion httpVersion, HttpStatus statusCode) {
        setHttpVersion(httpVersion);
        selectHttpStatus(statusCode);
    }

    private void setHttpVersion(HttpVersion httpVersion) {
        Objects.requireNonNull(httpVersion, "HTTP-Version은 필수입니다.");

        this.httpVersion = httpVersion;
    }

    public void selectHttpStatus(HttpStatus statusCode) {
        Objects.requireNonNull(statusCode, "Status-Code는 필수입니다.");

        this.statusCode = statusCode;
    }

    /**
     * Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
     */
    @Override
    public String toString() {
        return String.join(SP,
                httpVersion.getVersion(),
                String.valueOf(statusCode.getValue()),
                statusCode.getReasonPhrase());
    }
}
