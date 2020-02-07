package controllers;

import entity.Authors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.AuthorsService;

import java.util.List;


    @RestController
    @RequestMapping("/api")
    class AuthorsController {

        @Autowired
        private AuthorsService authorsService;

        @GetMapping("/authors")
        List<Authors> getAllAuthors() {
            return authorsService.findAll();
        }


    }


