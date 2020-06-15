package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {
    private RequestLine sut;

    @Test
    void new_GET_method_requestLine() {
        // given
        final Method method = Method.GET;
        final String path = "/test";
        final String queryParamKey1 = "a";
        final String queryParamVaue1 = "1";
        final String queryParamKey2 = "b";
        final String queryParamVaue2 = "2";
        final String queryString = "?" + queryParamKey1 + "=" + queryParamVaue1 + "&" + queryParamKey2 + "=" + queryParamVaue2;
        final String protocol = "HTTP";
        final String version = "1.1";

        String requestLine = method.name() + " " + path + queryString + " " + protocol + "/" + version + "\n";

        // when
        RequestLine req = sut.from(requestLine);

        // then
        assertThat(req .getMethod()).isEqualTo(method);
        assertThat(req .getPath()).isEqualTo(path);
        assertThat(req .getQueryMap().containsKey(queryParamKey1)).isEqualTo(true);
        assertThat(req .getQueryMap().containsKey(queryParamKey2)).isEqualTo(true);
        assertThat(req .getQueryMap().get(queryParamKey1)).isEqualTo(queryParamVaue1);
        assertThat(req .getQueryMap().get(queryParamKey2)).isEqualTo(queryParamVaue2);
        assertThat(req .getProtocol()).isEqualTo(protocol);
        assertThat(req .getVersion()).isEqualTo(version);
    }

    @Test
    void new_GET_method_requestLine_isEmpty() {
        // given
        String httpRawText = "";

        // when
        RequestLine httpProtocol = sut.from(httpRawText);

        // then
        assertThat(httpProtocol).extracting("class").isEqualTo(RequestLine.EmptyRequsetLine.class);
    }

    @Test
    void new_POST_method_requestLine() {
        // given
        final Method method = Method.POST;
        final String path = "/";
        final String protocol = "HTTP";
        final String version = "1.1";

        String requestLine = method.name() + " " + path + " " + protocol + "/" + version + "\n";

        // when
        RequestLine req = sut.from(requestLine);

        // then
        assertThat(req .getMethod()).isEqualTo(Method.POST);
        assertThat(req .getPath()).isEqualTo(path);
        assertThat(req .getProtocol()).isEqualTo(protocol);
        assertThat(req .getVersion()).isEqualTo(version);
    }
}