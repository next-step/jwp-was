package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private String view;
    private Map<String, Object> modelMap;

    public ModelAndView() {
        modelMap = new HashMap<>();
    }

    public ModelAndView(String view) {
        this.view = view;
        this.modelMap = new HashMap<>();
    }

    public Object getAttribute(String key) {
        return this.modelMap.get(key);
    }

    public void setAttribute(String key, Object value) {
        this.modelMap.put(key, value);
    }

    public Map<String, Object> getModelMap() {
        return this.modelMap;
    }

    public void setView(String viewName) {
        this.view = viewName;
    }

    public String getView() {
        return view;
    }
}
