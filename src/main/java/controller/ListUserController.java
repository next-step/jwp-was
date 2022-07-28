package controller;

import db.DataBase;
import model.User;
import utils.HandlebarsUtils;
import webserver.dto.UserDto;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ListUserController extends AbstractController {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        String cookie = request.getHeader("Cookie");
        String[] splitCookie = cookie.split("=");
        if (splitCookie.length == 2 && Boolean.parseBoolean(splitCookie[1])) {
            Collection<User> users = DataBase.findAll();
            List<UserDto> userDtos = users.stream()
                    .map(user -> new UserDto(user.getUserId(), user.getName(), user.getEmail()))
                    .collect(Collectors.toList());

            response.forwardBody(HandlebarsUtils.template("user/list", userDtos));
            return;
        }
        response.sendRedirect("/user/login.html");
    }
}