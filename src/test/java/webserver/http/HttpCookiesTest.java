package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpCookiesTest {



    @DisplayName("HttpCookies 파싱 테스트 : cookie 확인")
    @ParameterizedTest
    @CsvSource({"test1,test1Value", "test2,test2Value"})
    public void httpRequestCookiesTest(String cookieName, String cookieValue){
        try{
            HttpCookies httpCookies = HttpCookies.of("test1=test1Value; test2=test2Value;");
            assertThat(httpCookies).isNotNull();
            assertThat(httpCookies.getCookie(cookieName)).isNotNull();
            assertThat(httpCookies.getCookie(cookieName).getValue()).isEqualTo(cookieValue);
        } catch (Exception e) {
            assertThat(e).doesNotThrowAnyException();
            assertThat(e).hasNoCause();
        }

    }


}
