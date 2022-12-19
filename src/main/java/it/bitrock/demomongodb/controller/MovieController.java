package it.bitrock.demomongodb.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.bitrock.demomongodb.model.Movie;
import it.bitrock.demomongodb.repository.MovieRepository;
import it.bitrock.demomongodb.service.MovieService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@RequestMapping("/movie")
@Tag(name = "Movie Controller" , description = "Manage movie via MongoDB")
@RestController
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieService movieService;

    @GetMapping("/getAllRepo")
    public ResponseEntity<?> getAllMovie(Pageable page){
        return ResponseEntity.ok(movieRepository.findAll(page));
    }

    @GetMapping("/get_by_id")
    public ResponseEntity<?> getById(@RequestParam String id){
        return ResponseEntity.ok(movieRepository.findById(id).orElseGet(null));
    }

    @GetMapping("/getAllServ")
    public ResponseEntity<?> getAllMovie(){
        return ResponseEntity.ok(movieService.findAllMovie());
    }

    @GetMapping("/get_by_title")
    public ResponseEntity<?> getByTitle(String title){
        return ResponseEntity.ok(movieService.findByTitle(title));
    }





}
