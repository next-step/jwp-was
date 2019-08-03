package webserver.http;

import org.junit.jupiter.api.Test;
import request.RequestHeader;
import response.Response;
import handler.RequestEngine;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by youngjae.havi on 2019-08-03
 */
public class RequestStrategyTest {

    @Test
    void find_correct_strategy() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("GET /index.html HTTP/1.1\n" +
                "Host: www.nowhere123.com\n" +
                "Accept: image/gif, image/jpeg, */*\n" +
                "Accept-Language: en-us\n" +
                "Accept-Encoding: gzip, deflate\n" +
                "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)"));
        RequestHeader requestHeader = new RequestHeader(bufferedReader);

        RequestEngine requestEngine = new RequestEngine();
        Response response = requestEngine.run(requestHeader);

        assertThat(response.getBody()).isNotEmpty();
    }
}
