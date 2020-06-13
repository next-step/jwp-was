package http.request;

import org.springframework.util.StringUtils;

public class Cookie {

    private String key;
    private String value;
    private String path;

    public Cookie(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }

    public String getFormat() {
        String format = String.format("%s=%s;", key, value);
        return format + (StringUtils.isEmpty(path) ? "" : String.format("Path=%s;", path));
    }
}
