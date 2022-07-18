package utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    public void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readData(br, data.length()));
    }

	@DisplayName("inputstream을 읽을 수 있다.")
	@Test
	void testReadData() {
		String input = "test1\ntest2\ntest3";
		var inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

		var actual = IOUtils.readData(inputStream);

		assertThat(actual).containsExactly("test1", "test2", "test3");
	}
}
