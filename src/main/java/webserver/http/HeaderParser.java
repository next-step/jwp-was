package webserver.http;

public class HeaderParser {
    private String headerName;
    private String headerValue;

    public HeaderParser(String line) {
        this.headerName = line.split(":")[0];
        if (headerName.isEmpty()) {
            return;
        }
        this.headerValue = line.split(":")[1].trim();
    }

    public String getHeaderName() {
        return headerName;
    }

    public String getHeaderValue() {
        return headerValue;
    }
}
