package http;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestHeaderTest {

  @Test
  @DisplayName("RequestHeader 파싱")
  void parseTest() {

    StringBuffer request = new StringBuffer();
    request.append("Host: localhost:8080\n");
    request.append("Connection: keep-alive\n");
    request.append("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)\n");
    request.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9\n");
    request.append("Referer: http://localhost:8080/user/form.html\n");
    request.append("Accept-Encoding: gzip, deflate, br\n");
    request.append("Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n");
    BufferedReader bufferedReader = new BufferedReader(new StringReader(request.toString()));

    RequestHeader requestHeader = null;
    try {
      requestHeader = RequestHeader.parse(bufferedReader);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertThat(requestHeader.getHost()).isEqualTo("localhost:8080");
    assertThat(requestHeader.getConnection()).isEqualTo("keep-alive");
    assertThat(requestHeader.getUserAgent()).isEqualTo("Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
    assertThat(requestHeader.getAccept())
        .isEqualTo("text/html,application/xhtml+xml,application/xml;q=0.9");
    assertThat(requestHeader.getReferer()).isEqualTo("http://localhost:8080/user/form.html");
    assertThat(requestHeader.getAcceptEncoding()).isEqualTo("gzip, deflate, br");
    assertThat(requestHeader.getAcceptLanguage()).isEqualTo("ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");

  }

  @Test
  void name() {
    String aa = "Cookie: _ga=GA1.1.119138890.1541999795; Idea-c6c45020=4670ba4e-13af-4efe-aaab-aea22f033243; Webstorm-36797b5a=62a29650-07f7-489f-a7cb-75bcef8d91f3";
    System.out.println(aa.split(": ")[0]);
    System.out.println(aa.split(": ")[1]);
  }
}
