package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class RequestBody {
    private String body;

    public RequestBody() {
    }

    private static final Logger logger = LoggerFactory.getLogger(RequestBody.class);

    public void toBody(BufferedReader bufferedReader, HttpHeader httpHeader) throws IOException {
        String body = IOUtils.readData(bufferedReader, Integer.parseInt(httpHeader.getValue("Content-Length")));

        logger.debug("request Body : {}", body);

        this.body = body;
    }

    public Map<String, String> getParameters(String body) {
        QueryString queryString = QueryString.parse(body);
        return queryString.getQueryData();
    }

    public String getBody() {
        return body;
    }
}
