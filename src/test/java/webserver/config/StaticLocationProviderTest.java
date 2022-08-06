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
                    "/index.html -> ./templates/index.html",
                    "/favicon.ico -> ./templates/favicon.ico",
                    "/css/styles.css -> ./static/css/styles.css",
                    "/js/scripts.js -> ./static/js/scripts.js",
            },
            delimiterString = " -> ")
    @ParameterizedTest
    void staticLocationTest(String path, String location) {
        // given
        List<String> staticFileLocations = List.of("./templates", "./static");

        StaticLocationProvider staticLocationProvider = new StaticLocationProvider(staticFileLocations);

        // when
        String staticResourcePath = staticLocationProvider.getStaticResourcePath(path);

        // then
        Assertions.assertThat(staticResourcePath).isEqualTo(location);
    }

}
