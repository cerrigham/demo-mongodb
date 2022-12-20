package it.bitrock.demomongodb.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.bitrock.demomongodb.dto.InsertMovieDTO;
import it.bitrock.demomongodb.dto.UpdateMovieDTO;
import it.bitrock.demomongodb.model.Movie;
import it.bitrock.demomongodb.repository.MovieRepository;
import it.bitrock.demomongodb.service.MovieService;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.websocket.server.PathParam;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

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
        return ResponseEntity.ok(movieRepository.findById(id).orElseGet(null));
    }

    @GetMapping("/getAllServ")
    public ResponseEntity<?> getAllMovie(@RequestParam int limit){
        return movieService.findAllMovie(limit);
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

    @PostMapping(value = "/insert", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> insertMovie(@RequestBody InsertMovieDTO insertMovieDTO){
        return movieService.insertMovie(insertMovieDTO);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateMovie(@RequestBody Movie movie) throws Exception {
        for(Field field : Movie.class.getDeclaredFields()){
            String fieldName = field.getName();
            if(fieldName.equals("id")){
                continue;
            }
            final Method getter = Movie.class.getDeclaredMethod("get"+ StringUtils.capitalize(fieldName));
            final Object fieldValue = getter.invoke(movie);
            if(Objects.nonNull(fieldValue)){

                return movieService.updateMovie(movie.getId(), fieldName, fieldValue);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
