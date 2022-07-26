package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecoderUtilsTest {

    @DisplayName("UTF8로 디코딩한 문자열을 리턴한다.")
    @ParameterizedTest
    @CsvSource(value = {"%EB%B0%95%EC%9E%AC%EC%84%B1:박재성", "%40:@"}, delimiter = ':')
    void getUTF8DecodedString(String input, String output){
        String decoded = DecoderUtils.decode(input);

        assertEquals(output, decoded);
    }
}
