package http.requestline.path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileExtensionTest {

    @DisplayName("어떤 String이 FileExtension에 등록되어 있는 파일 확장자인지 체크하기")
    @Test
    void isFileExtension() {
        /* given */
        String text1 = "html";
        String text2 = "something";

        /* when */ /* then */
        assertThat(FileExtension.isFileExtension(text1)).isTrue();
        assertThat(FileExtension.isFileExtension(text2)).isFalse();
    }

    @DisplayName("String extension 이름으로 FileExtension 찾기")
    @Test
    void find() {
        /* given */
        String extensionName = "html";

        /* when */
        FileExtension fileExtension = FileExtension.find(extensionName).get();

        /* then */
        assertThat(fileExtension == FileExtension.HTML).isTrue();
    }
}