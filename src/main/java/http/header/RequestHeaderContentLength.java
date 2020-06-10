package http.header;

public class RequestHeaderContentLength {
    private final int contentLength;

    public RequestHeaderContentLength(final String contentLength) {
        this.contentLength = Integer.valueOf(contentLength);
    }

    public int getContentLength() {
        return contentLength;
    }
}
