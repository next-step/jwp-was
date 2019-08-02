package webserver.http;

import webserver.domain.abstractController;

import java.util.ArrayList;
import java.util.List;

/**
 * 호출 되는 함수를 저장
 */
public class RequestMethod {

    public static List<? extends abstractController> getMethodList(){
        List<? extends abstractController> methodHandlerVOS = new ArrayList<>();

        //controller 폴더를 찾아서 자동으로 등록

        return methodHandlerVOS;
    }

}
