package web.servlet;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModelAndView {

    private String viewName;
    private Map<String, Object> model;

    private ModelAndView(String viewName) {
        this.viewName = viewName;
        this.model = new LinkedHashMap<>();
    }

    public static ModelAndView from(String viewName) {
        return new ModelAndView(viewName);
    }

    public void addAttribute(String name, Object value) {
        this.model.put(name, value);
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, Object> getModel() {
        return this.model;
    }
}
