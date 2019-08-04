package webserver.http;

import webserver.controller.UserController;
import webserver.domain.HttpParseVO;

public class HttpControllerManage {

    public static void Call(HttpParseVO httpParseVO) {
        String method = httpParseVO.getMethod();

        if(UserController.getUserControllerUrl(httpParseVO.getUrlPath())){
            if(method.equals("GET")){
                UserController.newInstance().doGet(httpParseVO);
                //new UserController(httpParseVO).doGet();
            }else if(method.equals("POST")){
                UserController.newInstance().doPost(httpParseVO);
                //new UserController(httpParseVO).doPost();
            }
        }
    }
}
