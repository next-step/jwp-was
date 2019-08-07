package webserver;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.RequestLine;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {
    @ParameterizedTest
    @CsvSource({"GET / HTTP/1.1,GET,/", "POST /user/create HTTP/1.1,POST,/user/create"})
    void readHttpRequest(ArgumentsAccessor argumentsAccessor) throws IOException {
        //given
        InputStream inputStream = new ByteArrayInputStream(argumentsAccessor.getString(0).getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        //when
        RequestHandler requestHandler = new RequestHandler(null);
        RequestLine requestLine = requestHandler.readRequestLine(br);

        //then
        assertThat(requestLine.getMethod()).isEqualTo(argumentsAccessor.getString(1));
        assertThat(requestLine.getPath().getPath()).isEqualTo(argumentsAccessor.getString(2));
    }
}