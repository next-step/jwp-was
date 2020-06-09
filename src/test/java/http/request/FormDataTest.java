package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class FormDataTest {

    @Test
    void formDataCreateTest() {
        final String input = "userId=userId1&password=password1&name=name1&email=aa1%40aa.a";
        final FormData formData = new FormData(input);
    }

    @Test
    @DisplayName("FormData에서 이름으로 값을 가져올 수 있다")
    void test1() {
        final String input = "userId=userId1&password=password1&name=name1&email=aa1%40aa.a";
        final FormData formData = new FormData(input);

        final String userId = formData.getValue("userId");
        final String password = formData.getValue("password");
        final String name = formData.getValue("name");
        final String email = formData.getValue("email");

        assertThat(userId).isEqualTo("userId1");
        assertThat(password).isEqualTo("password1");
        assertThat(name).isEqualTo("name1");
        assertThat(email).isEqualTo("aa1@aa.a");
    }

    @ParameterizedTest
    @ValueSource(strings = {"id=", "id= "})
    void formData(String input) {
        final Throwable thrown = catchThrowable(() -> new FormData(input));

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("유효하지 않은 Form Data. " + input);
    }

}