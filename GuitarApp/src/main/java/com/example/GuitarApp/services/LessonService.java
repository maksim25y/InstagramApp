package com.example.GuitarApp.services;

import com.example.GuitarApp.models.Lesson;
import com.example.GuitarApp.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LessonService {
    private final LessonRepository lessonRepository;
    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }
    @Transactional
    public void addLesson(Lesson lesson){
        lessonRepository.save(lesson);
    }
}
