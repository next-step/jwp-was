package model.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpStatus;
import webserver.http.Protocol;
import webserver.http.Type;
import webserver.http.Version;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Http 응답 파싱 테스트")
class ResponseLineTest {

    @Test
    @DisplayName("Get 응답 라인 생성")
    void parseGetResponse() {
        HttpStatus httpStatus = HttpStatus.OK;

        ResponseLine responseLine = new ResponseLine(httpStatus);

        assertThat(responseLine.getProtocol()).isEqualTo(new Protocol(Type.HTTP, Version.VERSION1_1));
        assertThat(responseLine.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(responseLine.toString()).isEqualTo("HTTP/1.1 200 OK");
    }
}
