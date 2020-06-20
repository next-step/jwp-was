package webserver.controller;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ModelAndView {

    public static final String REDIRECT_PREFIX = "redirect:";

    private Map<String, Object> model = new HashMap<>();
    private String view;

    public boolean isRedirect() {
        return view.startsWith(REDIRECT_PREFIX);
    }

    public String getRedirectView() {
        return view.substring(REDIRECT_PREFIX.length());
    }

    public void addModel(String key, Object value) {
        model.put(key, value);
    }

    public void setView(String viewName) {
        this.view = viewName;
    }
}
