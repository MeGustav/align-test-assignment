package com.megustav.align.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Pages controller
 *
 * @author MeGustav
 * 27/05/2018 22:29
 */
@Controller
@RequestMapping(path = "/")
public class PagesController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "pages/index";
    }

}
