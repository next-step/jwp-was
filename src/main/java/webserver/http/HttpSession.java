package webserver.http;

import webserver.converter.HttpConverter;

import java.util.*;

public class HttpSession {

    private String sessionId;
    private Map<String, Object> sessionAttributeMap;

    private static final String UUID_SEPARATOR = "-";
    private static final String UUID_REPLACE_STR = "";

    public HttpSession() {
        this.sessionId = UUID.randomUUID().toString().replace(UUID_SEPARATOR, UUID_REPLACE_STR);
        sessionAttributeMap = new HashMap<>();
    }

    public HttpSession(String sessionId){
        this.sessionId = sessionId;
        sessionAttributeMap = new HashMap<>();
    }

    public String getId(){
        return this.sessionId;
    }

    public Object getAttribute(String attributeName){
        if(this.sessionAttributeMap.containsKey(attributeName)){
            return sessionAttributeMap.get(attributeName);
        }

        return null;
    }

    public void setAttribute(String attributeName, Object value){
        sessionAttributeMap.put(attributeName, value);
    }

    public void removeAttribute(String attributeName){
        sessionAttributeMap.remove(attributeName);
    }

    public void invalidate(){
        sessionAttributeMap.clear();
    }

    public String getToString(){
        String attributeStr = "";
        List<String> keyValue = new ArrayList<>(sessionAttributeMap.keySet());
        for(String keyStr : keyValue){
            if(keyStr.equals(HttpConverter.SESSION_ID)){
                continue;
            }

            attributeStr += keyStr + HttpConverter.QUERY_KEY_VALUE_DELIMITER + sessionAttributeMap.get(keyStr)
                    + HttpConverter.QUERY_COOKIE_SEPARATOR + HttpConverter.SEPARATOR;
        }

        return HttpConverter.SESSION_ID + HttpConverter.QUERY_KEY_VALUE_DELIMITER + sessionId
                + HttpConverter.QUERY_COOKIE_SEPARATOR + HttpConverter.SEPARATOR
                + attributeStr;
    }
}
