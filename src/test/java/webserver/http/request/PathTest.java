package webserver.http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class PathTest {
    List<String> nodes;
    Path path;

    @BeforeEach
    void setUp() {
        nodes = List.of("first", "second", "third");
        path = new Path(String.join(Path.PATH_DELIMITER, nodes), Queries.from(""));
    }

    @Test
    void 경로_계층_수_확인() {
        assertThat(path.getDepth()).isEqualTo(nodes.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void N번째_부분_경로_확인(int depth) {
        assertThat(path.getPartByDepth(depth).isPresent()).isTrue();
        assertThat(path.getPartByDepth(depth).get()).isEqualTo(nodes.get(depth));
    }

    @Test
    void 계층_외_부분_경로_확인_시_빈_옵셔널_반환() {
        assertThat(path.getPartByDepth(nodes.size()).isPresent()).isFalse();
    }

    @Test
    void 부분_경로_계층_확인() {
        nodes.forEach(node -> assertThat(path.getDepthOfPart(node)).isEqualTo(nodes.indexOf(node)));
    }

    @Test
    void 경로_외_부분_경로_계층_확인() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> path.getDepthOfPart("forth"))
                .withMessage("잘못된 부분 경로입니다.");
    }
}
