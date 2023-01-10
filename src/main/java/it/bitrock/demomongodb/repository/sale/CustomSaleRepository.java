package it.bitrock.demomongodb.repository.sale;

import it.bitrock.demomongodb.model.Movie;
import org.bson.types.ObjectId;

import java.util.List;

public interface CustomSaleRepository {


    void partialUpdate(final ObjectId movieId, final String fieldName, final Object fieldValue);
    public List<Movie> getSomeField(int limit, String... field);
    public List<?> aggregationMovieRuntimeGreaterThan(int limit, int runtimeGt);
    public List<?> aggregationRuntimeMinMax();
    public List<?> testAgg();

}
