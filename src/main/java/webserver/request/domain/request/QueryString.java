package webserver.request.domain.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
@EqualsAndHashCode
public class QueryString {

    private static final String DELIMITER1 = "&";
    private static final String DELIMITER2 = "=";

    private Map<String, String> map = new HashMap();
    private String value;

    public QueryString(String value) {
        if(StringUtils.hasText(value)) {

            this.value = value;

            splitQueryString(value);
        }
    }

    private void splitQueryString(String value) {
        String[] arr = value.split(DELIMITER1);

        Arrays.stream(arr).map(str -> str.split(DELIMITER2)).forEach(strings -> map.put(strings[0], strings[1]));
    }

    public int getQueryStringPairs() {
        return map.size();
    }

    public Map<String, String> getDataPairs() {
        return map;
    }
}
