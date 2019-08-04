package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static webserver.request.RequestBody.SEPARATOR;

class RequestBodyTest {

    @DisplayName("요청받은 body 구분자(" + SEPARATOR + ") 기준으로 데이터 파싱에 성공한다")
    @Test
    void parse_inputData_success() {
        // given
        String request = "userId=javajigi&email=javajigi%40slipp.net";

        // when
        RequestBody requestBody = RequestBody.parse(request);

        String userId = requestBody.get("userId");
        String email = requestBody.get("email");

        // then
        assertThat(userId).isEqualTo("javajigi");
        assertThat(email).isEqualTo("javajigi%40slipp.net");
    }
}