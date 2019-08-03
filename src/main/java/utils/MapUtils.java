package utils;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import javafx.util.Pair;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapUtils {
    public static Map<String, String> keyValueMap(Stream<String> sourceStream, String separator) {
        return sourceStream.filter(StringUtils::isNotBlank)
                .map(headerLine -> StringParseUtils.makeKeyValuePair(headerLine, separator))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }
}
