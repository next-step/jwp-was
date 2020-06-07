package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineUtilsTest {
    @DisplayName("URL 추출")
    @Test
    void getUrl() {
        //given
        String requestLine = "POST /user/create HTTP/1.1";
        String requestLine2 = "GET /user?userId=palmseung&name=seunghee";

        //when
        String url = RequestLineUtils.getUrl(requestLine);
        String url2 = RequestLineUtils.getUrl(requestLine2);

        //then
        assertThat(url).isEqualTo("/user/create");
        assertThat(url2).isEqualTo("/user");
    }
}
