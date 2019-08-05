package webserver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private StatusCode statusCode;
    private Map<String, Object> model;
    private String viewName;
    private boolean redirect;
    private Map<String, String> cookies;

    public static class Builder {
        private final Map<String, Object> model;
        private final String viewName;
        private StatusCode statusCode;
        private boolean redirect = false;
        private Map<String, String> cookies = new HashMap<>();

        public Builder(String viewName) {
            this.statusCode = StatusCode.OK;
            this.model = new HashMap<>();
            this.viewName = viewName;
        }

        public Builder statusCode(StatusCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder addAttributes(String key, Object value) {
            this.model.put(key, value);
            return this;
        }

        public Builder redirect(boolean redirect) {
            this.redirect = redirect;
            this.statusCode = StatusCode.FOUND;
            return this;
        }

        public Builder addCookie(String key, String value) {
            this.cookies.put(key, value);
            return this;
        }

        public ModelAndView build() {
            ModelAndView modelAndView = new ModelAndView(this.viewName);
            modelAndView.statusCode = this.statusCode;
            modelAndView.model = this.model;
            modelAndView.redirect = this.redirect;
            modelAndView.cookies = this.cookies;
            return modelAndView;
        }
    }

    public ModelAndView(String viewName) {
        this.statusCode = StatusCode.OK;
        this.viewName = viewName;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void addCookie(String key, String value) {
        this.cookies.put(key, value);
    }

    public boolean hasCookie() {
        return ! cookies.isEmpty();
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }
}
