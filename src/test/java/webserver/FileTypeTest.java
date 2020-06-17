package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class FileTypeTest {

    @DisplayName("파일이 존재하는 최상위 폴더의 위치 반환 - templates 위치 반환")
    @ParameterizedTest
    @EnumSource(value = FileType.class, names = {"HTML", "ICO"})
    void getLocation_templates(FileType fileType) {

        // when
        String location = fileType.getLocation();

        // then
        assertThat(location).isEqualTo("./templates");
    }

    @DisplayName("파일이 존재하는 최상위 폴더의 위치 반환 - static 위치 반환")
    @ParameterizedTest
    @EnumSource(value = FileType.class, names = {"CSS", "JS", "GIF", "PNG", "WOFF", "TTF"})
    void getLocation_static(FileType fileType) {

        // when
        String location = fileType.getLocation();

        // then
        assertThat(location).isEqualTo("./static");
    }
}