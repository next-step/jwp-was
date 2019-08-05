package webserver;

import java.util.Map;

public class ModelAndView {

    private Map<String, Object> model;
    private String viewName;

    public ModelAndView(Map<String, Object> model, String viewName) {
        this.model = model;
        this.viewName = viewName;
    }

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
