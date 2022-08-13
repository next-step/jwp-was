package webserver.response;

public class StatusLine {

    public static final String DELIMITER = " ";

    private String version;
    private String statusCode;
    private String statusReason;

    public StatusLine(String version, String statusCode, String statusReason) {
        this.version = version;
        this.statusCode = statusCode;
        this.statusReason = statusReason;
    }

    public static StatusLine parse(String line) {
        String[] values = line.split(DELIMITER);

        return new StatusLine(values[0], values[1], values[2]);
    }

    public String getVersion() {
        return version;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public String getStatusLine() {
        return version + " " + statusCode + " " + statusReason;
    }
}
