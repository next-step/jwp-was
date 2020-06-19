package utils;

import com.sun.jndi.toolkit.url.UrlUtil;

import java.net.MalformedURLException;

public class UrlUtils {
    private UrlUtils() {
    }

    public static String decode(String url) {
        try {
            return UrlUtil.decode(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException();
        }
    }
}
