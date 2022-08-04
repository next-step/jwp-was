package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RequestUtils 테스트")
class RequestUtilsTest {

    @DisplayName("문자열을 Map 으로 변환한다.")
    @Test
    void toMap() {
        String requestData = "userId=javajigi&password=password&name=JaeSung";
        Map<String, String> result = RequestUtils.createRequestDataMap(requestData);
        assertThat(result).contains(
                Map.entry("userId","javajigi"),
                Map.entry("password","password"),
                Map.entry("name","JaeSung")
        );
    }


}
