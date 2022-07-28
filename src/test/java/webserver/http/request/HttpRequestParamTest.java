package webserver.http.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestParamTest {

    @Test
    void create() {
        HttpRequestParam httpRequestParam = HttpRequestParam.from("queryName=queryValue");

        assertThat(httpRequestParam.getName()).isEqualTo("queryName");
        assertThat(httpRequestParam.getValue()).isEqualTo("queryValue");
    }

    @Test
    void create_only_queryName() {
        HttpRequestParam httpRequestParam = HttpRequestParam.from("queryName=");

        assertThat(httpRequestParam).isEqualTo(HttpRequestParam.EMPTY);
    }

    @Test
    void isNotEmpty() {
        HttpRequestParam httpRequestParam = HttpRequestParam.from("");
        HttpRequestParam httpRequestParam2 = HttpRequestParam.from(null);

        assertThat(httpRequestParam.isNotEmpty()).isFalse();
        assertThat(httpRequestParam2.isNotEmpty()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"queryName=queryValue=1", "queryValue", " "})
    void create_exception(String queryString) {
        assertThat(HttpRequestParam.from(queryString)).isEqualTo(HttpRequestParam.EMPTY);
    }
}
