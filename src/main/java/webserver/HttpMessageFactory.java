package webserver;

import http.RequestMessage;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpMessageFactory {

    public static RequestMessage makeRequestMessage(BufferedReader br) throws IOException {
        List<String> headers = new ArrayList<>();

        String line = br.readLine();

        while (!"".equals(line)) {
            headers.add(line);
            line = br.readLine();
        }

        return RequestMessage.of(headers);
    }
}
