package it.bitrock.demomongodb.repository;

import it.bitrock.demomongodb.model.Movie;
import it.bitrock.demomongodb.model.Prova;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProvaRepository extends MongoRepository<Prova, String> {


}
