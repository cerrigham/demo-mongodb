package it.bitrock.demomongodb.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.transform.Source;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document("movies")
public class Movie {

    @Id
    @Schema(name = "id", example = "insert_id")
    private ObjectId id;
    private String plot;
    private List<String> genres;
    private Long runtime;
    private String rated;
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
    private String type;

    @Override
    public String toString() {
        return "Movie [\n plot=" + plot + ",\n  genres=" + genres + ",\n  title=" + title + ",\n]";
    }

}