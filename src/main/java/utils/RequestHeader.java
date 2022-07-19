package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private String host;
    private String connection;
    private String contentLength;
    private String contentType;
    private String accept;

    private RequestHeader() {
    }

    private static class InnerInstanceRequestHeader {
        private static final RequestHeader instance = new RequestHeader();
    }

    public static RequestHeader getInstance() {
        return RequestHeader.InnerInstanceRequestHeader.instance;
    }

    public RequestHeader parsing(BufferedReader br) {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> headerInfo = new HashMap<>();

        try {
            br.readLine();
            String line = br.readLine();
            while (!line.equals("")) {
                System.out.println(line);
                String[] headerArr = line.split(":");
                headerInfo.put(headerArr[0], headerArr[1]);
                line = br.readLine();
            }
            RequestHeader requestHeader = objectMapper.convertValue(headerInfo, RequestHeader.class);
            logger.debug("requestHeader : {}", requestHeader);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
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
