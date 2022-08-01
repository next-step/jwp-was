package webserver.view;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ViewResolverTest {

    @DisplayName("view 이름에 redirect 라는 접두사가 있으면 RedirectView 를 반환한다.")
    @Test
    void redirectViewTest() {
        // given
        ViewResolver viewResolver = new ViewResolver("", "");

        // when
        View view = viewResolver.resolveView("redirect:/index.html");

        // then
        Assertions.assertThat(view).isInstanceOf(RedirectView.class);
        Assertions.assertThat(((RedirectView) view).getUrl()).isEqualTo("/index.html");
    }

    @DisplayName("view 이름이 redirect 라는 접두사가 없으면 HandleBarView 를 반환한다.")
    @Test
    void handleBarViewTest() {
        // given
        ViewResolver viewResolver = new ViewResolver("", "");

        // when
        View view = viewResolver.resolveView("/index");

        // then
        Assertions.assertThat(view).isInstanceOf(HandleBarView.class);
    }

}
