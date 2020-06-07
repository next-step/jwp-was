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
        String requestLine2 = "GET /user?userId=palmseung&name=seunghee HTTP/1.1";

        //when
        String url = RequestLineUtils.getUrl(requestLine);
        String url2 = RequestLineUtils.getUrl(requestLine2);

        //then
        assertThat(url).isEqualTo("/user/create");
        assertThat(url2).isEqualTo("/user");
    }

    @DisplayName("QueryString 추출")
    @Test
    void getQueries() {
        //given
        String requestLine = "POST /user/create HTTP/1.1";
        String requestLine2 = "GET /user?userId=palmseung&name=seunghee HTTP/1.1";

        //when
        String queries = RequestLineUtils.getQueries(requestLine);
        String queries2 = RequestLineUtils.getQueries(requestLine2);

        //then
        assertThat(queries).isEqualTo(Strings.EMPTY);
        assertThat(queries2).isEqualTo("userId=palmseung&name=seunghee");
    }

    @DisplayName("Protocol 추출")
    @Test
    void getProtocol() {
        //given
        String requestLine = "POST /user/create HTTP/1.1";
        String requestLine2 = "GET /user?userId=palmseung&name=seunghee HTTP/1.1";

        //when
        String protocol = RequestLineUtils.getProtocol(requestLine);
        String protocol2 = RequestLineUtils.getProtocol(requestLine2);

        //then
        assertThat(protocol).isEqualTo("HTTP");
        assertThat(protocol2).isEqualTo("HTTP");
    }

    @DisplayName("Version 추출")
    @Test
    void getVersion() {
        //given
        String requestLine = "POST /user/create HTTP/1.1";
        String requestLine2 = "GET /user?userId=palmseung&name=seunghee HTTP/1.1";

        //when
        String version = RequestLineUtils.getVersion(requestLine);
        String version2 = RequestLineUtils.getVersion(requestLine2);

        //then
        assertThat(version).isEqualTo("1.1");
        assertThat(version2).isEqualTo("1.1");
    }

    @DisplayName("Query String 보유 여부 검증")
    @Test
    void hasQueryStrings() {
        //given
        String requestLine = "POST /user/create HTTP/1.1";
        String requestLine2 = "GET /user?userId=palmseung&name=seunghee HTTP/1.1";

        //when
        boolean hasQueryStrings = RequestLineUtils.hasQueryStrings(requestLine);
        boolean hasQueryStrings2 = RequestLineUtils.hasQueryStrings(requestLine2);

        //then
        assertThat(hasQueryStrings).isFalse();
        assertThat(hasQueryStrings2).isTrue();
    }
}
