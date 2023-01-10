package it.bitrock.demomongodb.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.bitrock.demomongodb.dto.sales.SalesDTO;
import it.bitrock.demomongodb.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/prova")
@Tag(name = "Prova Controller" , description = "Controller for do a experiment")
@RestController
public class SalesController {

    @Autowired
    SalesService provaService;


    @GetMapping("/prova")
    public ResponseEntity<?> getAll(int limit) {
        return ResponseEntity.ok(provaService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SalesDTO provaDTO){
//        return provaService.add(provaDTO);
        return null;
    }





}
