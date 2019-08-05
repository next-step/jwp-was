package webserver.resource;

import webserver.ModelAndView;

import java.io.IOException;

public interface ResourceLoader {

    boolean support(String name);

    String getResource(ModelAndView mav) throws IOException;

}
