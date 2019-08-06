package webserver.http.resource;

import webserver.http.ModelAndView;
import webserver.resource.ResourceLoader;

import java.io.IOException;

public class MockResourceLoader implements ResourceLoader {

    private String location;

    public MockResourceLoader(String location) {
        this.location = location;
    }

    @Override
    public boolean support(String name) {
        return location.equals(name);
    }

    @Override
    public String getResource(ModelAndView mav) throws IOException {
        return mav.getViewName();
    }
}
