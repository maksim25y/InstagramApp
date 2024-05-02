package ru.mudan.SeansService.controllers;

import ru.mudan.SeansService.dto.SeansDTO;
import ru.mudan.SeansService.entity.Seans;
import ru.mudan.SeansService.facade.SeansFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mudan.SeansService.services.SeansService;

@RestController
@RequestMapping("/api/seans")
public class SeansController {
    private final SeansService seansService;
    private final SeansFacade seansFacade;
    @Autowired
    public SeansController(SeansService seansService, SeansFacade seansFacade) {
        this.seansService = seansService;
        this.seansFacade = seansFacade;
    }
    @PostMapping("/create")
    public ResponseEntity<Object>createSeans(@Valid @RequestBody SeansDTO seansDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        Seans seans = seansService.createSeans(seansDTO);
        SeansDTO createdSeans = seansFacade.seansToSeansDTO(seans);
        return new ResponseEntity<>(createdSeans, HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<Object>getSeanses(){
        return new ResponseEntity<>(seansService.getAll(), HttpStatus.OK);
    }
}
