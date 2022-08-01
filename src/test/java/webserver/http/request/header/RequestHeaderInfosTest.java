package webserver.http.request.header;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.header.RequestHeaderInfos;

class RequestHeaderInfosTest {

    @DisplayName("헤더 값 파싱 테스트")
    @Test
    void headerInfosTest() {
        String header = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n";

        RequestHeaderInfos requestHeaderInfos = new RequestHeaderInfos(header.split("\n"));

        assertThat(requestHeaderInfos.contentsLength()).isEqualTo(59);
    }

    @DisplayName("헤더에 정보 없는 경우 디폴트 값 확인")
    @Test
    void emptyHeaderInfosTest() {
        RequestHeaderInfos requestHeaderInfos = new RequestHeaderInfos(new String[0]);

        assertThat(requestHeaderInfos.contentsLength()).isZero();
    }
}
