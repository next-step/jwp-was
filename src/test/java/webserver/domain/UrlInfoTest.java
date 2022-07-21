package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UrlInfoTest {

    @DisplayName("Path를 파싱한다.")
    @Test
    void path() throws Exception {
        UrlInfo urlInfo = new UrlInfo("/users?a=b");
        assertThat(urlInfo.getPath()).isEqualTo("/users");
    }

    @DisplayName("QueryString 을 파싱한다.")
    @Test
    void parameter() throws Exception {
        UrlInfo urlInfo = new UrlInfo("/users?a=b&c=d");
        assertThat(urlInfo.getParameters().getParameter("a")).isEqualTo("b");
        assertThat(urlInfo.getParameters().getParameter("c")).isEqualTo("d");

    }
}
