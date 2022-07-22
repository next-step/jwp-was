package webserver.http;

import http.httprequest.requestline.ProtocolType;
import http.httprequest.requestline.Version;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import http.httprequest.requestline.Protocol;

import static org.assertj.core.api.Assertions.assertThat;

class ProtocolTest {

    @Test
    @DisplayName("from 메서드가 정상적으로 객체를 생성해주는지 확인")
    void create() {
        Protocol expected = new Protocol(ProtocolType.HTTP, new Version("1.1"));

        Protocol result =  Protocol.from("HTTP/1.1");

        assertThat(result).isEqualTo(expected);
    }
}
