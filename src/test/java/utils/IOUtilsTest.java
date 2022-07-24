package utils;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class IOUtilsTest {

    @Test
    public void readWhileEmptyLine() throws Exception {
        String data = "GET /path HTTP/1.1\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: 35\r\n" +
                "\r\n";
        BufferedReader br = new BufferedReader(new StringReader(data));

        List<String> actual = IOUtils.readWhileEmptyLine(br);

        assertThat(actual).isEqualTo(
                Lists.list(
                        "GET /path HTTP/1.1",
                        "Content-Type: application/json",
                        "Content-Length: 35"
                )
        );
    }

    @Test
    public void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        String actual = IOUtils.readData(br, data.length());
        assertThat(actual).isEqualTo(data);
    }
}
