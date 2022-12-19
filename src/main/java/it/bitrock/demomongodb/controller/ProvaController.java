package it.bitrock.demomongodb.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.bitrock.demomongodb.dto.ProvaDTO;
import it.bitrock.demomongodb.repository.MovieRepository;
import it.bitrock.demomongodb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/prova")
@Tag(name = "Prova Controller" , description = "Controller for do a experiment")
@RestController
public class ProvaController {

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ProvaDTO provaDTO){

        return null;
    }





}
