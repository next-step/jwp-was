package webserver.http.request.requestline;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpQueryStringsTest {

    @Test
    void create() {
        HttpQueryStrings httpQueryStrings = new HttpQueryStrings("queryValue=queryName&queryValue2=queryName2");
        assertThat(httpQueryStrings.get(0)).isEqualTo(HttpQueryString.from("queryValue=queryName"));
        assertThat(httpQueryStrings.get(1)).isEqualTo(HttpQueryString.from("queryValue2=queryName2"));
        assertThat(httpQueryStrings.getFullQueryString()).isEqualTo("?queryValue=queryName&queryValue2=queryName2");
    }

    @Test
    void create_has_only_primary_query_string() {
        HttpQueryStrings httpQueryStrings = new HttpQueryStrings("queryValue=queryName");
        assertThat(httpQueryStrings.get(0)).isEqualTo(HttpQueryString.from("queryValue=queryName"));
        assertThat(httpQueryStrings.getFullQueryString()).isEqualTo("?queryValue=queryName");
    }

    @Test
    void create_has_only_primary_query_string_name() {
        HttpQueryStrings httpQueryStrings = new HttpQueryStrings("queryValue=");
        assertThat(httpQueryStrings.get(0)).isEqualTo(HttpQueryString.from("queryValue="));
        assertThat(httpQueryStrings.getFullQueryString()).isEqualTo("?queryValue=");
    }


    @ParameterizedTest
    @ValueSource(strings = {""})
    void create_has_not_query_string(String fullQueryString) {
        HttpQueryStrings httpQueryStrings = new HttpQueryStrings(fullQueryString);
        assertThat(httpQueryStrings.get(0)).isEqualTo(HttpQueryString.EMPTY);
        assertThat(httpQueryStrings.getFullQueryString()).isEqualTo("");
    }
}
