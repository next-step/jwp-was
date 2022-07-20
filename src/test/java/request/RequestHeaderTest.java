package request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.RequestHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import static org.assertj.core.api.Assertions.*;

class RequestHeaderTest {
    RequestHeader requestHeader = RequestHeader.getInstance();

    @Test
    @DisplayName("요청에 대한 헤더값 파싱 잘 되는지 확인")
    void parsing() throws IOException {

        BufferedReader br = HelpData.postHelpData();

        RequestHeader parsingRequestHeader = requestHeader.parsing(br);
        assertThat(parsingRequestHeader.getHost()).isEqualTo("localhost");
        assertThat(parsingRequestHeader.getConnection()).isEqualTo("keep-alive");
        assertThat(parsingRequestHeader.getContentLength()).isEqualTo(59);
        assertThat(parsingRequestHeader.getContentType()).isEqualTo("application/x-www-form-urlencoded");
        assertThat(parsingRequestHeader.getAccept()).isEqualTo("*/*");
    }
}