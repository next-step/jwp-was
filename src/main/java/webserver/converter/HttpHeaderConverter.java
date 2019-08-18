package webserver.converter;

import webserver.http.HttpRequest;
import webserver.http.HttpSession;
import webserver.http.HttpSessionManager;

import java.util.HashMap;

public class HttpHeaderConverter extends HttpConverter{


    public HttpHeaderConverter(HttpRequest httpRequest, String httpMsg) {
        setParseHeader(httpRequest, httpMsg);
    }

    private void setParseHeader(HttpRequest httpRequest, String httpMsg) {
        String[] httpSplit = httpMsg.split(QUERY_NEW_LINE);
        setHtmlHeaderMsgParsing(httpRequest, httpSplit);
        setHtmlOptionMsgParsing(httpRequest, httpSplit);
        setSession(httpRequest);
    }

    private void setHtmlHeaderMsgParsing(HttpRequest httpRequest, String[] httpSplit) {
        String[] separatorMethodPath = httpSplit[0].split(SEPARATOR);
        if(separatorMethodPath.length == 1){
            return;
        }

        httpRequest.initHeaderValue(separatorMethodPath[0],
                setSeparatorUrlParameter(separatorMethodPath[1]),
                separatorMethodPath[2]);

        HttpParameterConverter.HttpParameterParse(httpRequest, httpSplit);
    }

    private void setHtmlOptionMsgParsing(HttpRequest httpRequest, String[] str){
        HashMap<String, String> etcHeader = new HashMap<>();
        for (int i = 1; i < str.length; i++) {
            String[] keyValue = setSeparatorKeyValue(str[i]);
            if(keyValue != null){
                etcHeader.put(keyValue[0], keyValue[1]);
            }
        }
        httpRequest.initEtcHeaderParameter(etcHeader);
    }

    private String setSeparatorUrlParameter(String urlPath){
        return urlPath.split(QUERY_PREFIX)[0];
    }


    private String[] setSeparatorKeyValue(String line){
        String[] keyValueStr = line.split(QUERY_HEADER_KEY_VALUE);

        if(keyValueStr.length < 2){
            return null;
        }

        String[] kvArray = new String[2];
        kvArray[0] = keyValueStr[0];
        kvArray[1] = line.substring(
                line.indexOf(QUERY_HEADER_KEY_VALUE) + 2, line.length());

        return kvArray;
    }

    private void setSession(HttpRequest request){
        String cookies = request.getEtcHeader().get(COOKIE);
        if(cookies == null){
            return;
        }

        HashMap<String, String> cookieAttribute = new HashMap<>();

        String[] cookieArray = cookies.split(QUERY_COOKIE_SEPARATOR + SEPARATOR);
        for (int i = 0; i < cookieArray.length; i++) {
            String[] cookieValue = cookieArray[i].split(QUERY_KEY_VALUE_DELIMITER);
            initCookieValue(cookieAttribute, cookieValue);
        }

        HttpSession session;
        if(cookieAttribute.containsKey(SESSION_ID)){
            session = HttpSessionManager.getSession(cookieAttribute.get(SESSION_ID));
            request.addSession(session);
            return;
        }

        session = HttpSessionManager.newSession();
        request.addSession(session);
    }

    private void initCookieValue(HashMap<String, String> map, String[] cookieValue){
        if(cookieValue.length != 1){
            map.put(cookieValue[0], cookieValue[1]);
        }else{
            map.put(cookieValue[0], "");
        }
    }

}
