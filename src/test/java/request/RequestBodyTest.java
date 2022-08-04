package request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestBodyTest {

    @Test
    @DisplayName("RequestBody 값을 추가합니다.")
    void addBodyTest() {
        String body = "userId=java&password=1111";

        RequestBody requestBody = RequestBody.empty();
        requestBody.addBody(body);

        Map<String, String> map = new HashMap<>();
        map.put("userId", "java");
        map.put("password", "1111");
        RequestBody 비교값 = new RequestBody(map);

        assertThat(requestBody).isEqualTo(비교값);
    }

    @Test
    @DisplayName("body 값을 반환합니다.")
    void getParameterTest() {
        String body = "userId=java&password=1111";

        RequestBody requestBody = RequestBody.empty();
        requestBody.addBody(body);

        assertThat(requestBody.getParameter("userId")).isEqualTo("java");
    }

}
