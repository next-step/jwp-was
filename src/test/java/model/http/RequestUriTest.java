package model.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestUriTest {
    @ParameterizedTest
    @ValueSource(strings = {"/",
            "/user?ssosso1=ssosso1&s_2=s__2",
            "/user/Id/test-test/?ssosso1=ssoss2",
            "/user/id"})
    void of(String requestUri) {
        RequestUri.of(requestUri);
    }

    @Test
    void appendQuery() {
        UriPath uriPath = UriPath.of("/user/create");
        List<QueryParameter> queryParameters = Arrays.asList(QueryParameter.of("ssosso", "nice"), QueryParameter.of("hey", "like"));

        RequestUri requestUriGiven = RequestUri.of(uriPath, Query.ofEmpty());
        RequestUri requestUriExpected = RequestUri.of(uriPath, Query.of(queryParameters));

        Query queryForAdd = Query.of(queryParameters);

        assertThat(requestUriGiven.appendQuery(queryForAdd)).isEqualTo(requestUriExpected);
    }
}
