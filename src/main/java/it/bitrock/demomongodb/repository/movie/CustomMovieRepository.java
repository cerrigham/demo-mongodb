package it.bitrock.demomongodb.repository.movie;

import org.bson.types.ObjectId;
import it.bitrock.demomongodb.model.Movie;
import java.util.List;

public interface CustomMovieRepository {


    void partialUpdate(final ObjectId movieId, final String fieldName, final Object fieldValue);
    public List<Movie> getSomeField(int limit, String... field);
    public List<?> aggregationMovieRuntimeGreaterThan(int limit, int runtimeGt);
    public List<?> aggregationRuntimeMinMax();
    public List<?> testAgg();

}
