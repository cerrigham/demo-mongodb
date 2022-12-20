package it.bitrock.demomongodb.service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import it.bitrock.demomongodb.dto.InsertMovieDTO;
import it.bitrock.demomongodb.dto.UpdateMovieDTO;
import it.bitrock.demomongodb.model.Movie;
import it.bitrock.demomongodb.repository.MovieRepository;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired

    private MongoClient init(){
        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb+srv://root:Yun4W8lv8TdKVG5D@cluster0.qnmving.mongodb.net/?retryWrites=true&w=majority";
        return MongoClients.create(uri);
    }

    private MongoClient initImplement(){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://root:Yun4W8lv8TdKVG5D@cluster0.qnmving.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(builder ->
                        builder.maxConnectionIdleTime(60000, TimeUnit.MILLISECONDS))
                .applyToSslSettings(builder -> builder.enabled(true))
                .build();
        return MongoClients.create(settings);
    }

    private MongoCollection<Movie> getMovieCollection(){
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        MongoDatabase database = init().getDatabase("sample_mflix").withCodecRegistry(pojoCodecRegistry);
        MongoCollection<Movie> collection = database.getCollection("movies", Movie.class);
        return collection;
    }

    public ResponseEntity<?> findAllMovie(int limit){
        FindIterable<Movie> iterable = getMovieCollection().find().limit(limit); // (1)
        MongoCursor<Movie> cursor = iterable.iterator(); // (2)
        List<Movie> movies = new ArrayList<>();
        try {
            while(cursor.hasNext()) {
                movies.add(cursor.next());
            }
            return ResponseEntity.ok(movies);
        } finally {
            cursor.close();
        }
    }

    public ResponseEntity<?> findByTitle(String title){
        if(movieRepository.existsByTitle(title)) {
            Movie movie = getMovieCollection().find(eq("title", title)).first();
            return ResponseEntity.ok(movie);
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> findByCountry(String country, int limit){
        if(movieRepository.existsByCountries(country)) {
//            FindIterable<Movie> movies = getMovieCollection().find(eq("countries", country)).limit(limit);
            List<Movie> movies = movieRepository.findByCountries(country).stream().limit(limit).collect(Collectors.toList());
            return ResponseEntity.ok(movies);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<?> findByLanguage(String language, int limit){
        if(movieRepository.existsByLanguages(language)) {
//            FindIterable<Movie> movies = getMovieCollection().find(eq("languages", language)).limit(limit);
            List<Movie> movies = movieRepository.findByLanguages(language).stream().limit(limit).collect(Collectors.toList());
            return ResponseEntity.ok(movies);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<?> findByPlot(String plot, int limit){
        if(movieRepository.existsByPlotContains(plot)) {
            return ResponseEntity.ok(movieRepository.findByPlotContains(plot).stream().limit(limit));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<?> findByTitleAndCountry(String title, String country){
        if(movieRepository.existsByTitleContains(title) || movieRepository.existsByCountriesContains(country)) {
            return ResponseEntity.ok(movieRepository.findByTitleContainsAndCountriesContains(title, country));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<?> insertMovie(InsertMovieDTO insertMovieDTO){
        Movie movie = new Movie();
        BeanUtils.copyProperties(insertMovieDTO, movie);
        movieRepository.save(movie);
        return ResponseEntity.ok("Movie Added");
    }

    public ResponseEntity<?> updateMovie(String movieId, String filedName, Object fieldValue){
        if(movieRepository.existsById(movieId)) {
            if (movieRepository.partialUpdate(movieId,
                    filedName, fieldValue)) {
                return ResponseEntity.ok("Update Movie id: " + movieId +
                        " field: " + filedName +
                        " with value: " + fieldValue);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
