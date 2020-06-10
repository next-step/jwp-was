package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.exceptions.ErrorMessage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class FormDataTest {

    @Test
    void formDataCreateTest() {
        final String input = "userId=userId1&password=password1&name=name1&email=aa1%40aa.a";
        final Parameters formData = new Parameters(input);
    }

    @Test
    @DisplayName("FormData에서 이름으로 값을 가져올 수 있다")
    void test1() {
        final String input = "userId=userId1&password=password1&name=name1&email=aa1%40aa.a";
        final Parameters formData = new Parameters(input);

        final String userId = formData.getValue("userId");
        final String password = formData.getValue("password");
        final String name = formData.getValue("name");
        final String email = formData.getValue("email");

        assertThat(userId).isEqualTo("userId1");
        assertThat(password).isEqualTo("password1");
        assertThat(name).isEqualTo("name1");
        assertThat(email).isEqualTo("aa1@aa.a");
    }

    @Test
    @DisplayName("URL인코딩된 formData을 넣은 경우 가져올 때에는 URL디코딩되어 나온다")
    void getDecoded() throws UnsupportedEncodingException {
        final String key = "password";
        final String value = "p&a&s&==s";
        final String valueURLEncoded = URLEncoder.encode(value, "UTF-8");
        final String input = "userId=a&" + key + "=" + valueURLEncoded;
        final Parameters formData = new Parameters(input);

        final String result = formData.getValue(key);

        assertThat(result).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(strings = {"id=", "id= "})
    void formData(String input) {
        final Throwable thrown = catchThrowable(() -> new Parameters(input));

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(ErrorMessage.ILLEGAL_PARAMETER.getMessage());
    }

}