package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName(("Attributes 단위 테스트"))
class AttributesTest {
    @DisplayName("Attributes를 생성한다.")
    @Test
    void create() {
        // given
        final Map<String, Object> attributesMap = Map.of("key1", "value1", "key2", "value2");

        // when
        final Attributes attributes = new Attributes(attributesMap);

        // then
        assertAll(
                () -> assertThat(attributes.getAttribute("key1")).isEqualTo("value1"),
                () -> assertThat(attributes.getAttribute("key2")).isEqualTo("value2")
        );
    }
}
