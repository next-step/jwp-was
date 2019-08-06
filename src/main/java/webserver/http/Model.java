package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class Model {
    private Map<String, Object> modelMap;

    public Model() {
        modelMap = new HashMap<>();
    }

    public Object get(String key) {
        return this.modelMap.get(key);
    }

    public void set(String key, Object value) {
        this.modelMap.put(key, value);
    }

    public Map<String, Object> getModelMap() {
        return this.modelMap;
    }
}
