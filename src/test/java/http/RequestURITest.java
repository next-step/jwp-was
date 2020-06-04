package http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class RequestURITest {

    @ParameterizedTest
    @ValueSource(strings = {"/", "/users", "/users?userId=javajigi", "/users?userId=javajigi&password=password&name=JaeSung"})
    void createRequestURITest(String uriInput) {
        RequestURI requestURI = new RequestURI(uriInput);
        assertThat(requestURI).isEqualTo(new RequestURI(uriInput));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/users?"})
    void createInvalidRequestURITest(String uriInput) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new RequestURI(uriInput);
        }).withMessage(RequestURI.ILLEGAL_URI);
    }
}
