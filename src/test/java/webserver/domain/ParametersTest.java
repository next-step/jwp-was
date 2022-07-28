package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ParametersTest {
    private static final String QUERY_STRING = "/create?userId=javajigi&password=password&email=javajigi%40slipp.net";
    private static final String[][] QUERY_STRING_ARRAY = new String[3][2];

    @DisplayName("유효한 문자열으로 객체를 생성할 수 있다.")
    @Test
    void fromWithValidLine() {
        Parameters parameters = Parameters.from(QUERY_STRING);

        for (String[] pair : QUERY_STRING_ARRAY) {
            assertThat(parameters.get(pair[0])).isEqualTo(pair[1]);
        }
    }

    @DisplayName("정적팩토리를 이용해 값을 변경할 수 없는 비어있는 객체를 만들 수 있다.")
    @Test
    void emptyInstance() {
        Parameters parameters = Parameters.emptyInstance();

        assertAll(
                ()-> assertThat(parameters.getParameters()).isEmpty(),
                ()-> assertThatThrownBy(()-> parameters.addParameters("a", "b"))
                        .isInstanceOf(UnsupportedOperationException.class)

        );

    }

    @DisplayName("정적팩토리를 이용해 값을 변경할 수 없는 비어있는 객체를 만들 수 있다.")
    @Test
    void newInstance() {
        Parameters parameters = Parameters.newInstance();
        parameters.addParameters("a", "b");

        assertThat(parameters.get("a")).isEqualTo("b");
    }

}
