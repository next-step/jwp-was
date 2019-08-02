package webserver.http;

import java.util.HashMap;

public class RequestLine {

    public String url;

    public RequestLine(String url){
        this.url = url;
    }

    public static RequestLine parse(String url){
        return new RequestLine(url);
    }

    public String getMethod(){
        return strSplit()[0];
    }

    public String getPath(){
        return strSplit()[1];
    }

    public String getVersion(){
        return strSplit()[2];
    }

    public String getParam(String param) {
        //? 기준으로 자르고, & 기준으로 한번더 잘라 파라미터를 분리 한다
        String[] paramStr = getPath().split("\\?")[1].split("&");

        HashMap<String, String> paramMap = new HashMap<>();
        for (int i = 0; i < paramStr.length; i++) {
            String[] keyValueStr = paramStr[i].split("=");
            paramMap.put(keyValueStr[0], nullCheck(keyValueStr));
        }

        return paramMap.get(param);
    }

    /**
     * URL 분리
     * @return
     */
    private String[] strSplit(){
        return url.split(" ");
    }

    /**
     * 파라미터 value 값 분리
     * @param keyValue
     * @return
     */
    private String nullCheck(String[] keyValue){
        return keyValue.length == 1 ? null : keyValue[1];
    }
}
