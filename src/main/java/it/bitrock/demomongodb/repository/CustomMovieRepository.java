package it.bitrock.demomongodb.repository;

import org.bson.types.ObjectId;
import it.bitrock.demomongodb.model.Movie;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface CustomMovieRepository {


    void partialUpdate(final ObjectId movieId, final String fieldName, final Object fieldValue);
    public List<Movie> getSomeField(int limit, String... field);

}
