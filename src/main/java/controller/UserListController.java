package controller;

import db.DataBase;
import dto.UserDto;
import java.util.List;
import java.util.stream.Collectors;
import request.HttpRequest;
import response.HttpResponse;
import utils.HandlerUtils;

public class UserListController extends Controller {
    private static final String LOGIN_VIEW = "/user/login.html";

    @Override
    public HttpResponse doGet(HttpRequest request) throws Exception {
        if(!request.getCoookie().getLogined().equals("true")) {
            return HttpResponse.sendRedirect(LOGIN_VIEW);
        }

        List<UserDto> userDtoList = DataBase.findAll()
            .stream()
            .map(user -> new UserDto(user.getUserId(), user.getName(), user.getEmail()))
            .collect(Collectors.toList());

        String contextPage = HandlerUtils.handle("user/list", userDtoList);

        return HttpResponse.sendTemplate(contextPage);
    }
}
