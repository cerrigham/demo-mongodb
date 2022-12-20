package it.bitrock.demomongodb.repository;

import org.bson.types.ObjectId;

public interface CustomMovieRepository {

    boolean partialUpdate(final ObjectId movieId, final String fieldName, final Object fieldValue);

}
