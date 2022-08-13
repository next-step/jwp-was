package webserver.request;

import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class RequestBody {

    private static final String DELIMITER1 = "&";
    private static final String DELIMITER2 = "=";

    private Map<String, String> map = new HashMap();

    public RequestBody(String value) {
        if (StringUtils.hasText(value)) {
            splitBody(value);
        }
    }

    private void splitBody(String value) {
        String[] arr = value.split(DELIMITER1);

        Arrays.stream(arr).map(str -> str.split(DELIMITER2)).forEach(strings ->
        {
            if(strings.length == 2) {
                map.put(strings[0], strings[1]);
            }
        });
    }

    public String getParameter(String parameter) {
        return map.get(parameter);
    }

    public Map<String, String> getDataPairs() {
        return map;
    }
}
