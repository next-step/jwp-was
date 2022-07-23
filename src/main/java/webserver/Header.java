package request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestHeader {
    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private Map<String, String> headerMap;

    public RequestHeader() {
        headerMap = Maps.newHashMap();
    }

    public RequestHeader(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public static RequestHeader parsing(BufferedReader br) throws IOException {
        Map<String, String> headerMap = new HashMap<>();
        try {
            br.readLine();
            String line = br.readLine();
            while (!line.equals("")) {
                String[] headerArr = line.split(":");
                headerMap.put(headerArr[0], headerArr[1].trim());
                line = br.readLine();
            }
            logger.debug("requestHeader : {}", headerMap);
            return new RequestHeader(headerMap);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }


    public String get(String key) {
        return this.headerMap.get(key);
    }

    public void set(String key, String value) {
        this.headerMap.put(key, value);
    }

    public int getContentLength() {
        String contentLength = headerMap.get("Content-Length");
        if (Objects.isNull(contentLength)) {
            return 0;
        }
        return Integer.parseInt(this.headerMap.get("Content-Length"));
    }

    public Cookie parseCookie() {
        return new Cookie(headerMap.get("Cookie"));
    }
}
