package webserver.response;

import java.util.Objects;

public class HttpResponse {

    private final StatusLine responseLine;
    private final ResponseHeaders responseHeaders;
    private final ResponseBody responseBody;

    public HttpResponse(StatusLine responseLine, ResponseHeaders responseHeaders, ResponseBody responseBody) {
        this.responseLine = responseLine;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }



    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HttpResponse that = (HttpResponse) o;
        return Objects.equals(responseLine, that.responseLine) && Objects.equals(responseHeaders, that.responseHeaders)
            && Objects.equals(responseBody, that.responseBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseLine, responseHeaders, responseBody);
    }
}
