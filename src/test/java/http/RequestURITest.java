package http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class RequestURITest {

    @ParameterizedTest
    @ValueSource(strings = {"/", "/users", "/users?userId=javajigi", "/users?userId=javajigi&password=password&name=JaeSung"})
    @DisplayName("유효한 입력값으로 RequestURI을 생성 시에 정상적으로 RequestURI가 생성되는지 테스트")
    void createRequestURITest(String uriInput) {
        RequestURI requestURI = new RequestURI(uriInput);
        assertThat(requestURI).isEqualTo(new RequestURI(uriInput));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/users?"})
    @DisplayName("유효하지 않은 입력값으로 RequestURI을 생성 시에 지정한 예외가 발생하는지 테스트")
    void createInvalidRequestURITest(String uriInput) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new RequestURI(uriInput);
        }).withMessage(RequestURI.ILLEGAL_URI);
    }
}
