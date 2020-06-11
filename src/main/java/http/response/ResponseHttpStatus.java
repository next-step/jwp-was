package http.response;

public enum ResponseHttpStatus {

    OK(200, "OK"),
    Found(302, "Found");

    private int status;
    private String code;

    ResponseHttpStatus(final int status, final String code) {
        this.status = status;
        this.code = code;
    }

    @Override
    public String toString() {
        return status + " " + code + "\r\n";
    }
}
