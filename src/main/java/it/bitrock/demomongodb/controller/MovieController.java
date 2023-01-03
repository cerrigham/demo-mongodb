package it.bitrock.demomongodb.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.bitrock.demomongodb.dto.InsertMovieDTO;
import it.bitrock.demomongodb.model.Movie;
import it.bitrock.demomongodb.repository.CustomMovieRepositoryImpl;
import it.bitrock.demomongodb.repository.MovieRepository;
import it.bitrock.demomongodb.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Slf4j
@RequestMapping("/movie")
@Tag(name = "Movie Controller" , description = "Manage movie via MongoDB")
@RestController
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    CustomMovieRepositoryImpl customMovieRepository;

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
        return movieService.getByTitle(title);
    }

    @GetMapping("/getByTitleAndCountry")
    public ResponseEntity<?> getByTitleAndCountry(@RequestParam String title, @RequestParam String country){
        return movieService.getByTitleAndCountry(title, country);
    }

    @GetMapping("/getByCountry/{limit}")
    public ResponseEntity<?> getByCountry(@RequestParam String country, @PathVariable("limit") int limit){
        return movieService.getByCountry(country, limit);
    }

    @GetMapping("/getByLanguage")
    public ResponseEntity<?> getByLanguage(@RequestParam String language, @RequestParam int limit){
        return movieService.getByLanguage(language, limit);
    }

    @GetMapping("/getByPlot")
    public ResponseEntity<?> getByPlot(@RequestParam String plot, @RequestParam int limit){
        return movieService.getByPlot(plot, limit);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok(customMovieRepository.testAgg());
    }

    @GetMapping("/getByRuntimeMinMax")
    public ResponseEntity<?> getByRuntimeMinMax(@RequestParam int limit){
        return ResponseEntity.ok(customMovieRepository.aggregationRuntimeMinMax(limit));
    }

    @GetMapping("/getMovieField")
    public ResponseEntity<?> getSomeMovieFields(@RequestParam int limit, @RequestParam String... field){
        return movieService.getSomeField(limit, field);
    }

    //TODO CERCARE DI CAPIRE PERCHE' NON PRENDE LE @REQUESTBODY
    @GetMapping("/getByRuntime")
    public ResponseEntity<?> getByRuntime(@RequestParam int limit,
                                          @RequestParam(name = "Runtime Greater Then") int runtimeGt){
        log.info(limit + " " + runtimeGt);
        return movieService.getAggregationMovieRuntime(limit, runtimeGt);
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
        return movieService.deleteMovie(id);
    }


}
