package webserver.http.resource;

import webserver.http.ModelAndView;
import webserver.resource.ResourceHandler;

public class MockResourceHandler extends ResourceHandler {

    @Override
    public String getContents(ModelAndView mav) {
        return mav.getViewName();
    }
}
