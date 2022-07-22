package constant;

public enum Message {
    NOT_ALLOW_HTTP_METHOD("허용되지 않은 HttpMethod 입니다.");

    private String value;

    Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
