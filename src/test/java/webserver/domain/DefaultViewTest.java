package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.resolvers.DefaultViewResolver;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DefaultViewTest {

    public static final String PREFIX = "prefix";
    public static final String VIEW_NAME = "viewName";
    public static final String SUFFIX = "suffix";

    @DisplayName("객체를 생성할 수 있다.")
    @Test
    void createDefaultView() {
        DefaultView createdView = new DefaultView(PREFIX, VIEW_NAME, SUFFIX);

        assertThat(createdView.getPrefix()).isEqualTo(PREFIX);
        assertThat(createdView.getViewName()).isEqualTo(VIEW_NAME);
        assertThat(createdView.getSuffix()).isEqualTo(SUFFIX);
    }

    @DisplayName("정적팩토리를 이용해 기본 타입 HTML용 객체를 만들 수 있다.")
    @Test
    void createDetailViewFromViewName() throws IOException, URISyntaxException {
        DefaultView indexView = DefaultView.createDefaultHtmlView("/index");
        DefaultViewResolver resolver = new DefaultViewResolver();

        byte[] bytes = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        String expectedIndexView = new String(bytes);

        assertThat(resolver.resolve(indexView)).hasToString(expectedIndexView);
    }

}
