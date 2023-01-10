package it.bitrock.demomongodb.repository.sale;

import it.bitrock.demomongodb.model.Sale;
import org.bson.types.ObjectId;

import java.util.List;

public interface CustomSaleRepository {

    void partialUpdate(final ObjectId movieId, final String fieldName, final Object fieldValue);
    public List<Sale> getSomeField(int limit, String... field);;

}
