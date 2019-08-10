package webserver.http.resource;

import webserver.http.ModelAndView;
import webserver.resource.ResourceHandler;

public class StubResourceHandler extends ResourceHandler {

    @Override
    public String getContents(ModelAndView mav) {
        return mav.getViewName();
    }
}
