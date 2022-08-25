package webserver.http.response.start_line;

import org.apache.logging.log4j.util.Strings;

public class HttpResponseStatusLine {

    private String statusLine;

    public HttpResponseStatusLine(final String statusLine) {
        this.statusLine = statusLine;
    }

    public static HttpResponseStatusLine empty() {
        return new HttpResponseStatusLine(Strings.EMPTY);
    }

    public String getStatusLine() {
        return statusLine;
    }

    public void addStatusLine(String statusLine) {
        this.statusLine = statusLine;
    }
}
