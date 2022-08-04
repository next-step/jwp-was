package controller;

import db.DataBase;
import dto.UserDto;
import request.HttpRequest;
import response.HttpResponse;
import utils.HandlerUtils;

import java.util.List;
import java.util.stream.Collectors;

public class UserListController extends AbstractController {
    private static final String LOGIN_VIEW = "/user/login.html";
    private static final String USER_LIST_VIEW = "user/list";
    private static final String TRUE = "true";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws Exception {
        if (!request.getCoookie().getLogined().equals(TRUE)) {
            response.sendRedirect(LOGIN_VIEW);
            return;
        }

        List<UserDto> userDtoList = DataBase.findAll()
                .stream()
                .map(user -> new UserDto(user.getUserId(), user.getName(), user.getEmail()))
                .collect(Collectors.toList());

        String contextPage = HandlerUtils.handle(USER_LIST_VIEW, userDtoList);

        response.forwardBody(contextPage);
    }
}
