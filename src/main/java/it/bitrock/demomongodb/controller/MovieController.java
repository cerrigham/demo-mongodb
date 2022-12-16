package it.bitrock.demomongodb.controller;

import it.bitrock.demomongodb.repository.MovieRepository;
import it.bitrock.demomongodb.service.MovieService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/movie")
@RestController
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieService movieService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllMovie(Pageable page){
        return ResponseEntity.ok(movieRepository.findAll(page));
    }

}
