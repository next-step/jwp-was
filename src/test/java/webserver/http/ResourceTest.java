package webserver.http;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.ModelAndView;
import webserver.Parameter;
import webserver.request.RequestBody;
import webserver.request.RequestHeader;
import webserver.request.RequestHolder;
import webserver.request.RequestLine;
import webserver.resource.HandlebarsResourceLoader;
import webserver.resource.ResourceLoader;
import webserver.resource.StaticResourceLoader;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ResourceTest {

    @DisplayName("load static resource by path")
    @ParameterizedTest(name = "viewName: {0}")
    @ValueSource(strings = {"/test.js", "/test.css"})
    void loadStaticResource(String viewName) throws IOException {
        ResourceLoader resourceHandler = new StaticResourceLoader(getClass().getClassLoader());
        ModelAndView mav = new ModelAndView(viewName, Collections.emptyMap());
        assertThat(resourceHandler.getResource(mav)).contains("hello test");
    }

    @DisplayName("load handlebars resource by path")
    @ParameterizedTest(name = "viewName: {0}")
    @ValueSource(strings = {"/index.html", "/registration.html"})
    void loadHandlebarsResource(String viewName) throws IOException {
        ResourceLoader resourceHandler = new HandlebarsResourceLoader();
        ModelAndView mav = new ModelAndView(viewName, Collections.emptyMap());
        assertThat(resourceHandler.getResource(mav)).contains("hello test");
    }

    @DisplayName("load handlebars resource with attributes by path")
    @ParameterizedTest(name = "viewName: {0}")
    @ValueSource(strings = {"/profile.html"})
    void loadHandlebarsResourceWithAttribute(String viewName) throws IOException {
        ResourceLoader resourceHandler = new HandlebarsResourceLoader();
        ModelAndView mav = new ModelAndView(viewName, Collections.emptyMap());
        mav.setModel(new HashMap<String, Object>(){{
            put("user", new User("jun", "password", "hyunjun", "test@test.com"));
        }});
        assertThat(resourceHandler.getResource(mav)).contains("hyunjun");
    }

    @DisplayName("load handlebars resource with list attributes by path")
    @ParameterizedTest(name = "viewName: {0}")
    @ValueSource(strings = {"/list.html"})
    void loadHandlebarsResourceWithListAttribute(String viewName) throws IOException {
        ResourceLoader resourceHandler = new HandlebarsResourceLoader();
        ModelAndView mav = new ModelAndView(viewName, Collections.emptyMap());
        List<User> users = asList(
                new User("jun", "password", "hyunjun", "jun@test.com"),
                new User("min", "password", "sangmin", "min@test.com")
        );
        mav.setModel(new HashMap<String, Object>(){{
            put("users", users);
        }});
        assertThat(resourceHandler.getResource(mav)).contains("hyunjun", "sangmin");
    }

}
