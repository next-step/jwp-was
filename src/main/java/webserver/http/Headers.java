package webserver.http;

import utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Headers {

    private static final String HEADER_LINE_SPLIT_SIGN = ":";

    private static final int HEADER_LINE_SPLIT_LIMIT = 2;

    private static final Pattern HEADER_LINE_PATTERN = Pattern.compile("^[^:\\s]+:.+");

    private final Map<String, String> headerValueByName;

    public Headers(){
        headerValueByName = new HashMap<>();
    }


    public void addHeaderLine(String headerLine) {

        String[] headerNameAndValue = parseHeaderLine(headerLine);
        setHeader(this.headerValueByName, headerNameAndValue[0], headerNameAndValue[1]);
    }

    private void setHeader(Map<String, String> headerValueByName, String name, String value) {

        if(StringUtils.isEmpty(value)) {
            return;
        }

        headerValueByName.put(name.toLowerCase(), value.trim());
    }

    private String[] parseHeaderLine(String headerLine) {

        if(!isValidHeaderLine(headerLine)) {
            throw new IllegalArgumentException("headerLine 이상함.");
        }

        return splitHeaderLine(headerLine);
    }

    private String[] splitHeaderLine (String headerLine) {
        return headerLine.split(HEADER_LINE_SPLIT_SIGN, HEADER_LINE_SPLIT_LIMIT);
    }

    private boolean isValidHeaderLine(String headerLine) {

        if(StringUtils.isEmpty(headerLine)) {
            return false;
        }

        if(!HEADER_LINE_PATTERN.matcher(headerLine).find()) {
            return false;
        }

        return true;
    }

    public String getHeaderValue(String name) {
        return this.headerValueByName.get(name);
    }
}
