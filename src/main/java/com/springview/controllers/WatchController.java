package com.springview.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WatchController {

    @RequestMapping("/")
    public String index() {
        return "watch.html";
    }
}
