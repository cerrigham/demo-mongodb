package it.bitrock.demomongodb.service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import it.bitrock.demomongodb.model.Movie;
import it.bitrock.demomongodb.repository.MovieRepository;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

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
        ConnectionString connectionString = new ConnectionString("mongodb+srv://root:Yun4W8lv8TdKVG5D@cluster0.qnmving.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(builder ->
                        builder.maxConnectionIdleTime(60000, TimeUnit.MILLISECONDS))
                .applyToSslSettings(builder -> builder.enabled(true))
                .build();
        return MongoClients.create(settings);
    }

    private MongoCollection<Movie> getMongoCollection(){
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        MongoDatabase database = init().getDatabase("sample_mflix").withCodecRegistry(pojoCodecRegistry);
        MongoCollection<Movie> collection = database.getCollection("movies", Movie.class);
        return collection;
    }

    @Deprecated
    public ResponseEntity<?> findAllMovie(){
        FindIterable<Movie> iterable = getMongoCollection().find(); // (1)
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
            Movie movie = getMongoCollection().find(eq("title", title)).first();
            return ResponseEntity.ok(movie);
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> findByCountry(String country){
        FindIterable<Movie> movies = getMongoCollection().find(eq("countries", country)).limit(20);
        return ResponseEntity.ok(movies);
    }

    public ResponseEntity<?> findByPlot(String plot){
        return ResponseEntity.ok(movieRepository.findByPlotContains(plot));
    }

}
