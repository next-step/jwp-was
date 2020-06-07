package utils;

import org.apache.logging.log4j.util.Strings;
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

    @DisplayName("QueryString 추출")
    @Test
    void getQueries(){
        //given
        String requestLine = "POST /user/create HTTP/1.1";
        String requestLine2 = "GET /user?userId=palmseung&name=seunghee";

        //when
        String queries = RequestLineUtils.getQueries(requestLine);
        String queries2 = RequestLineUtils.getQueries(requestLine2);

        //then
        assertThat(queries).isEqualTo(Strings.EMPTY);
        assertThat(queries2).isEqualTo("userId=palmseung&name=seunghee");
    }
}
