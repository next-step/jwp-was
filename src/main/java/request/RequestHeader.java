package request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestHeader {
    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    @JsonProperty("Host")
    private String host;
    @JsonProperty("Connection")
    private String connection;
    @JsonProperty("Content-Length")
    private int contentLength;
    @JsonProperty("Content-Type")
    private String contentType;
    @JsonProperty("Accept")
    private String accept;

    private RequestHeader() {
    }

    private static class InnerInstanceRequestHeader {
        private static final RequestHeader instance = new RequestHeader();
    }

    public static RequestHeader getInstance() {
        return RequestHeader.InnerInstanceRequestHeader.instance;
    }

    public RequestHeader parsing(BufferedReader br) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> headerInfo = new HashMap<>();

        try {
            br.readLine();
            String line = br.readLine();
            while (!line.equals("")) {
                String[] headerArr = line.split(":");
                headerInfo.put(headerArr[0], headerArr[1].trim());
                line = br.readLine();
            }
            RequestHeader requestHeader = objectMapper.convertValue(headerInfo, RequestHeader.class);
            logger.debug("requestHeader : {}", requestHeader);
            return requestHeader;
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public String getHost() {
        return host;
    }

    public String getConnection() {
        return connection;
    }

    public int getContentLength() {
        return contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public String getAccept() {
        return accept;
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "host='" + host + '\'' +
                ", connection='" + connection + '\'' +
                ", contentLength='" + contentLength + '\'' +
                ", contentType='" + contentType + '\'' +
                ", accept='" + accept + '\'' +
                '}';
    }
}
