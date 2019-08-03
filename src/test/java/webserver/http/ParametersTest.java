package webserver.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParametersTest {
    @ParameterizedTest
    @ValueSource(strings = {"field1=value1&field2=value2"})
    void 객체생성(String queryString) {
        //when
        Parameters parameters = Parameters.newInstance(queryString);

        //then
        assertThat(parameters.getParameters().size()).isEqualTo(2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"field1=&", "=value1", "field1==value1", "&"})
    void 잘못된_패턴검사(String queryString) {
        //when & then
        assertThrows(IllegalArgumentException.class,
                () -> Parameters.newInstance(queryString));
    }

    @Test
    void queryString이_없는경우() {
        //when
        Parameters parameters = Parameters.emptyInstance();

        //then
        assertThat(parameters.isEmpty()).isTrue();
    }
}
