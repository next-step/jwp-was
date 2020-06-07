package utils;

import http.Headers;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.tika.Tika;

public class HttpUtils {

    public static String getMimeType(String fileName) {
        return new Tika().detect(fileName);
    }

    public static Map<String, String> getPairs(String raw, String firstSplitter, String second) {
        String[] sources = raw.split(firstSplitter);

        Map<String, String> pairs = new HashMap<>();
        for (String source : sources) {
            String[] values = source.split(second);
            if (values.length != 2) {
                continue;
            }

            try {
                pairs.put(URLDecoder.decode(values[0], "UTF-8"),
                    URLDecoder.decode(values[1].trim(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                continue;
            }
        }

        return pairs;
    }

    public static Map<String, String> getPair(String raw, String splitter) {
        Map<String, String> pair = new HashMap<>();

        if (StringUtils.isEmpty(raw)) {
            return pair;
        }
        String[] values = raw.split(splitter);
        if (values.length != 2) {
            return pair;
        }

        pair.put(values[0].trim(), values[1].trim());
        return pair;
    }
}
