package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class SessionData {
    private Map<String, Object> values;

    public SessionData() {
        this(new HashMap<>());
    }

    public SessionData(Map<String, Object> values) {
        this.values = values;
    }

    public Object getObject(String name) {
        return values.get(name);
    }

    public void removeSessionData(String name) {
        this.values.remove(name);
    }

    public void put(String name, Object value) {
        this.values.put(name, value);
    }
}
