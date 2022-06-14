package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.exception.IllegalRequestBodyException;
import webserver.request.exception.IllegalRequestBodyKeyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestBodyTest {

    @DisplayName("RequestBody 를 파싱해서 값을 가져올 수 있어야 한다.")
    @Test
    void parse() {
        RequestBody requestBody = RequestBody.from("userId=javajigi&password=password&name=JaeSung");
        assertAll(
                () -> assertThat(requestBody.get("userId")).isEqualTo("javajigi"),
                () -> assertThat(requestBody.get("password")).isEqualTo("password"),
                () -> assertThat(requestBody.get("name")).isEqualTo("JaeSung")
        );
    }

    @DisplayName("부적절한 RequestBody 일 경우, IllegalRequestBodyException 이 발생한다.")
    @Test
    void illegalQueryString() {
        assertThatThrownBy(
                () -> RequestBody.from("=&=")
        ).isInstanceOf(IllegalRequestBodyException.class);
    }

    @DisplayName("키에 대응되는 값이 존재하지 않으면, IllegalRequestBodyKeyException 이 발생한다.")
    @Test
    void illegalQueryStringKey() {
        RequestBody requestBody = RequestBody.from("");
        assertThatThrownBy(
                () -> requestBody.get("Illegal_Key")
        ).isInstanceOf(IllegalRequestBodyKeyException.class);
    }
}
