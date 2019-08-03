package webserver.http;

import utils.FileIoUtils;
import webserver.domain.HttpParseVO;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestLine {
    public HttpRequest httpRequest;
    public String httpMsg;

    public HttpParseVO httpParseVO;
    public List<MethodHandlerVO> methodHandlerVOList;

    public RequestLine(String httpStr){
        httpParseVO = new HttpParseVO();
        methodHandlerVOList = new ArrayList<>();
        httpRequest = new HttpRequest(httpMsg);
        parseHeader(httpStr);
    }

    public static RequestLine parse(String url){
        return new RequestLine(url);
    }

    public HttpParseVO getParseResult(){
        return httpParseVO;
    }

    public String getParam(String param) {
        return httpParseVO.getParameter().get(param);
    }

    /**
     * 첫번째 라인 분리, 나머지 라인은 key-value 형식으로 저장
     * @param param
     */
    private void parseHeader(String param){
        String[] httpSplit = param.split("\\r\\n");
        init1stLine(httpSplit[0]);
        init2stLine(httpSplit);
    }

    /**
     * 첫번째 라인 파싱 및 파라미터 분리
     * @param str
     */
    private void init1stLine(String str){
        String[] splitStr = str.split(" ");

        //첫번째 라인 파싱
        httpParseVO.setMethod(splitStr[0]);
        httpParseVO.setUrlPath(splitStr[1]);
        httpParseVO.setVersion(splitStr[2]);

        HashMap<String, String> paramMap = new HashMap<>();

        //파라미터 분리
        String[] urlStr = splitStr[1].split("\\?");
        if(urlStr.length > 1){
            String[] paramStr = urlStr[1].split("&");
            for (int i = 0; i < paramStr.length; i++) {
                String[] keyValueStr = paramStr[i].split("=");
                paramMap.put(keyValueStr[0], nullCheck(keyValueStr));
            }
            httpParseVO.setParameter(paramMap);
        }

        //1. 경로에 .html이 없을 경우 페이지 호출이 아니다

        if(httpParseVO.getUrlPath().indexOf(".html") != -1){
            //해당 경로의 파일을 읽어드림
            try {
                readFileHtml();
            }catch (Exception e){
                httpParseVO.setReturnContent("404 Not Found");
                httpParseVO.setResultCode("404");
            }
        }else{

        }
    }

    /**
     * 나머지 라인을 파싱 한다
     * @param str
     */
    private void init2stLine(String[] str){
        HashMap<String, String> etcHeader = new HashMap<>();
        for (int i = 0; i < str.length; i++) {
            String[] keyValue = splitKeyValue(str[i]);
            if(keyValue != null)
                etcHeader.put(keyValue[0], keyValue[1]);
        }
        httpParseVO.setEtcHeader(etcHeader);
    }

    /**
     * 파라미터 value 값 분리
     * @param keyValue
     * @return
     */
    private String nullCheck(String[] keyValue){
        return keyValue.length == 1 ? null : keyValue[1];
    }

    /**
     * key-value 구분
     * @param line
     * @return
     */
    private String[] splitKeyValue(String line){
        String[] keyValueStr = line.split(":");

        if(keyValueStr.length < 2) return null;

        String[] kvArray = new String[2];
        kvArray[0] = keyValueStr[0];

        //':' 와 ' ' 이후의 텍스트만 가져온다.
        kvArray[1] = line.substring(line.indexOf(":") + 2, line.length());

        return kvArray;
    }

    /**
     * 해당 경로로 파일을 읽어드린다
     */
    private void readFileHtml() throws IOException, URISyntaxException {
        httpParseVO.setReturnContent(new String(FileIoUtils.loadFileFromClasspath("./templates/" + httpParseVO.getUrlPath())));
    }
}
