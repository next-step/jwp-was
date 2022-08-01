package webserver.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.StaticLocationProvider;

import java.util.List;


class StaticLocationProviderTest {

    @DisplayName("html 파일은 ./templates 폴더, 나머지 파일은 ./static 폴더 하위에 있다.")
    @CsvSource(
            value = {
                    "/index.html -> ./templates",
                    "/favicon.ico -> ./templates",
                    "/css/styles.css -> ./static",
                    "/js/scripts.js -> ./static",
            },
            delimiterString = " -> ")
    @ParameterizedTest
    void staticLocationTest(String ext, String location) {
        // given
        List<String> staticFileLocations = List.of("./templates", "./static");

        StaticLocationProvider staticLocationProvider = new StaticLocationProvider(staticFileLocations);

        // when
        String staticLocation = staticLocationProvider.getStaticLocation(ext);

        // then
        Assertions.assertThat(staticLocation).isEqualTo(location);
    }

}
