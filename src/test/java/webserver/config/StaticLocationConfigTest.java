package webserver.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class StaticLocationConfigTest {

    @DisplayName("html 파일은 ./templates 폴더, ico 파일은 ./static/images, 나머지 파일은 ./static 폴더 하위에 있다.")
    @CsvSource(
            value = {
                    "html -> ./templates",
                    "ico -> ./static/images",
                    "css -> ./static",
                    "js -> ./static",
            },
            delimiterString = " -> ")
    @ParameterizedTest
    void staticLocationTest(String ext, String location) {
        // given
        StaticLocationConfig staticLocationConfig = new StaticLocationConfig();

        // when
        String staticLocation = staticLocationConfig.getStaticLocation(ext);

        // then
        Assertions.assertThat(staticLocation).isEqualTo(location);
    }

}