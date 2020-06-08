package http.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("forward 관리 클래스")
class ForwardTest {
    private Forward forward;

    @BeforeEach
    void init() {
        this.forward = new Forward();
    }

    @Test
    @DisplayName("forward 셋팅")
    void setForward() {
        assertThatCode(() -> forward.setForward("path")).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("forward 설정이 되어있는지 아닌지")
    void isForward() {
        assertThat(forward.isForward()).isFalse();

        forward.setForward("hi");
        assertThat(forward.isForward()).isTrue();
    }

    @Test
    @DisplayName("forward 가져오기")
    void getForward() {
        forward.setForward("hi");
        assertThat(forward.getForward()).isEqualTo("hi");
    }
}
