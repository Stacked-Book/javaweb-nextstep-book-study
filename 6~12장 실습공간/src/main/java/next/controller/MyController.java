package next.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MyController {

    private static final Logger logger = LoggerFactory.getLogger(MyController.class);

    @RequestMapping("/users")
    public ModelAndView list(HttpServletRequest request,
                             HttpServletResponse response) {
        logger.debug("users findUserId");
        return new ModelAndView(new JspView("/users/list.jsp"));
    }

    @RequestMapping(value = "/users/show", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest request,
                             HttpServletResponse response) {
        logger.debug("users findUserID");
        return new ModelAndView(new JspView("/users/show.jsp"));
    }

    @RequestMapping(value = "/users/show", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request,
                             HttpServletResponse response) {
        logger.debug("users create");
        return new ModelAndView(new JspView("redirect:/users"));
    }
}
