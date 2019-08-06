package webserver.resolver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlebarsViewResolverTest {

    @DisplayName("If suffix exists, remove it")
    @ParameterizedTest
    @ValueSource(strings = {
            "index",
            "index.html",
            "/user/login.html",
    })
    void name(String viewName) throws Exception {
        ViewResolver viewResolver = new HandlebarsViewResolver();

        assertThat(viewResolver.resolve(viewName)).isNotEmpty();
        assertThat(viewResolver.resolve(new ModelAndView(viewName, "test data"))).isNotEmpty();
    }
}