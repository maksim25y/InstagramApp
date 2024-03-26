package com.example.GuitarApp.controllers;

import com.example.GuitarApp.models.Lesson;
import com.example.GuitarApp.repositories.LessonRepository;
import com.example.GuitarApp.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;

@Controller
public class MainController {
    private final LessonService lessonService;
    @Autowired
    public MainController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public String getMainPage(){
        return "index";
    }
    @PostMapping
    public String addNewUserOnLesson(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") Date date){
        System.out.println(date);
        Lesson lesson = new Lesson();
        lesson.setDate(date);
        lessonService.addLesson(lesson);
        return "redirect:/";
    }
}
