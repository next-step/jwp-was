package webserver.http.resource;

import exception.HttpException;
import org.junit.jupiter.api.Test;
import webserver.http.ModelAndView;
import webserver.resource.ResourceHandler;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResourceHandlerTest {

    private static final String EXPECTED_LOCATION = "/templates/index.html";

    @Test
    void resourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler(asList(new MockResourceLoader(EXPECTED_LOCATION)));
        String contents = resourceHandler.getContents(new ModelAndView(EXPECTED_LOCATION));
        assertThat(contents).isEqualTo(EXPECTED_LOCATION);
    }

    @Test
    void resourceHandlerNotFound() {
        ResourceHandler resourceHandler = new ResourceHandler(asList(new MockResourceLoader("NotFound")));
        assertThrows(HttpException.class, () -> resourceHandler.getContents(new ModelAndView(EXPECTED_LOCATION)));
    }

}
