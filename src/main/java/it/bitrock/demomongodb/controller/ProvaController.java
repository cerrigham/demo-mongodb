package it.bitrock.demomongodb.controller;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.bitrock.demomongodb.dto.ProvaDTO;
import it.bitrock.demomongodb.repository.MovieRepository;
import it.bitrock.demomongodb.repository.ProvaRepository;
import it.bitrock.demomongodb.service.MovieService;
import it.bitrock.demomongodb.service.ProvaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/prova")
@Tag(name = "Prova Controller" , description = "Controller for do a experiment")
@RestController
public class ProvaController {

    @Autowired
    ProvaService provaService;

    @Autowired
    ProvaRepository provaRepository;

    @GetMapping("/prova")
    public ResponseEntity<?> getAll(Pageable page) {
        return ResponseEntity.ok(provaRepository.findAll(page));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ProvaDTO provaDTO){
        return provaService.add(provaDTO);
    }





}
