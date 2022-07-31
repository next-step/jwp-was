package webserver;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private final String view;

    private final Map<String, Object> model;

    public ModelAndView(String view) {
        this.view = view;
        this.model = new HashMap<>();
    }

    public void addAttribute(String name, Object value) {
        this.model.put(name, value);
    }

    public String getView() {
        return view;
    }

    public Map<String, Object> getModel() {
        return new HashMap<>(model);
    }
}
