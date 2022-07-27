package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Cookie;
import webserver.Header;

import java.io.BufferedReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

class HeaderTest {

    @Test
    @DisplayName("요청에 대한 헤더값 파싱 잘 되는지 확인")
    void parsing() throws IOException {

        BufferedReader br = HelpData.postHelpData();

        Header parsingHeader = Header.parsing(br);
        assertThat(parsingHeader.get("Host")).isEqualTo("localhost:8080");
        assertThat(parsingHeader.get("Connection")).isEqualTo("keep-alive");
        assertThat(parsingHeader.get("Content-Length")).isEqualTo("93");
        assertThat(parsingHeader.get("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(parsingHeader.get("Accept")).isEqualTo("*/*");
        assertThat(parsingHeader.get("Cookie")).isEqualTo("_ga=GA1.1.1851084625.1652927218; _ga_C3QBWSBLPT=GS1.1.1652943473.2.0.1652943473.60; wcs_bt=s_1a7f4b55d0bb ");
    }

    @Test
    @DisplayName("요청 헤더에 대한 쿠키파싱")
    void parsing_cookie() throws IOException {
        BufferedReader br = HelpData.postHelpData();
        Header Header = webserver.Header.parsing(br);

        Cookie cookie = Header.parseCookie();

        assertThat(cookie.keySet().size()).isEqualTo(3);
        assertThat(cookie.getCookie("_ga")).isEqualTo("GA1.1.1851084625.1652927218");
        assertThat(cookie.getCookie("_ga_C3QBWSBLPT")).isEqualTo("GS1.1.1652943473.2.0.1652943473.60");
    }
}