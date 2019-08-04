package webserver.resolver;

public interface ViewResolver {

    byte[] resolve(String viewName) throws Exception;
    String getContentType();
}