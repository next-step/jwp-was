package webserver.resource;

import webserver.http.ModelAndView;

/**
 * strategy interface for loading resource given file extension
 */
public interface ResourceLoader {

    /**
     * Whether the given file name's extension is supported by loader
     */
    boolean support(String name);

    String getResource(ModelAndView mav);

}
