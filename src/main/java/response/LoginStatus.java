package response;

/**
 * Created by youngjae.havi on 2019-08-03
 */
public enum LoginStatus {
    LOGIN_SUCCESS(true),
    LOGIN_FAILED(false),
    NOT_LOGIN(null);

    private Boolean stauts;

    LoginStatus(Boolean stauts) {
        this.stauts = stauts;
    }

    public Boolean status() {
        return stauts;
    }}
