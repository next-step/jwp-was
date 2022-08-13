package webserver.http.model.response;

public class Cookie {
    private final String name;
    private String value;

    public Cookie(String name) {
        this(name, null);
    }

    public Cookie(String name, String value) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Cookie Name은 빈 값을 입력할 수 없습니다.");
        }
        this.name = name;
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Set-Cookie: " + name + "=" + value + "; Path=/";
    }
}
