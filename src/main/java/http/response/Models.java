package http.response;

import java.util.HashMap;
import java.util.Map;

public class Models {
    private final Map<String, Object> models = new HashMap<>();

    private Models() {}

    public static Models init() {
        return new Models();
    }

    public void addModel(final String key, final Object value) {
        models.put(key, value);
    }

    public Map<String, Object> getModels() {
        return models;
    }
}
