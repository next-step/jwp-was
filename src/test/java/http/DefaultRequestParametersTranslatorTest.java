package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DefaultRequestParametersTranslatorTest {

    private String queryString;

    @BeforeEach
    void setUp() {
        queryString = "userId=javajigi&password=password&name=JaeSung";
    }

    @Test
    @DisplayName("Map 사이즈 확인")
    void sizeByMap() {
        // give
        RequestParametersTranslator requestParametersTranslator = new DefaultRequestParametersTranslator(queryString);
        Map<String, String> keyValue = requestParametersTranslator.create();
        int actualSize = keyValue.size();
        // when
        boolean same = actualSize == 3;
        // then
        assertThat(same).isTrue();
    }

    @Test
    @DisplayName("요구사항 3 - Query String 파싱")
    void checkKeyAndValue() {
        // give
        RequestParametersTranslator requestParametersTranslator = new DefaultRequestParametersTranslator(queryString);
        Map<String, String> keyValue = requestParametersTranslator.create();

        assertAll("query key value",
                () -> assertThat(keyValue.containsKey("userId")).isTrue(),
                () -> assertThat(keyValue.get("userId")).isEqualTo("javajigi"),
                () -> assertThat(keyValue.containsKey("password")).isTrue(),
                () -> assertThat(keyValue.get("password")).isEqualTo("password"),
                () -> assertThat(keyValue.containsKey("name")).isTrue(),
                () -> assertThat(keyValue.get("name")).isEqualTo("JaeSung")
        );
    }
}
