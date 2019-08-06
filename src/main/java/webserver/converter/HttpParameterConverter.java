package webserver.converter;

import webserver.domain.HttpEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class HttpParameterConverter extends HttpConverter{

    public static void HttpParameterParse(HttpEntity httpEntity, String[] separatorMethodPath){
        setMethodParse(httpEntity, separatorMethodPath);
    }

    private static void setMethodParse(HttpEntity httpEntity, String[] httpSplit){
        if(httpEntity.getMethod().equals("GET")){
            setGetMethodParameter(httpEntity, httpSplit);
        }

        if(httpEntity.getMethod().equals("POST")){
            setPostMethodParameter(httpEntity, httpSplit);
        }
    }

    private static void setGetMethodParameter(HttpEntity httpEntity, String[] httpSplit){
        String[] urlStr = httpSplit[0]
                .split(SEPARATOR)[1]
                .split(QUERY_PREFIX);
        if(urlStr.length > 1){
            setSeparatorParameter(httpEntity, urlStr[1]);
        }
    }

    private static void setPostMethodParameter(HttpEntity httpEntity, String[] httpSplit){
        boolean postParameterCheck = false;
        for (int i = 1; i < httpSplit.length; i++) {
            if(postParameterCheck){
                setSeparatorParameter(httpEntity, httpSplit[i]);
                break;
            }

            if(httpSplit[i].equals("")){
                postParameterCheck = true;
            }
        }
    }

    private static void setSeparatorParameter(HttpEntity httpEntity, String splitStr){
        HashMap<String, String> paramMap = new HashMap<>();
        String[] paramStr = splitStr.split(QUERY_DELIMITER);
        for (int i = 0; i < paramStr.length; i++) {
            String[] keyValueStr = paramStr[i].split(QUERY_KEY_VALUE_DELIMITER);
            paramMap.put(keyValueStr[0], setNullCheck(keyValueStr));
        }

        httpEntity.setParameter(paramMap);
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
