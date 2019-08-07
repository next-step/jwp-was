package webserver.resource;

import webserver.http.ModelAndView;

public interface ResourceLoader {

    boolean support(String name);

    String getResource(ModelAndView mav);

}
