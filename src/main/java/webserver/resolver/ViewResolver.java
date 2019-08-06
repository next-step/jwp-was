package webserver.resolver;

import webserver.http.ModelAndView;

public interface ViewResolver {

    String DEFAULT_ROOT_PATH = "/templates";

    byte[] resolve(String viewName) throws Exception;
    byte[] resolve(ModelAndView modelAndView) throws Exception;
    String getContentType();
}