package it.bitrock.demomongodb.model;

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
    private ObjectId objectId;
    private String plot;
    private List<String> genres;
    private String title;

    @Override
    public String toString() {
        return "Movie [\n plot=" + plot + ",\n  genres=" + genres + ",\n  title=" + title + ",\n]";
    }
    
}