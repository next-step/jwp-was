package model.http;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {
    @Test
    void of() {
        RequestLine requestLine = RequestLine.of("GET /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getRequestUri()).isEqualTo(RequestUri.of("/users"));
        assertThat(requestLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP1_1);
    }

    @Test
    void appendQuery() {
        UriPath uriPath = UriPath.of("/user/create");
        List<QueryParameter> queryParameters = Arrays.asList(QueryParameter.of("ssosso", "nice"), QueryParameter.of("hey", "like"));

        RequestLine requestLineGiven = RequestLine.of(HttpMethod.GET, RequestUri.of(uriPath, Query.ofEmpty()), HttpVersion.HTTP1_1);
        RequestLine requestLineExpected = RequestLine.of(HttpMethod.GET, RequestUri.of(uriPath, Query.of(queryParameters)), HttpVersion.HTTP1_1);

        Query queryForAdd = Query.of(queryParameters);

        assertThat(requestLineGiven.appendQuery(queryForAdd)).isEqualTo(requestLineExpected);
    }
}
