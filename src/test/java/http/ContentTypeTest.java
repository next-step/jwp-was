package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Content type enum test")
class ContentTypeTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("확장자에 따라서 타입을 잘 가져오는지")
    void of(final String extension, final ContentType expected) {
        assertThat(ContentType.of(extension)).isEqualTo(expected);
    }

    private static Stream<Arguments> of() {
        return Stream.of(
                Arguments.of("text", ContentType.TEXT),
                Arguments.of("html", ContentType.HTML),
                Arguments.of("css", ContentType.CSS),
                Arguments.of("js", ContentType.JS),
                Arguments.of("png", ContentType.IMAGE_PNG),
                Arguments.of("", ContentType.TEXT),
                Arguments.of(null, ContentType.TEXT)
        );
    }
}