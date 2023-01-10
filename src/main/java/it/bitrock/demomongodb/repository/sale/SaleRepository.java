package it.bitrock.demomongodb.repository.sale;

import it.bitrock.demomongodb.model.Sale;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SaleRepository extends MongoRepository<Sale, String> {


}
