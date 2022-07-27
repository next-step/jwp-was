package utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Map;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    public void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readData(br, data.length()));
    }

    @Test
    @DisplayName("queryString을 맵으로 변경")
    public void changeStringToMap() {
        String queryString = "userId=abc&password=123&name=java";
        Map<String, String> map = IOUtils.changeStringToMap(queryString);
        Assertions.assertThat(map.get("userId")).isEqualTo("abc");
        Assertions.assertThat(map.get("password")).isEqualTo("123");
        Assertions.assertThat(map.get("name")).isEqualTo("java");
    }
}
