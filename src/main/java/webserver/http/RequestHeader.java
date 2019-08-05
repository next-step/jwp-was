package webserver.http;

import utils.HttpStringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestHeader {
    private Map<String, String> headerInfo;

    private RequestHeader(Map<String, String> headerInfo) {
        this.headerInfo = headerInfo;
    }

    public static RequestHeader newInstance(BufferedReader br) throws IOException {
        Map<String, String> requestHeader = new HashMap<>();
        String line;

        while (!"".equals(line = br.readLine())) {
            if (line == null) {
                break;
            }
            String[] headerData = HttpStringUtils.split(line, ": ");
            requestHeader.put(headerData[0], headerData[1]);
        }

        return new RequestHeader(requestHeader);
    }

    public String findByKey(String key) {
        return Optional.ofNullable(headerInfo.get(key))
                .orElseThrow(IllegalArgumentException::new);
    }
}
