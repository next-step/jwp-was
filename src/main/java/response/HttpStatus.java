package response;

/**
 * Created by youngjae.havi on 2019-08-04
 */
public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "FOUND"),
    NOT_FOUND(404, "NOT FOUND");

    private static final String FORMAT = "%d %s";

    private final int code;
    private final String reasonType;

    HttpStatus(final int code,
               final String reasonType) {
        this.code = code;
        this.reasonType = reasonType;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, code, reasonType);
    }
}
