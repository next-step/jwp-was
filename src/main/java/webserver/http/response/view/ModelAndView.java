package webserver.http.response.view;

/**
 * @author : yusik
 * @date : 2019-08-07
 */
public class ModelAndView {

    private static final String PREFIX_REDIRECT = "redirect::";
    private Object model;
    private String view;

    public ModelAndView(String view) {
        this.view = view;
    }

    public ModelAndView(Object model, String view) {
        this.model = model;
        this.view = view;
    }

    public ModelAndView() {
        this.view = "";
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public boolean isRedirect() {
        return view.startsWith(PREFIX_REDIRECT);
    }

    public String getRedirectUrl() {
        return view.substring(PREFIX_REDIRECT.length());
    }
}
