package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("경로")
class PathTest {

    @Test
    @DisplayName("생성")
    void instance() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> Path.from("/users")),
                () -> assertThatNoException().isThrownBy(Path::root)
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("값이 없으면 루트 경로")
    void instance_empty_equalRoot(String pathString) {
        assertThat(Path.from(pathString)).isEqualTo(Path.root());
    }
}
