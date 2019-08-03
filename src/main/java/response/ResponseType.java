package response;

/**
 * Created by youngjae.havi on 2019-08-03
 */
public enum ResponseType {
    LOGIN_SUCCESS("true"),
    LOGIN_FAILED("false"),
    NOT_LOGIN(""),
    CSS("");

    private String stauts;

    ResponseType(String stauts) {
        this.stauts = stauts;
    }

    public String status() {
        return stauts;
    }}
