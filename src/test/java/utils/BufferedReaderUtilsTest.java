package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static dummy.HttpRequestHeaderDummy.HTTP_REQUEST_HEADER_STRING_DUMMY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BufferedReaderUtilsTest {
    @Test
    @DisplayName("lines는 br의 모든 라인을 리스트 String 형태로 반환한다.")
    void linesTest() throws IOException {
        int mockStringSize = 13;
        String LINE_SEPARATOR = "\n";
        String FIST_LINE = "GET /jason/test/ HTTP/1.1";
        int FIRST_LINE_INDEX = 0;
        String httpRequestHeaderStringDummy = HTTP_REQUEST_HEADER_STRING_DUMMY;

        InputStream inputStream = new ByteArrayInputStream(httpRequestHeaderStringDummy.getBytes());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        List<String> result = BufferedReaderUtils.lines(bufferedReader);

        assertAll(
            () -> assertThat(result.size()).isEqualTo(mockStringSize),
            () -> assertThat(httpRequestHeaderStringDummy.split(LINE_SEPARATOR)[FIRST_LINE_INDEX]).isEqualTo(FIST_LINE)
        );
    }
}
