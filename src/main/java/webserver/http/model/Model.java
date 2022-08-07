package webserver.http.model;

import java.util.Map;

public class Model {

    private final String path;
    private final Map<String, Object> modelMap;

    public Model(String path, Map<String, Object> modelMap) {
        this.path = path;
        this.modelMap = modelMap;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModelMap() {
        return modelMap;
    }
}
