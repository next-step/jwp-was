package http;


import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StatusLine {
    private final HttpProtocol httpProtocol;
    private final String statusCode;
    private final String reason;

    public StatusLine(HttpProtocol httpProtocol, String statusCode, String reason) {
        this.httpProtocol = httpProtocol;
        this.statusCode = statusCode;
        this.reason = reason;
    }

    @Nonnull
    public static StatusLine from(@Nullable String statusLine) {
        if (StringUtils.isEmpty(statusLine)) {
            return makeEmptyStatusLine();
        }

        String[] splitBySpace = statusLine.replaceAll("\r\n", "").split(" ");
        if (splitBySpace.length < 3) {
            return makeEmptyStatusLine();
        }

        HttpProtocol httpProtocol = HttpProtocol.from(splitBySpace[0]);
        String statusCode = splitBySpace[1];
        String reason = splitBySpace[2];

        return new StatusLine(httpProtocol, statusCode, reason);
    }

    @Nonnull
    public String makeStatusLineString() {
        return httpProtocol.getProtocol() + "/" + httpProtocol.getVersion() + " " +
                statusCode + " " + reason + "\r\n";

    }

    public static StatusLine makeEmptyStatusLine() {
        return new EmptyStatusLine(null, "", "");
    }

    static class EmptyStatusLine extends StatusLine {
        private EmptyStatusLine(HttpProtocol httpProtocol, String statusCode, String reasonPhrase) {
            super(httpProtocol, statusCode, reasonPhrase);
        }

        // TODO 예외 일 때 처리 확인
        public String makeStatusLineString() {
            return "";
        }
    }

    public HttpProtocol getHttpProtocol() {
        return httpProtocol;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getReason() {
        return reason;
    }
}
