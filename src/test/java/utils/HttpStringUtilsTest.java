package utils;

import org.junit.jupiter.api.Test;

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
        String inputData = "a/b/c";

        //when
        String result = HttpStringUtils.splitAndFindByIndex(inputData, "/", 2);

        //then
        assertThat(result).isEqualTo(inputData.split("/")[2]);
    }
}
