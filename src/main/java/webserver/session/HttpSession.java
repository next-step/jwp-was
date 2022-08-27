package webserver.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {

    private String uuid;

    private Map<String, Object> attributesByName = new HashMap<>();

    private HttpSession(String uuid) {
        this.uuid = uuid;
    }

    public static HttpSession createSession(String uuid) {
        return new HttpSession(uuid);
    }

    public String getId() {
        return uuid;
    }

    public void setAttribute(String name, Object value) {
        attributesByName.put(name, value);
    }

    public Object getAttribute(String name) {
        if(!attributesByName.containsKey(name)) {
            return null;
        }

        return attributesByName.get(name);
    }

    public void removeAttribute(String name) {
        if(!attributesByName.containsKey(name)) {
            throw new IllegalArgumentException("삭제할 Attribute가 없습니다");
        }

        attributesByName.remove(name);
    }

    public void invalidate() {
        attributesByName.clear();
    }
}
