package it.bitrock.demomongodb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertMovieDTO {

    private String title;
    private String plot;
    private List<String> genres;
    private List<String> languages;
    private List<String> countries;
    @Schema(hidden = true)
    private String type = "Movie";

}
