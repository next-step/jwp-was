package user;

import annotations.Controller;
import annotations.RequestMapping;
import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.servlet.ModelAndView;

/**
 * @author KingCjy
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/form.html")
    public ModelAndView userForm() {
        return ModelAndView.from("user/form");
    }

    @RequestMapping(value = "/create", method = HttpMethod.POST)
    public ModelAndView signUp(HttpRequest httpRequest) {

        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");

        userService.signUp(userId, password, name, email);

        return ModelAndView.from("redirect:/index.html");
    }

    @RequestMapping("/login.html")
    public ModelAndView loginView() {
        return ModelAndView.from("user/login");
    }

    @RequestMapping("/login_failed.html")
    public ModelAndView loginFailView() {
        return ModelAndView.from("user/login_failed");
    }

    @RequestMapping(value = "/login", method = HttpMethod.POST)
    public ModelAndView login(HttpRequest httpRequest, HttpResponse httpResponse) {

        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        boolean login = userService.login(httpResponse, userId, password);

        if(!login) {
            return ModelAndView.from("redirect:/user/login_failed.html");
        }

        return ModelAndView.from("redirect:/index.html");
    }

    @RequestMapping("/list.html")
    public ModelAndView userListView(HttpRequest httpRequest) {

        boolean isLoggedIn = userService.isLoggedIn(httpRequest);

        if(!isLoggedIn) {
            return ModelAndView.from("redirect:/user/login.html");
        }

        ModelAndView modelAndView = ModelAndView.from("user/list");
        modelAndView.addAttribute("users", userService.findAllUsers());
        return modelAndView;
    }
}
