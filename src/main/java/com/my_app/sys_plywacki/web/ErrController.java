package com.my_app.sys_plywacki.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
