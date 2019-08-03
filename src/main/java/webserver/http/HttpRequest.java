package webserver.http;

import java.util.HashMap;

public class HttpRequest {

    private static final String SEPARATOR = " ";
    private static final String QUERY_PREFIX = "\\?";
    private static final String QUERY_DELIMITER = "&";
    private static final String QUERY_KEY_VALUE_DELIMITER = "=";

    public String httpMethod;
    public String httpPath;
    public HashMap<String, String> httpParameter;

    public HttpRequest(String httpMsg) {
        String[] separatorMsg = httpMsg.split(SEPARATOR);
        httpMethod = separatorMsg[0];
        httpPath = separatorMsg[1];
        getSeparatorParameter();
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getHttpPath() {
        return httpPath;
    }

    public String getHttpParameterValue(String keyStr){
        if(httpParameter.containsKey(keyStr))
            return httpParameter.get(keyStr);
        else return null;
    }

    public void getSeparatorParameter() {
        String[] paramStr = httpPath.split(QUERY_PREFIX)[1].split(QUERY_DELIMITER);

        HashMap<String, String> paramMap = new HashMap<>();
        for (int i = 0; i < paramStr.length; i++) {
            String[] keyValueStr = paramStr[i].split(QUERY_KEY_VALUE_DELIMITER);
            paramMap.put(keyValueStr[0], nullCheck(keyValueStr));
        }

        httpParameter = paramMap;
    }

    private String nullCheck(String[] keyValue){
        return keyValue.length == 1 ? null : keyValue[1];
    }

}
