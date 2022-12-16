package it.bitrock.demomongodb.repository;

import it.bitrock.demomongodb.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {

}
