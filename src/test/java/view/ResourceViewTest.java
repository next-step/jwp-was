package view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceViewTest {

    @DisplayName("uri에 슬래시(/)만 입력해도 /index.html 경로로 설정한다")
    @Test
    void uri에_슬래시만_입력해도_index_html로_설정() throws IOException, URISyntaxException {
        // given
        // when
        ResourceView resourceView = new ResourceView("/");

        // then
        assertThat(resourceView.getPath()).isEqualTo("/index.html");
    }

    @DisplayName("uri가 슬래시로 시작하지 않으면 슬래시를 붙여준다")
    @Test
    void uri가_슬래시로_시작하지_않으면_슬래시를_붙여준다() throws IOException, URISyntaxException {
        // given
        // when
        ResourceView resourceView = new ResourceView("index.html");

        // then
        assertThat(resourceView.getPath()).isEqualTo("/index.html");
    }

    @DisplayName("확장자를 생략하면 html을 붙여준다")
    @Test
    void 확장자를_생략하면_html을_붙여준다() throws IOException, URISyntaxException {
        // given
        // when
        ResourceView resourceView = new ResourceView("index");

        // then
        assertThat(resourceView.getPath()).isEqualTo("/index.html");
    }
}