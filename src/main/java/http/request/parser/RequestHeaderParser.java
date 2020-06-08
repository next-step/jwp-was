package http.request.parser;

import http.HeaderField;
import http.request.Header;
import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;

public class RequestHeaderParser {

    private static final String HEADER_NAME_VALUE_TOKENIZER = ":";

    public static Header parse(String headerLines) {
        Map<String, HeaderField> header = new HashMap<>();

        String[] headersStr = headerLines.split("\n");
        for (String headerStr : headersStr) {
            if (Strings.isBlank(headerStr)) {
                continue;
            }

            String[] h = headerStr.split(HEADER_NAME_VALUE_TOKENIZER,2);
            String headerName = h[0].trim();
            String headerValue = h[1].trim();

            HeaderField headerField = new HeaderField(headerName, headerValue);
            header.put(headerName, headerField);
        }
        return new Header(header);
    }
}
