package webserver.http;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import db.DataBase;
import model.User;
import utils.FileIoUtils;
import webserver.domain.HttpParseVO;
import webserver.domain.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class HttpRequest {

    public static final String SEPARATOR = " ";
    public static final String QUERY_PREFIX = "\\?";
    public static final String QUERY_DELIMITER = "&";
    public static final String QUERY_KEY_VALUE_DELIMITER = "=";
    public static final String QUERY_NEW_LINE = "\\r\\n";
    public static final String QUERY_HEADER_KEY_VALUE = ":";

    public static final String HTML_FILE_NAMING = ".html";
    public static final String CSS_FILE_NAMING = ".css";
    public static final String ICO_FILE_NAMING = ".ico";
    public static final String JS_FILE_NAMING = ".js";

    public static final String BASIC_TEMPLATE_PATH = "./templates/";
    public static final String BASIC_RESOURCE_PATH = "./static/";
    public static final String BASIC_URL = "http://localhost:8080";

    private HttpParseVO httpParseVO;
    private final String httpMsg;

    public HttpRequest(String httpMsg) {
        this.httpMsg = httpMsg;
        httpParseVO = new HttpParseVO();
        setParseHeader();
    }

    public HttpParseVO getHttpParseVO() {
        return httpParseVO;
    }

    private void setParseHeader(){
        String[] httpSplit = httpMsg.split(QUERY_NEW_LINE);
        setHtmlHeaderMsgParsing(httpSplit);
        setHtmlOptionMsgParsing(httpSplit);
    }

    private void setHtmlHeaderMsgParsing(String[] httpSplit){
        String[] separatorMethodPath = httpSplit[0].split(SEPARATOR);
        if(separatorMethodPath.length == 1){
            return;
        }

        httpParseVO.setMethod(separatorMethodPath[0]);
        httpParseVO.setUrlPath(setSeparatorUrlParameter(separatorMethodPath[1]));
        httpParseVO.setVersion(separatorMethodPath[2]);
        HttpMethodParameter.HttpParameterParse(httpParseVO, httpSplit);

        if(httpParseVO.getUrlPath().indexOf(HTML_FILE_NAMING) != -1 ||
                httpParseVO.getUrlPath().indexOf(ICO_FILE_NAMING) != -1) {
            try {
                readFileHtml();
            } catch (Exception e) {
                HttpStatus.setPageNotFond(httpParseVO);
            }
        }else if(httpParseVO.getUrlPath().indexOf(CSS_FILE_NAMING) != -1 ||
                httpParseVO.getUrlPath().indexOf(JS_FILE_NAMING) != -1){
            try {
                readFileCSS();
            } catch (Exception e) {
                HttpStatus.setPageNotFond(httpParseVO);
            }
        }else{
            HttpControllerManage.Call(httpParseVO);
        }
    }

    private void setHtmlOptionMsgParsing(String[] str){
        HashMap<String, String> etcHeader = new HashMap<>();
        for (int i = 1; i < str.length; i++) {
            String[] keyValue = setSeparatorKeyValue(str[i]);
            if(keyValue != null){
                etcHeader.put(keyValue[0], keyValue[1]);
            }
        }
        httpParseVO.setEtcHeader(etcHeader);
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

    private void readFileHtml() throws IOException, URISyntaxException {
        httpParseVO.setReturnContent(
                new String(FileIoUtils
                        .loadFileFromClasspath(
                                BASIC_TEMPLATE_PATH +
                                        httpParseVO.getUrlPath())));
    }

    private void readFileCSS() throws IOException, URISyntaxException {
        httpParseVO.setReturnContent(
                new String(FileIoUtils
                        .loadFileFromClasspath(
                                BASIC_RESOURCE_PATH +
                                        httpParseVO.getUrlPath())));
    }

    public String getParameterQuery(String query){
        return httpParseVO.getParameter().get(query);
    }
}
