package com.sigma.coursesMS.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


/*
* Exception handler
* Handle all exception could be thrown in system
* Send customized views to inform users of the exception message (but using user language)
* Last update 2/5/2023
*/
@ControllerAdvice
public class ExceptionHandlerController {

    // Handle different exception will be added here later




    // handle all exception if they are not handled explicitly
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
