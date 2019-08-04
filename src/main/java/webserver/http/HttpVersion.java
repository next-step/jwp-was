package webserver.http;

public enum HttpVersion {
    HTTP_1_1("HTTP/1.1", "HTTP/1.1"),
    HTTP_2_0("HTTP/2.0", "HTTP/2"),
    HTTP_3_0("HTTP/3.0", "HTTP/3");

    private final String value;
    private final String display;

    private HttpVersion(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public static HttpVersion of(String value) {
        if (HttpVersion.HTTP_1_1.value.equals(value) || HttpVersion.HTTP_1_1.display.equals(value)) {
            return HttpVersion.HTTP_1_1;
        } else if (HttpVersion.HTTP_2_0.value.equals(value) || HttpVersion.HTTP_2_0.display.equals(value)) {
            return HttpVersion.HTTP_2_0;
        } else if (HttpVersion.HTTP_3_0.value.equals(value) || HttpVersion.HTTP_3_0.display.equals(value)) {
            return HttpVersion.HTTP_3_0;
        }

        throw new IllegalArgumentException(String.format("'%s'에 해당하는 HttpVersion 을 찾을 수 없습니다.", value));
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return display;
    }
}
