package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class RequestBody {
    private String body;

    private Map<String, String> parameter;
    public RequestBody() {
    }

    public RequestBody(Map<String, String> parameter) {
        this.parameter = parameter;
    }

    private static final Logger logger = LoggerFactory.getLogger(RequestBody.class);

    public void toBody(BufferedReader bufferedReader, HttpHeader httpHeader) throws IOException {

        String body = IOUtils.readData(bufferedReader, Integer.parseInt(httpHeader.getValue("Content-Length")));

        logger.debug("request Body : {}", body);

        this.body = body;
    }

    public Map<String, String> createParameter(String body) {
        QueryString queryString = QueryString.parse(body);
        return this.parameter = queryString.getQueryData();
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getParameter() {
        return parameter;
    }

    public String getParameterValue(String name) {
        return parameter.get(name);
    }


}
