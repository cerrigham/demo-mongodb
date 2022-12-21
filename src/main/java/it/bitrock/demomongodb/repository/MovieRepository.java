package it.bitrock.demomongodb.repository;

import it.bitrock.demomongodb.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends MongoRepository<Movie, String>, CustomMovieRepository{

    Boolean existsByTitle(String title);
    Boolean existsByTitleContains(String title);
    Boolean existsByTitleAllIgnoreCaseContains(String title);
    Boolean existsByPlotContains(String plot);
    Boolean existsByPlotAllIgnoreCaseContains(String plot);
    Boolean existsByCountries(String title);
    Boolean existsByCountriesAllIgnoreCaseContains(String title);
    Boolean existsByCountriesContains(String title);
    Boolean existsByLanguages(String title);
    Boolean existsByAllIgnoreCaseLanguages(String title);
    Movie findByTitle(String title);
    List<Movie> findByTitleAllIgnoreCaseContains(String title);
    Optional<Movie> findById(String id);
    Long deleteByTitle(String title);
    List<Movie> findByTitleAndCountries(String title, String country);
    List<Movie> findByTitleContainsAndCountriesAllIgnoreCaseContains(String title, String country);
    List<Movie> findByCountries(String country);
    List<Movie> findByIgnoreCaseCountriesContaining(String country);
    List<Movie> findByPlotContains(String plot);
    List<Movie> findByPlotAllIgnoreCaseContains(String plot);
    List<Movie> findByLanguages(String language);
    List<Movie> findByLanguagesAllIgnoreCase(String language);

}
