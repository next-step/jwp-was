package webserver.http.view;

public class ErrorViewResolver implements ViewResolver {

    @Override
    public View resolveView(String viewName) {
        return new ErrorView();
    }
}
