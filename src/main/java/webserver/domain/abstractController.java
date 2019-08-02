package webserver.domain;

import java.util.HashMap;

/**
 * 웹 기본 컨트롤러 구조
 */
public abstract class abstractController {

    abstract public String setMethodType();
    abstract public String setUrlPath();
    abstract public String setReturnType();
    abstract public String setReturnPath();
    abstract public Object doAction(HashMap<String, String> param);

}
