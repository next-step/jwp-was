package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.exceptions.ErrorMessage;
import webserver.exceptions.IllegalParameterException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ParametersTest {

    @Test
    @DisplayName("Parameters 객체를 생성할 수 있다")
    void createParameters() {
        final String input = "userId=userId1&password=password1&name=name1&email=aa1%40aa.a";
        new Parameters(input);
    }

    @Test
    @DisplayName("Parameters에서 이름으로 값을 가져올 수 있다")
    void getValue() {
        final String input = "userId=userId1&password=password1&name=name1&email=aa1%40aa.a";
        final Parameters parameters = new Parameters(input);

        final String userId = parameters.getValue("userId");
        final String password = parameters.getValue("password");
        final String name = parameters.getValue("name");
        final String email = parameters.getValue("email");

        assertThat(userId).isEqualTo("userId1");
        assertThat(password).isEqualTo("password1");
        assertThat(name).isEqualTo("name1");
        assertThat(email).isEqualTo("aa1@aa.a");
    }

    @Test
    @DisplayName("URL인코딩된 parameter를 넣은 경우 가져올 때에는 URL디코딩되어 나온다")
    void getDecoded() throws UnsupportedEncodingException {
        final String key = "password";
        final String value = "p&a&s&==s";
        final String valueURLEncoded = URLEncoder.encode(value, "UTF-8");
        final String input = "userId=a&" + key + "=" + valueURLEncoded;
        final Parameters parameters = new Parameters(input);

        final String result = parameters.getValue(key);

        assertThat(result).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(strings = {"id=", "id= "})
    @DisplayName("밸류가 공백이거나 존재하지 않는 파라미터로 parameter 생성 시 에러를 반환한다")
    void errorEmptyValue(String input) {
        final Throwable thrown = catchThrowable(() -> new Parameters(input));

        assertThat(thrown)
                .isInstanceOf(IllegalParameterException.class)
                .hasMessageContaining(ErrorMessage.ILLEGAL_PARAMETER.getMessage());
    }

}