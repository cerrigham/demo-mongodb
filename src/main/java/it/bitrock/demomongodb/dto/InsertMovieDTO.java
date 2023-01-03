package it.bitrock.demomongodb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertMovieDTO {

    private String plot;
    private List<String> genres;
    private long runtime;
    private List<String> cast;
    private int num_mflix_comments;
    private String title;
    private String fullPlot;
    private List<String> languages;
    private List<String> countries;
    private LocalDate released;
    private List<String> directors;
    private List<String> writer;
    private int year;
    @Schema(hidden = true)
    private String type = "Movie";

}
