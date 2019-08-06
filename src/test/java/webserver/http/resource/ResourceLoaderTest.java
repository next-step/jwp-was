package webserver.http.resource;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.ModelAndView;
import webserver.resource.HandlebarsResourceLoader;
import webserver.resource.ResourceLoader;
import webserver.resource.StaticResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ResourceLoaderTest {

    @DisplayName("load static resource by path")
    @ParameterizedTest(name = "viewName: {0}")
    @ValueSource(strings = {"/test.js", "/test.css"})
    void loadStaticResource(String viewName) throws IOException {
        ResourceLoader resourceLoader = new StaticResourceLoader(getClass().getClassLoader());
        ModelAndView mav = new ModelAndView(viewName);
        assertThat(resourceLoader.getResource(mav)).contains("hello test");
    }

    @DisplayName("load handlebars resource by path")
    @ParameterizedTest(name = "viewName: {0}")
    @ValueSource(strings = {"/index.html", "/registration.html"})
    void loadHandlebarsResource(String viewName) throws IOException {
        ResourceLoader resourceLoader = new HandlebarsResourceLoader();
        ModelAndView mav = new ModelAndView(viewName);
        assertThat(resourceLoader.getResource(mav)).contains("hello test");
    }

    @DisplayName("load handlebars resource with attributes by path")
    @ParameterizedTest(name = "viewName: {0}")
    @ValueSource(strings = {"/profile.html"})
    void loadHandlebarsResourceWithAttribute(String viewName) throws IOException {
        ResourceLoader resourceLoader = new HandlebarsResourceLoader();
        ModelAndView mav = new ModelAndView(new HashMap<String, Object>(){{
            put("user", new User("jun", "password", "hyunjun", "test@test.com"));
        }}, viewName);
        assertThat(resourceLoader.getResource(mav)).contains("hyunjun");
    }

    @DisplayName("load handlebars resource with list attributes by path")
    @ParameterizedTest(name = "viewName: {0}")
    @ValueSource(strings = {"/list.html"})
    void loadHandlebarsResourceWithListAttribute(String viewName) throws IOException {
        ResourceLoader resourceLoader = new HandlebarsResourceLoader();
        List<User> users = asList(
                new User("jun", "password", "hyunjun", "jun@test.com"),
                new User("min", "password", "sangmin", "min@test.com")
        );
        ModelAndView mav = new ModelAndView(new HashMap<String, Object>(){{
            put("users", users);
        }}, viewName);
        assertThat(resourceLoader.getResource(mav)).contains("hyunjun", "sangmin");
    }

}
