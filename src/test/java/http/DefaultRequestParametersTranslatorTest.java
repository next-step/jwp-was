package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DefaultQueryStringsTranslatorTest {

    private String queryString;

    @BeforeEach
    void setUp() {
        queryString = "userId=javajigi&password=password&name=JaeSung";
    }

    @Test
    @DisplayName("Map 사이즈 확인")
    void sizeByMap() {
        // give
        QueryStringsTranslator queryStringsTranslator = new DefaultQueryStringsTranslator(queryString);
        Map<String, String> keyValue = queryStringsTranslator.create();
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
        QueryStringsTranslator queryStringsTranslator = new DefaultQueryStringsTranslator(queryString);
        Map<String, String> keyValue = queryStringsTranslator.create();

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
