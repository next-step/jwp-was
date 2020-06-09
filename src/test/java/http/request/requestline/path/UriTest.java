package http.request.requestline.path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UriTest {

    @DisplayName("Uri 경로가 파일 확장자를 가진 경로인지 체크하기")
    @Test
    void hasExtension() {
        /* given */
        Uri uri1 = new Uri("/users");
        Uri uri2 = new Uri("/index.html");

        /* when */ /* then */
        assertThat(uri1.hasExtension()).isFalse();
        assertThat(uri2.hasExtension()).isTrue();
    }

    @DisplayName("파일 경로 구하기")
    @Test
    void getFilePath() {
        /* given */
        Uri uri1 = new Uri("/users");
        Uri uri2 = new Uri("/index.html");

        /* when */
        String filePath1 = uri1.getFilePath();
        String filePath2 = uri2.getFilePath();

        /* then */
        assertThat(filePath1).isEqualTo("/users");
        assertThat(filePath2).isEqualTo("./templates/index.html");
    }
}