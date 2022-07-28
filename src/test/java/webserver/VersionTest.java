package webserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.domain.Version;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VersionTest {

    @ParameterizedTest
    @MethodSource("provideVersionTestData")
    void findVersion(String versionStr, Version expectedVersion) {

        Version actualVersion = Version.from(versionStr);

        assertThat(actualVersion).isEqualTo(expectedVersion);
    }

    public static Stream<Arguments> provideVersionTestData() {
        return Stream.of(
                Arguments.arguments("1.0", Version.ONE),
                Arguments.arguments("1.1", Version.ONE_DOT_ONE),
                Arguments.arguments("2", Version.TWO)
        );
    }

    @Test
    void findFailVersion() {
        assertThatThrownBy(()-> Version.from("3"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
