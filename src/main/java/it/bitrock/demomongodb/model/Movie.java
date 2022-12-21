package it.bitrock.demomongodb.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document("movies")
public class Movie {

    @Schema(name = "id", example = "insert_id")
    private ObjectId id;
    private String title;
    private String plot;
    private List<String> genres;
    private List<String> languages;
    private List<String> countries;
    private String type;

    @Override
    public String toString() {
        return "Movie [\n plot=" + plot + ",\n  genres=" + genres + ",\n  title=" + title + ",\n]";
    }
    
}