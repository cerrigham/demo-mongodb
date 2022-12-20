package it.bitrock.demomongodb.repository;

public interface CustomMovieRepository {

    boolean partialUpdate(final String movieId, final String fieldName, final Object fieldValue);

}
