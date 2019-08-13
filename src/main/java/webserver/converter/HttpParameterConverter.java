package webserver.converter;

import webserver.http.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class HttpParameterConverter extends HttpConverter{

    public static void HttpParameterParse(HttpRequest request, String[] separatorMethodPath){
        setMethodParse(request, separatorMethodPath);
    }

    private static void setMethodParse(HttpRequest request, String[] httpSplit){
        if(request.getMethod().equals("GET")){
            setGetMethodParameter(request, httpSplit);
        }

        if(request.getMethod().equals("POST")){
            setPostMethodParameter(request, httpSplit);
        }
    }

    private static void setGetMethodParameter(HttpRequest request, String[] httpSplit){
        String[] urlStr = httpSplit[0]
                .split(SEPARATOR)[1]
                .split(QUERY_PREFIX);
        if(urlStr.length > 1){
            setSeparatorParameter(request, urlStr[1]);
        }
    }

    private static void setPostMethodParameter(HttpRequest request, String[] httpSplit){
        boolean postParameterCheck = false;
        for (int i = 1; i < httpSplit.length; i++) {
            if(postParameterCheck){
                setSeparatorParameter(request, httpSplit[i]);
                break;
            }

            if(httpSplit[i].equals("")){
                postParameterCheck = true;
            }
        }
    }

    private static void setSeparatorParameter(HttpRequest request, String splitStr){
        HashMap<String, String> paramMap = new HashMap<>();
        String[] paramStr = splitStr.split(QUERY_DELIMITER);
        for (int i = 0; i < paramStr.length; i++) {
            String[] keyValueStr = paramStr[i].split(QUERY_KEY_VALUE_DELIMITER);
            paramMap.put(keyValueStr[0], setNullCheck(keyValueStr));
        }

        request.initRequestParameter(paramMap);
    }

    private static String setNullCheck(String[] keyValue){
        if(keyValue.length == 1){
            return null;
        }else{
            try {
                String decodeText = URLDecoder.decode(keyValue[1], "UTF-8");
                return decodeText;
            }catch (UnsupportedEncodingException e){
                return null;
            }
        }
    }

}
