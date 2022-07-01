package mvc.view;

public class StaticViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(String viewName) {
        return new StaticResourceView();
    }
}
