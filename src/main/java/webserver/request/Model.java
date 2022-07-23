package webserver.request;

import com.google.common.collect.Maps;
import java.util.Map;

public class Model {
    private Map<String, Object> modelMap = Maps.newHashMap();

    public void set(String key, Object value) {
        modelMap.put(key, value);
    }

    public Object get(String key) {
        return modelMap.get(key);
    }

    public Map<String, Object> getModelMap() {
        return this.modelMap;
    }
}
