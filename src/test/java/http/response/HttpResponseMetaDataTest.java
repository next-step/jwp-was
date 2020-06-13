package http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseMetaDataTest {

    @DisplayName("Cookie 응답값 한줄로 만들기")
    @Test
    void generateCookieValue() {
        /* given */
        HttpResponseMetaData metaData = new HttpResponseMetaData();
        metaData.addCookie("logined", String.valueOf(true));
        metaData.addCookie("Path", "/");

        /* when */
        String cookieValue = metaData.generateCookieValue();

        /* then */
        assertThat(cookieValue).isEqualTo("logined=true; Path=/");
    }

    @DisplayName("Cookie값 없는 경우 빈 값 반환")
    @Test
    void emptyCookieValue() {
        /* given */
        HttpResponseMetaData metaData = new HttpResponseMetaData();

        /* when */
        String cookieValue = metaData.generateCookieValue();

        /* then */
        assertThat(cookieValue).isEmpty();
    }

    @DisplayName("Response Header Line 만들기")
    @Test
    void generateHeaderLine() {
        /* given */
        HttpResponseMetaData metaData = new HttpResponseMetaData();

        /* when */
        String headerLine = metaData.generateHeaderLine("Location", "/index.html");

        /* then */
        assertThat(headerLine).isEqualTo("Location: /index.html");
    }
}
