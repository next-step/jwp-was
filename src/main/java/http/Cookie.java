package http;

import org.springframework.util.StringUtils;

public class Cookie {
    private boolean logined;

    public Cookie(boolean logined) {
        this.logined = logined;
    }

    public static Cookie of(String value) {
        if (StringUtils.isEmpty(value))
            return null;
        String[] values = value.split(" ");
        if (values.length < 2)
            return null;
        String[] keyvalue = values[1].split("=");
        if (keyvalue.length == 2 && "logined".equals(keyvalue[0]))
            return new Cookie(Boolean.valueOf(keyvalue[1]));

        return null;
    }

    public boolean isLogined() {
        return logined;
    }
}
