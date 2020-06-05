package model;

import http.requests.HttpRequest;

public class UserParser {

    // TODO: 흠... user parser가 굳이 이걸 알아야하나.. 비즈니스 로직을 위한 컨트롤러 같은 친구가 필요한 듯..
    public static User parse(HttpRequest httpRequest) {
        final String userId = httpRequest.getAttributeFromForm("userId");
        final String password = httpRequest.getAttributeFromForm("password");
        final String name = httpRequest.getAttributeFromForm("name");
        final String email = httpRequest.getAttributeFromForm("email");
        return new User(userId, password, name, email);
    }
}
