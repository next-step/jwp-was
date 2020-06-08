package common;

import annotations.Controller;
import annotations.RequestMapping;
import web.servlet.ModelAndView;

@Controller
public class CommonController {

    @RequestMapping("/index.html")
    public ModelAndView index() {
        return ModelAndView.from("index");
    }
}
