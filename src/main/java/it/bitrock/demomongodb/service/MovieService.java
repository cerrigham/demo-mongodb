package it.bitrock.demomongodb.service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import it.bitrock.demomongodb.dto.InsertMovieDTO;
import it.bitrock.demomongodb.model.Movie;
import it.bitrock.demomongodb.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Slf4j
@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    private MongoClient init(){
        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb+srv://root:Yun4W8lv8TdKVG5D@cluster0.qnmving.mongodb.net/?retryWrites=true&w=majority";
        return MongoClients.create(uri);
    }

    private MongoClient initImplement(){
        ConnectionString connectionString = new ConnectionString(
                "mongodb+srv://root:Yun4W8lv8TdKVG5D@cluster0.qnmving.mongodb.net/?retryWrites=true&w=majority");
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

    public ResponseEntity<?> getById(String id){
        if(movieRepository.existsById(id)) {
            return ResponseEntity.ok(movieRepository.findById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> getAllMovie(int limit){
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

    public ResponseEntity<?> getByTitle(String title){
        if(movieRepository.existsByTitleAllIgnoreCaseContains(title)) {
//            Movie movie = getMovieCollection().find(eq("title", title)).first();
            return ResponseEntity.ok(movieRepository.findByTitleAllIgnoreCaseContains(title));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> getByCountry(String country, int limit){
        if(movieRepository.existsByCountriesAllIgnoreCaseContains(country)) {
//            FindIterable<Movie> movies = getMovieCollection().find(eq("countries", country)).limit(limit);
            List<Movie> movies = movieRepository.findByIgnoreCaseCountriesContaining(country)
                    .stream().limit(limit).collect(Collectors.toList());
            return ResponseEntity.ok(movies);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<?> getByLanguage(String language, int limit){
        if(movieRepository.existsByAllIgnoreCaseLanguages(language)) {
//            FindIterable<Movie> movies = getMovieCollection().find(eq("languages", language)).limit(limit);
            List<Movie> movies = movieRepository.findByLanguagesAllIgnoreCase(language)
                    .stream().limit(limit).collect(Collectors.toList());
            return ResponseEntity.ok(movies);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<?> getByPlot(String plot, int limit){
        if(movieRepository.existsByPlotAllIgnoreCaseContains(plot)) {
            return ResponseEntity.ok(movieRepository.findByPlotAllIgnoreCaseContains(plot).stream().limit(limit));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<?> getByTitleAndCountry(String title, String country){
        if(movieRepository.existsByTitleContains(title) &&
                movieRepository.existsByCountriesAllIgnoreCaseContains(country)) {
            return ResponseEntity.ok(movieRepository
                    .findByTitleContainsAndCountriesAllIgnoreCaseContains(title, country));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<?> getSomeField(int limit, String... field){
        if(field != null) {
            return ResponseEntity.ok(movieRepository.getSomeField(limit, field));
        } return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<?> getAggregationMovieRuntime(int limit, int runtimeGt){

//        boolean fieldNotNull = Arrays.stream(aggregateRuntimeMovieDTO.getFields()).allMatch(f -> Objects.nonNull(f));
        if(limit != 0) {
            return ResponseEntity.ok(movieRepository.aggregationMovieRuntimeGreaterThan(limit, runtimeGt));
        } else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<?> insertMovie(InsertMovieDTO insertMovieDTO){
        Movie movie = new Movie();
        BeanUtils.copyProperties(insertMovieDTO, movie);
        ObjectId id = movieRepository.save(movie).getId();
        log.info("id: " + id);
        return ResponseEntity.ok("Added Movie with ID: " + id);
    }

    public ResponseEntity<?> updateMovie(Movie movie) throws Exception{
        String id = String.valueOf(movie.getId());
        String movieUpdate = "Update Movie id: " + id +"-";
        String stringField = "";
        if(movieRepository.existsById(id)) {
            for (Field field : Movie.class.getDeclaredFields()) {
                String fieldName = field.getName();
                if (fieldName.equals("id")) {
                    continue;
                }
                final Method getter = Movie.class.getDeclaredMethod("get" + StringUtils.capitalize(fieldName));
                final Object fieldValue = getter.invoke(movie);
                if (Objects.nonNull(fieldValue)) {
                    log.info("field : " + fieldName);
                    movieRepository.partialUpdate(movie.getId(), fieldName, fieldValue);
                    stringField = " field: \"" + fieldName + "\", with value: \"" + fieldValue + "\" -";
                }
            }
            return ResponseEntity.ok(movieUpdate + stringField);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> deleteMovie(String id){
        if(movieRepository.existsById(id)) {
            Optional<Movie> movie = movieRepository.findById(id);
            List<String> title = movie.stream().map(m -> m.getTitle()).collect(Collectors.toList());
            log.info("title : " + title.get(0));
            if(movieRepository.deleteByTitle(title.get(0)) == 1) {
                return ResponseEntity.ok("Movie id: " + id + " deleted");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
