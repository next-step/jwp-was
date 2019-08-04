package webserver.http;

import webserver.domain.HttpParseVO;

import java.util.HashMap;

public class HttpMethodParameter {

    public static void HttpParameterParse(HttpParseVO httpParseVO, String[] separatorMethodPath){
        setMethodParse(httpParseVO, separatorMethodPath);
    }

    private static void setMethodParse(HttpParseVO httpParseVO, String[] httpSplit){
        if(httpParseVO.getMethod().equals("GET")){
            setGetMethodParameter(httpParseVO, httpSplit);
        }else if(httpParseVO.getMethod().equals("POST")){
            setPostMethodParameter(httpParseVO, httpSplit);
        }
    }

    private static void setGetMethodParameter(HttpParseVO httpParseVO, String[] httpSplit){
        String[] urlStr = httpSplit[0]
                .split(HttpRequest.SEPARATOR)[1]
                .split(HttpRequest.QUERY_PREFIX);
        if(urlStr.length > 1){
            setSeparatorParameter(httpParseVO, urlStr[1]);
        }
    }

    private static void setPostMethodParameter(HttpParseVO httpParseVO, String[] httpSplit){
        boolean postParameterCheck = false;
        for (int i = 1; i < httpSplit.length; i++) {
            if(postParameterCheck){
                setSeparatorParameter(httpParseVO, httpSplit[i]);
                break;
            }

            if(httpSplit[i].equals("")){
                postParameterCheck = true;
            }
        }
    }

    private static void setSeparatorParameter(HttpParseVO httpParseVO, String splitStr){
        HashMap<String, String> paramMap = new HashMap<>();
        String[] paramStr = splitStr.split(HttpRequest.QUERY_DELIMITER);
        for (int i = 0; i < paramStr.length; i++) {
            String[] keyValueStr = paramStr[i].split(HttpRequest.QUERY_KEY_VALUE_DELIMITER);
            paramMap.put(keyValueStr[0], setNullCheck(keyValueStr));
        }

        httpParseVO.setParameter(paramMap);
    }

    private static String setNullCheck(String[] keyValue){
        return keyValue.length == 1 ? null : keyValue[1];
    }

}
