package header;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by youngjae.havi on 2019-08-03
 */
public class Cookie implements HeaderSetter<Cookie> {
    private Map<String, String> cookieMap = new HashMap<>();

    @Override
    public Cookie setEliment(String[] keyValue) {
        String[] values = keyValue[1].split("=");
        IntStream.range(0, values.length)
                .filter(i -> i%2 == 0 && i+1 < values.length)
                .forEach(i -> cookieMap.put(values[i].trim(), values[i+1]));
        return this;
    }

    public String get(String key) {
        return cookieMap.get(key);
    }
}
