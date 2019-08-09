package webserver.http.resource;

import webserver.http.ModelAndView;
import webserver.resource.ResourceLoader;

public class StubResourceLoader implements ResourceLoader {

    private String location;

    public StubResourceLoader(String location) {
        this.location = location;
    }

    @Override
    public boolean support(String name) {
        return location.equals(name);
    }

    @Override
    public String getResource(ModelAndView mav) {
        return mav.getViewName();
    }
}
