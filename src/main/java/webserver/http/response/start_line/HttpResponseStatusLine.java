package webserver.http.response.start_line;

public class HttpResponseStatusLine {

    private final String statusLine;

    public HttpResponseStatusLine(final String statusLine) {
        this.statusLine = statusLine;
    }

    public String getStatusLine() {
        return statusLine;
    }
}
