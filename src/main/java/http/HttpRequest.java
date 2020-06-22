package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.RequestHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final RequestLine requestLine;
    private final QueryStrings queryStrings;
    private final HttpHeaders headers;
    private Map<String, String> params = new HashMap<>();

    public HttpRequest(InputStream in) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        String line = br.readLine();

        this.requestLine = RequestLineParser.parse(line);

        Map<String, String> header = new HashMap<>();


        line = br.readLine();
        while (!"".equals(line)) {
            logger.debug("header : {} ", line);
            String[] token = line.split(": ");
            header.put(token[0], token[1]);
            line = br.readLine();
        }

        this.headers = new HttpHeaders(header);
        this.queryStrings = requestLine.getQueryString();
        if(this.requestLine.getMapping().isPost()) {
            String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
            params = new QueryStrings().getParseQuery(body);
        }
    }

    public String getPath(){
        return requestLine.getPath();
    }

    public String getHeader(String param) {
        return headers.get(param);
    }

    public String getQurey(String param){
        return queryStrings.getParameter(param);
    }

    public String getParameter(String param) {
        return params.get(param);
    }

    public HttpMethod getMethod() {
        return  requestLine.getMapping();
    }
}
