package it.bitrock.demomongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import it.bitrock.demomongodb.repository.MovieRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableMongoRepositories
public class DemoMongodbApplication {//implements CommandLineRunner {

	@Autowired
	static
	MovieRepository movieRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoMongodbApplication.class, args);

//		ConnectionString connectionString = new ConnectionString("mongodb+srv://root:Yun4W8lv8TdKVG5D@cluster0.qnmving.mongodb.net/?retryWrites=true&w=majority");
//		MongoClientSettings settings = MongoClientSettings.builder()
//				.applyConnectionString(connectionString)
//				.applyToConnectionPoolSettings(builder ->
//						builder.maxConnectionIdleTime(60000, TimeUnit.MILLISECONDS))
//				.applyToSslSettings(builder -> builder.enabled(true))
//				.build();
//
//		MongoClient mongoClient = MongoClients.create(settings);
//		MongoDatabase database = mongoClient.getDatabase("sample_restaurant");
//
//		System.out.println(database.getName());
//		MongoCollection<Document> collection = database.getCollection("restaurants");
//		FindIterable<Document> iterable = collection.find(); // (1)
//
//		MongoCursor<Document> cursor = iterable.iterator(); // (2)
//		try {
//			while (cursor.hasNext()) {
//				System.out.println(cursor.next());
//			}
//		} finally {
//			cursor.close();
//		}



	}
}
