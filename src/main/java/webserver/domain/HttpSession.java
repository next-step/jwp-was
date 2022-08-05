package webserver.domain;

import static webserver.servlet.UserListController.KEY_LOGINED;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class HttpSession {

    private String sessionId;
    private Map<String, Object> attributes;

    public HttpSession() {
        this.sessionId = UUID.randomUUID().toString();
        this.attributes = Maps.newConcurrentMap();
    }

    public static HttpSession empty() {
        return new HttpSession();
    }

    public String getId() {
        return this.sessionId;
    }

    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    public void invalidate() {
        this.attributes.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpSession that = (HttpSession) o;
        return Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }

    public HttpSession login() {
        setAttribute(KEY_LOGINED, Boolean.TRUE.toString());
        return this;
    }

    public boolean isLogined() {
        String logined = (String) getAttribute(KEY_LOGINED);
        return Strings.isNullOrEmpty(logined) || !Boolean.parseBoolean(logined);
    }

    public Cookie getLoginedCookie() {
        return Cookie.jsseionId(getId());
    }

    public int size() {
        return this.attributes.size();
    }
}
