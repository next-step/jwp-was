package http.response;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import http.HttpStatus;

class HttpResponseTest {

    private static final String PATH = HttpResponseTest.class.getResource(".").getPath() + "response.txt";

    @AfterEach
    void setUp() throws IOException {
        if (Files.exists(Paths.get(PATH))) {
            Files.delete(Paths.get(PATH));
        }
    }

    @Test
    void write() throws FileNotFoundException {
        // given
        var httpResponse = new HttpResponse(HttpStatus.OK, Map.of());
        var dataOutputStream = new DataOutputStream(new FileOutputStream(PATH));

        // when
        httpResponse.write(dataOutputStream);

        // then
        var bufferedReader = new BufferedReader(new FileReader(PATH));
        var actual = bufferedReader.lines()
            .collect(Collectors.toList());

        assertThat(actual.get(0)).isEqualTo("HTTP/1.1 200 OK");
    }
}