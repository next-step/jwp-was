package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Header {
    private static final Logger logger = LoggerFactory.getLogger(Header.class);

    private Map<String, String> headerMap;

    public Header() {
        headerMap = Maps.newHashMap();
    }

    public Header(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public static Header parsing(BufferedReader br) throws IOException {
        Map<String, String> headerMap = new HashMap<>();
        try {
            br.readLine();
            String line = br.readLine();
            while (!StringUtils.isEmpty(line)) {
                String[] headerArr = line.split(": ");
                headerMap.put(headerArr[0], headerArr[1]);
                line = br.readLine();
            }
            logger.debug("Header : {}", headerMap);
            return new Header(headerMap);
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
