package http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("forward 관리 클래스")
class ForwardTest {

    @Test
    @DisplayName("forward 셋팅")
    void setForward() {
        Forward forward = Forward.init();

        assertThatCode(() -> forward.setForward("path")).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("forward 설정이 되어있는지 아닌지")
    void isForward() {
        Forward forward = Forward.init();

        assertThat(forward.isForward()).isFalse();

        forward.setForward("hi");
        assertThat(forward.isForward()).isTrue();
    }

    @Test
    @DisplayName("forward 가져오기")
    void getForward() {
        Forward forward = Forward.init();

        forward.setForward("hi");
        assertThat(forward.getForward()).isEqualTo("hi");
    }
}
