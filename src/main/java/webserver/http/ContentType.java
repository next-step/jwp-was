package webserver.http;

import utils.ParsingUtils;
import webserver.http.request.RequestHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static utils.IOUtils.readData;

public enum ContentType {

    NONE("none"),
    X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded") {
        @Override
        public HttpParameter to(BufferedReader reader, RequestHeader requestHeader) throws IOException {
            String query = readData(reader, requestHeader.getContentLength());
            return new HttpParameter(ParsingUtils.parseUrl(query));
        }
    };

    private String type;

    private static Map<String, ContentType> contentTypeMap = new HashMap<>();

    static {
        for (ContentType contentType : values()) {
            contentTypeMap.put(contentType.getType(), contentType);
        }
    }

    public static ContentType getByType(String type) {
        if (type != null && contentTypeMap.containsKey(type)) {
            return contentTypeMap.get(type);
        }

        return NONE;
    }

    public HttpParameter to(BufferedReader reader, RequestHeader requestHeader) throws IOException {
        return new HttpParameter(Collections.emptyMap());
    }

    ContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
