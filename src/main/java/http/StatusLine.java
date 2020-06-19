package http;

import static utils.StringConstant.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.util.StringUtils;

public class StatusLine {
    private final HttpProtocol httpProtocol;
    private final String statusCode;
    private final String reasonPhrase;

    public StatusLine(HttpProtocol httpProtocol, String statusCode, String reasonPhrase) {
        this.httpProtocol = httpProtocol;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    @Nonnull
    public static StatusLine from(@Nullable String statusLine) {
        if (StringUtils.isEmpty(statusLine)) {
            return makeErrorStatusLine(HttpStatus.BAD_REQUEST);
        }

        String[] splitBySpace = statusLine.replaceAll("\r\n", EMPTY).split(SPACE);
        if (splitBySpace.length < 3) {
            return makeErrorStatusLine(HttpStatus.BAD_REQUEST);
        }

        HttpProtocol httpProtocol = HttpProtocol.from(splitBySpace[0]);
        String statusCode = splitBySpace[1];
        String reasonPhrase = splitBySpace[2];

        return new StatusLine(httpProtocol, statusCode, reasonPhrase);
    }

    @Nonnull
    public static StatusLine makeErrorStatusLine(@Nonnull HttpStatus httpStatus) {
        String statusCode = Integer.toString(httpStatus.getValue());
        String reasonPhase = httpStatus.getReasonPhrase();
        return new StatusLine(new HttpProtocol("HTTP", "1.1"), statusCode, reasonPhase);
    }

    @Nonnull
    public String makeStatusLineString() {
        return httpProtocol.getProtocol() + SLASH + httpProtocol.getVersion() + SPACE +
                statusCode + SPACE + reasonPhrase + "\r\n";
    }

    public HttpProtocol getHttpProtocol() {
        return httpProtocol;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
