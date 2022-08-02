package enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResourceIdentifierTest {
    @Test
    @DisplayName("Resource 타입이 포함된 Strig을 던지면 true를 반환한다.")
    void anyMatchTest() {
        assertAll(
            () -> assertThat(ResourceIdentifier.anyMatch("test.svg")).isTrue(),
            () -> assertThat(ResourceIdentifier.anyMatch("test.")).isFalse()
        );
    }
}