package http;

import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HttpSession implements Session {
    protected ConcurrentMap<String, Object> attributes = new ConcurrentHashMap<>();
    private final String id;

    public HttpSession(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value)) {
            return;
        }

        attributes.put(name, value);
    }

    @Override
    @Nullable
    public Object getAttribute(@Nullable String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        return attributes.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public void invalidate() {
        attributes.clear();
    }
}
