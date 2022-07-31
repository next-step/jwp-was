package utils;

import java.util.HashMap;
import java.util.Map;

import static utils.DelimiterConstants.PARAMETER_KEY_VALUE_DELIMITER;
import static utils.DelimiterConstants.QUERY_STRING_DELIMITER;

public class RequestUtils {

    public static Map<String, String> createRequestDataMap(String stringParameters) {
        Map<String, String> map = new HashMap<>();
        String[] splitParameters = stringParameters.split(QUERY_STRING_DELIMITER);
        for (String parameter : splitParameters) {
            String[] splitParameter = parameter.split(PARAMETER_KEY_VALUE_DELIMITER);
            map.put(splitParameter[0], splitParameter[1]);
        }
        return map;
    }
}
