package it.bitrock.demomongodb.repository.sale;

import it.bitrock.demomongodb.model.Sales;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SalesRepository extends MongoRepository<Sales, String> {


}
