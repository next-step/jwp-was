package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FormDataTest {

    @Test
    void FormData를_넣으면_이름으로_값을_가져올_수_있다() {
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

}