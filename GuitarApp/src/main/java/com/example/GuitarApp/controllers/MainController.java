package com.example.GuitarApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @GetMapping
    public String getMainPage(){
        return "index";
    }
    @PostMapping
    public String addNewUserOnLesson(){
        return "redirect:";
    }
}
