package webserver.http.view;

public class ResourceViewResolver implements ViewResolver {
    @Override
    public View resolveView(String viewName) {
        return new ResourceView(viewName);
    }
}
