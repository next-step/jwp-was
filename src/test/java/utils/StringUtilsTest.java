package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;


public class StringUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(StringUtilsTest.class);

    @DisplayName("StringUtil isEmpty Test : null value")
    @ParameterizedTest
    @NullSource
    public void nullTest(String str) throws Exception {
        assertThat(StringUtils.isEmpty(str)).isEqualTo(Boolean.TRUE);
    }

    @DisplayName("StringUtil isEmpty Test : empty String")
    @ParameterizedTest
    @ValueSource(strings = "")
    public void emptyStringTest(String str) throws Exception {
        assertThat(StringUtils.isEmpty(str)).isEqualTo(Boolean.TRUE);
    }

    @DisplayName("StringUtil isEmpty Test : not empty String")
    @ParameterizedTest
    @ValueSource(strings = "1")
    public void notEmptyStringTest(String str) throws Exception {
        assertThat(StringUtils.isEmpty(str)).isEqualTo(Boolean.FALSE);
    }
}
