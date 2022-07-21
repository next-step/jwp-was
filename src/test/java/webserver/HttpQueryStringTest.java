package webserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpQueryStringTest {

    @Test
    void create() {
        HttpQueryString httpQueryString = new HttpQueryString("queryName=queryValue");

        assertThat(httpQueryString.getName()).isEqualTo("queryName");
        assertThat(httpQueryString.getValue()).isEqualTo("queryValue");
    }

    @Test
    void create_only_queryName() {
        HttpQueryString httpQueryString = new HttpQueryString("queryName=");

        assertThat(httpQueryString.getName()).isEqualTo("queryName");
        assertThat(httpQueryString.getValue()).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"queryName=queryValue=1", "queryValue", "", " "})
    void create_exception(String queryString) {
        assertThrows(IllegalArgumentException.class, () -> new HttpQueryString(queryString));
    }
}
