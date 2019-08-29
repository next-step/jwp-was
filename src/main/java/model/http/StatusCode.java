package model.http;

import java.util.Arrays;
import java.util.Optional;

public enum StatusCode {
    OK(200, "OK"),
    FOUND(302, "Found")
    ;

    private int code;
    private String reasonPhase;

    private StatusCode(int code, String reasonPhase) {
        this.code = code;
        this.reasonPhase = reasonPhase;
    }

    public int getCode() {
        return code;
    }

    public String getReasonPhase() {
        return reasonPhase;
    }

    public static StatusCode of(int code) {
        return find(code).orElseThrow(() -> new IllegalArgumentException("wrong status code"));
    }

    public static Optional<StatusCode> find(int code) {
        return Arrays.stream(values())
                .filter(statusCode -> statusCode.getCode() == code)
                .findFirst();
    }

    @Override
    public String toString() {
        return "StatusCode{" +
                "code=" + code +
                ", reasonPhase=" + reasonPhase +
                '}';
    }
}
