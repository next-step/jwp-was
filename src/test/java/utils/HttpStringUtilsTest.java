package utils;

import converter.NullableConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpStringUtilsTest {
    @Test
    void split() {
        //given
        String inputData = "a b c";

        //when
        String[] result = HttpStringUtils.split(inputData, " ");

        //then
        assertThat(result.length).isEqualTo(3);
    }

    @Test
    void splitAndGetDataByIndex() {
        //given
        String inputData = "a b c";

        //when
        String result = HttpStringUtils.substring(inputData, HttpStringType.DLM_SPACE.getType(), 2);

        //then
        assertThat(result).isEqualTo(inputData.split(HttpStringType.DLM_SPACE.getType())[2]);
    }

    @ParameterizedTest
    @CsvSource({"logined=false, false"
            , " Idea-efa8df2d=1bb5567d-7139-475d-a323-ee4e3492e089; Idea-dcb08711=75f15d7b-5df3-4803-abcc-3cc01d84def2; logined=true, true"
            , " Idea-efa8df2d=1bb5567d-7139-475d-a323-ee4e3492e089; logined=true; Idea-dcb08711=75f15d7b-5df3-4803-abcc-3cc01d84def2, true"})
    void checkLoginCookie(ArgumentsAccessor argumentsAccessor) {
        //when
        boolean isLogined = HttpStringUtils.checkLoginCookie(argumentsAccessor.getString(0));

        //then
        assertThat(isLogined).isEqualTo(argumentsAccessor.getBoolean(1));
    }

    @ParameterizedTest
    @CsvSource({"null, null"
            , " logined=false, null"
            , " sessionId=1234, 1234"
            , " Idea-efa8df2d=1bb5567d-7139-475d-a323-ee4e3492e089; sessionId=1234;, 1234"})
    void checkSessionId(@ConvertWith(NullableConverter.class) String cookie
            , @ConvertWith(NullableConverter.class) String expectedId) {
        //when
        String actualId = HttpStringUtils.extractSessionId(cookie);

        //then
        assertThat(actualId).isEqualTo(expectedId);
    }

    @Test
    void addValue() {
        //given
        String values = null;
        String newValue = "sessionId=1234;";

        //when, then
        assertThat(HttpStringUtils.addValue(values, newValue)).isEqualTo(newValue);
    }
}
