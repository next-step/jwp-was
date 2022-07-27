package webserver.http.request;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class URITest {

    @DisplayName("Parameter에 저장된 key 에 해당하는 value를 가져온다.")
    @ParameterizedTest
    @MethodSource("ProvideForGetParameter")
    void getParameter(Parameters parameters, String expected) {
        String actual = new URI("/path", parameters).getParameter("key");
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> ProvideForGetParameter() {
        return Stream.of(
                arguments(
                        new Parameters(
                                Map.of(
                                        "키", Lists.list("밸류"),
                                        "key", Lists.list("value")
                                )
                        ), "value"
                ),
                arguments(
                        new Parameters(
                                Map.of(
                                        "키", Lists.list("밸류"),
                                        "key", Lists.list("value2", "value3")
                                )
                        ), "value2"
                ),
                arguments(
                        new Parameters(
                                Map.of(
                                        "키", Lists.list("밸류"),
                                        "key", Lists.list()
                                )
                        ), null
                ),
                arguments(
                        new Parameters(
                                Map.of(
                                        "키", Lists.list("밸류")
                                )
                        ), null
                )
        );
    }
}