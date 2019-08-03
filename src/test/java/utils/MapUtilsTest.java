package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MapUtilsTest {

    @DisplayName("does not put if blank key")
    @ParameterizedTest(name = "case : {0} -> key : {1}, value : {2}")
    @MethodSource("sampleKeyValues")
    void endSplitTest(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        MapUtils.putIfKeyNotBlank(map, key, value);
        assertTrue(map.isEmpty());
    }

    private static Stream<Arguments> sampleKeyValues() {
        return Stream.of(
                Arguments.of("", "value"),
                Arguments.of(null, "value")
        );
    }

}
