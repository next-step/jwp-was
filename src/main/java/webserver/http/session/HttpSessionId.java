package webserver.http.session;

import java.util.UUID;

public class HttpSessionId {
    private static final int SESSION_ID_LENGTH = 32;
    private static final String SESSION_ID_REGEX_PATTERN = "[a-f0-9]{" + SESSION_ID_LENGTH + "}";
    private String id;

    private HttpSessionId(String id) {
        if (id.length() != SESSION_ID_LENGTH) {
            throw new IllegalArgumentException("잘못된 Session ID 길이입니다.");
        }

        if (!id.matches(SESSION_ID_REGEX_PATTERN)) {
            throw new IllegalArgumentException("잘못된 Session ID 패턴입니다.");
        }

        this.id = id;
    }

    private static String generateSessionId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static HttpSessionId create() {
        return new HttpSessionId(generateSessionId());
    }

    public static HttpSessionId of(String id) {
        return new HttpSessionId(id);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpSessionId that = (HttpSessionId) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
