package webserver.http.view;

public class TemplateViewResolver implements ViewResolver {
    @Override
    public View resolveView(String viewName) {
        return new TemplateView(viewName);
    }
}
