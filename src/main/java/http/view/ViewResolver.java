package http.view;

/**
 * 뭘 그릴지 찾아주는 고마운분
 */
public interface ViewResolver {
    View resolve(String viewName);
}
