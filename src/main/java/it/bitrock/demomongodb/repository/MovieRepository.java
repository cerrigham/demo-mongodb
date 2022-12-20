package it.bitrock.demomongodb.repository;

import it.bitrock.demomongodb.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String>, CustomMovieRepository{

    Boolean existsByTitle(String title);
    Boolean existsByTitleContains(String title);
    Boolean existsByPlotContains(String plot);
    Boolean existsByCountries(String title);
    Boolean existsByCountriesContains(String title);
    Boolean existsByLanguages(String title);
    Movie findByTitle(String title);
    List<Movie> findByTitleContainsAndCountriesContains(String title, String country);
    List<Movie> findByCountries(String country);
    List<Movie> findByPlotContains(String plot);
    List<Movie> findByLanguages(String language);



}
