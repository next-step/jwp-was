package webserver.domain;

import exception.HttpMethodNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpMethodTest {

    @DisplayName("존재하는 메서드는 성공적으로 리턴한다.")
    @ParameterizedTest
    @CsvSource(value = {"GET:GET", "POST:POST", "PATCH:PATCH", "PUT:PUT", "DELETE:DELETE"}, delimiter = ':')
    void existsHttpMethod(String input, String output){
        HttpMethod method = HttpMethod.from(input);

        assertEquals(output, method.name());
    }

    @DisplayName("존재하지 않는 메서드는 예외를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"GETT", "GE", "ET", "G"})
    void notExistsHttpMethod(String input){
        assertThatExceptionOfType(HttpMethodNotFound.class)
                .isThrownBy(() -> HttpMethod.from(input));
    }
}
