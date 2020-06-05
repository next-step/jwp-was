package web.servlet;

public interface ViewResolver {
    View resolveViewName(String viewName);
}
