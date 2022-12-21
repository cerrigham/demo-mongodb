package it.bitrock.demomongodb.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.bitrock.demomongodb.dto.InsertMovieDTO;
import it.bitrock.demomongodb.model.Movie;
import it.bitrock.demomongodb.repository.MovieRepository;
import it.bitrock.demomongodb.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

    @GetMapping("/getById")
    public ResponseEntity<?> getById(@RequestParam String id){
        return movieService.getById(id);
    }

    @GetMapping("/getAllServ")
    public ResponseEntity<?> getAllMovie(@RequestParam int limit){
        return movieService.getAllMovie(limit);
    }

    @GetMapping("/getByTitle")
    public ResponseEntity<?> getByTitle(@RequestParam String title){
        return movieService.findByTitle(title);
    }

    @GetMapping("/getByTitleAndCountry")
    public ResponseEntity<?> getByTitleAndCountry(@RequestParam String title, @RequestParam String country){
        return movieService.findByTitleAndCountry(title, country);
    }

    @GetMapping("/getByCountry/{limit}")
    public ResponseEntity<?> getByCountry(@RequestParam String country, @PathVariable("limit") int limit){
        return movieService.findByCountry(country, limit);
    }

    @GetMapping("/getByLanguage")
    public ResponseEntity<?> getByLanguage(@RequestParam String language, @RequestParam int limit){
        return movieService.findByLanguage(language, limit);
    }

    @GetMapping("/getByPlot")
    public ResponseEntity<?> getByPlot(@RequestParam String plot, @RequestParam int limit){
        return movieService.findByPlot(plot, limit);
    }

    @PostMapping(value = "/insert", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertMovie(@RequestBody InsertMovieDTO insertMovieDTO){
        return movieService.insertMovie(insertMovieDTO);
    }

    //TODO cos'è il PatchMapping?
    @PatchMapping("/update")
    public ResponseEntity<?> updateMovie(@RequestBody Movie movie) throws Exception {
        return movieService.updateMovie(movie);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable("id") String id){
        return movieService.delete(id);
    }


}
