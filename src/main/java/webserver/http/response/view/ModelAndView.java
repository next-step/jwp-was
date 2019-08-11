package webserver.http.response.view;

/**
 * @author : yusik
 * @date : 2019-08-07
 */
public class ModelAndView {
    private Object model;
    private String viewName;

    public ModelAndView(Object model, String viewName) {
        this.model = model;
        this.viewName = viewName;
    }

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public Object getModel() {
        return model;
    }

    public String getViewName() {
        return viewName;
    }

    public String getOriginalViewName(String prefix) {
        return viewName.substring(prefix.length());
    }
}
