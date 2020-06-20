package http;

import com.google.common.collect.Maps;
import org.springframework.util.StringUtils;
import utils.StringConstant;

import javax.annotation.Nullable;
import java.util.Map;

public class Cookies {
    private final Map<String, String> cookieMap = Maps.newHashMap();

    public Cookies(RequestHeaders requestHeaders) {
        String cookie = requestHeaders.get("Cookie");

        if (StringUtils.isEmpty(cookie)) {
            return;
        }

        String[] splitBySpace = cookie.split(StringConstant.SEMI_COLON);

        for (String s : splitBySpace) {
            if (StringUtils.isEmpty(s)) {
                continue;
            }

            String[] splitByEqualSign = s.split(StringConstant.EQUAL_SIGN);
            if (splitByEqualSign.length < 2) {
                continue;
            }

            String cookieName = splitByEqualSign[0].trim();
            String cookieValue = splitByEqualSign[1].trim();

            cookieMap.put(cookieName, cookieValue);
        }
    }

    @Nullable
    public String findCookieValue(@Nullable String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        return cookieMap.get(name);
    }
}
