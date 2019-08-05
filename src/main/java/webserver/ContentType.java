package webserver;

import java.util.HashMap;
import java.util.Map;

public enum ContentType {

    NONE("none"),
    X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded");


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

    ContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
