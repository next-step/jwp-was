package webserver.http;

import org.junit.jupiter.api.Test;
import request.RequestHeader;
import response.Response;
import handler.StaticMappingHandler;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by youngjae.havi on 2019-08-03
 */
public class StaticMappingHandlerTest {

    @Test
    void style_sheet_mapping_test() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("GET ./css/style.css HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Accept: text/css,*/*;q=0.1\n" +
                "Connection: keep-alive"));
        RequestHeader requestHeader = new RequestHeader(bufferedReader);
        Response response = new StaticMappingHandler().request(requestHeader);
        String stringBody = new String(response.getBody());

        assertThat(stringBody).isNotEmpty();
    }
}
