package webserver.http.response;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseHeader {

    private static final String CONNECTION = "Connection";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String LOCATION = "location";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final Map<String, String> BASE_HEADERS = Map.of(
            CONNECTION, "Keep-Alive",
            CONTENT_TYPE, "text/html; charset=utf-8"
    );

    private Map<String, String> headers = new HashMap<>();

    public ResponseHeader() {}

    public ResponseHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    private static ResponseHeader baseResponseHeader(){
        return new ResponseHeader(Maps.newHashMap(BASE_HEADERS));
    }

    public static ResponseHeader of302(String redirectUrl){
        HashMap<String, String> responseHeader = Maps.newHashMap(BASE_HEADERS);
        responseHeader.put(LOCATION, "http://localhost:8888" + redirectUrl);
        return new ResponseHeader(responseHeader);
    }

    public static ResponseHeader of200() {
        return baseResponseHeader();
    }

    public String getLocation(){
        return headers.get(LOCATION);
    }

    public void setContentLength(String value) {
        this.headers.put(CONTENT_LENGTH, value);
        System.out.println("why??");
        System.out.println("why??");
    }

    public List<String> toPrint() {
        return headers.keySet().stream()
                .map(key -> key + ": " + headers.get(key) + "\r\n")
                .collect(Collectors.toList());
    }
}
