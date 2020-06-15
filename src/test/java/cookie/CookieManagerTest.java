package cookie;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CookieManagerTest {

    @Test
    void create() {
        String cookiesString = "remember-me=RnVaJTJCMUhyb1lpJTJCMWtRbE1KSGZEd3clM0QlM0Q6akklMkJwNGxlYkNNSDc1aFdUQjZMSSUyQnclM0QlM0Q; SESSIONID=7316c614-0558-4615-96b1-5a38f3390622";

        Cookies cookies = CookieManager.read(cookiesString);

        assertThat(cookies).isNotEqualTo(null);
        assertThat(cookies.getCookie("SESSIONID")).isEqualTo("7316c614-0558-4615-96b1-5a38f3390622");
        assertThat(cookies.getCookie("remember-me")).isEqualTo("RnVaJTJCMUhyb1lpJTJCMWtRbE1KSGZEd3clM0QlM0Q6akklMkJwNGxlYkNNSDc1aFdUQjZMSSUyQnclM0QlM0Q");
    }

    @Test
    void nullCheck() {
        Cookies cookies = CookieManager.read(null);

        assertThat(cookies).isNotEqualTo(null);
    }
}
