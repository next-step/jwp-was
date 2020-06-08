package http.response;

import org.apache.logging.log4j.util.Strings;

public class Cookie {
    private String key;
    private String value;
    private String path;
    private String httpOnly;

    public Cookie(String key, String value, String path, boolean isHttpOnly) {
        this.key = key;
        this.value = value;
        this.path = path;
        this.httpOnly = assignHttpOnlyValue(isHttpOnly);
    }

    @Override
    public String toString() {
        return key + "=" + value + "; "
                +"Path=" + path + "; "
                + httpOnly;
    }

    private String assignHttpOnlyValue(boolean isHttpOnly){
        if(isHttpOnly){
            return "HttpOnly";
        }
        return Strings.EMPTY;
    }
}
