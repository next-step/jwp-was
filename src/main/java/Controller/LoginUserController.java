package Controller;

import com.google.common.collect.Maps;
import http.HttpRequest;
import http.HttpResponse;

import java.util.Map;

public class LoginUserController extends AbstractController {

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        Map<String, String> additionalHeaders = Maps.newHashMap();
        if (LoginChecker.isLogin(httpRequest)) {
            additionalHeaders.put("Set-Cookie", "logined=true; Path=/");
            return HttpResponse.redirectBy302StatusCode("/index.html", additionalHeaders);
        }

        additionalHeaders.put("Set-Cookie", "logined=false; Path=/");
        return HttpResponse.redirectBy302StatusCode("/user/login_fail.html", additionalHeaders);
    }
}
