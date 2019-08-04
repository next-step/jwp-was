package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import db.DataBase;
import model.User;
import utils.FileIoUtils;
import webserver.domain.ControllerCreator;
import webserver.domain.HttpParseVO;
import webserver.domain.HttpStatus;
import webserver.http.HttpRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserController implements ControllerCreator {

    static final List<String> USER_CONTROLLER_PATH =
            Arrays.asList("/user/create", "/user/login", "/user/list");

    private static UserController userController;
    private HttpParseVO httpParseVO;

    public UserController(HttpParseVO httpParseVO) {
        this.httpParseVO = httpParseVO;
    }

    public static UserController newInstance(HttpParseVO httpParseVO){
        if(userController == null){
            userController = new UserController(httpParseVO);
        }

        return userController;
    }

    public static boolean getUserControllerUrl(String urlPath){
        long existPathCount = USER_CONTROLLER_PATH.stream()
                .filter(str -> str.equals(urlPath))
                .count();

        return (existPathCount == 0) ? false : true;
    }

    @Override
    public void doPost() {
        if(httpParseVO.getUrlPath().equals("/user/create")){
            User user = new User(httpParseVO.getParameter().get("userId"),
                    httpParseVO.getParameter().get("password"),
                    httpParseVO.getParameter().get("name"),
                    httpParseVO.getParameter().get("email"));
            DataBase.addUser(user);

            HttpStatus.setRedirect(httpParseVO,
                    HttpRequest.BASIC_URL + "/index.html");
        }

        if(httpParseVO.getUrlPath().equals("/user/login")){
            User user = DataBase.findUserById(httpParseVO
                    .getParameter().get("userId"));
            if(user != null){
                HttpStatus.setRedirect(httpParseVO,
                        HttpRequest.BASIC_URL + "/index.html");
                httpParseVO.setCookie("logined=true; Path=/");
            }else{
                HttpStatus.setRedirect(httpParseVO,
                        HttpRequest.BASIC_URL + "/user/login_failed.html");
                httpParseVO.setCookie("logined=false; Path=/user/login_failed.html");
            }
        }
    }

    @Override
    public void doGet() {
        if(httpParseVO.getUrlPath().equals("/user/list")){
            try {
                httpParseVO.setUrlPath(httpParseVO.getUrlPath() + HttpRequest.HTML_FILE_NAMING);
                readFileHtml();

                Handlebars handlebars = new Handlebars();
                Template template = handlebars.
                        compileInline(httpParseVO.getReturnContent());
                List<User> userList = DataBase.findAll()
                        .stream().collect(Collectors.toList());
                String remakeContent = template.apply(userList);
                httpParseVO.setReturnContent(remakeContent);
            }catch (Exception e){
                HttpStatus.setPageNotFond(httpParseVO);
            }
        }
    }

    private void readFileHtml() throws IOException, URISyntaxException {
        httpParseVO.setReturnContent(
                new String(FileIoUtils.loadFileFromClasspath(
                    HttpRequest.BASIC_TEMPLATE_PATH +
                            httpParseVO.getUrlPath())));
    }
}
