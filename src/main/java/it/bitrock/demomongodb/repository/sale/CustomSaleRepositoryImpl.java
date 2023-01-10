package it.bitrock.demomongodb.repository.sale;

import it.bitrock.demomongodb.dto.movie.aggregate.AggregateMinMaxDTO;
import it.bitrock.demomongodb.model.Movie;
import it.bitrock.demomongodb.model.Sale;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Slf4j
public class CustomSaleRepositoryImpl implements CustomSaleRepository {

    List<Movie> count = new ArrayList();

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void partialUpdate(ObjectId movieId, String fieldName, Object fieldValue) {
        mongoTemplate.findAndModify(BasicQuery.query(Criteria.where("id").is(movieId)),
                BasicUpdate.update(fieldName, fieldValue), FindAndModifyOptions.none(), Movie.class);
    }

    @Override
    public List<Sale> getSomeField(int limit, String... fields){
        Query query = new Query();
        query.fields().include(fields).exclude("id");
        List<Sale> movieList = mongoTemplate.find(query, Sale.class)
                .stream().limit(limit).collect(Collectors.toList());

        return movieList;
    }

    //TODO
    public List<?> aggregationMinMax(){
        List<?> sales;
        MatchOperation filter = match(new Criteria("...").gt(1));
        /*Il sort non riesce a gestire un alto quantitativo di valori e va in errore,
         * per questo ho dovuto impostare un gt minimo per escludere un pò di valori
         */
        SortOperation sortBy = sort(Sort.Direction.ASC, "...");
        GroupOperation groupFirstAndLast = group().first("_id").as("min...")
                .first("...").as("min...").last("_id").as("max...")
                .last("...").as("max...");

        Aggregation aggregation = newAggregation(filter, sortBy, groupFirstAndLast);

        List<?> result = mongoTemplate
                .aggregate(aggregation, mongoTemplate.getCollectionName(Sale.class),
                        AggregateMinMaxDTO.class).getMappedResults();
        return sales = result;
    }

    //TODO
    public Movie aggregationTwo(){
        GroupOperation sumTotalCityPop = group("state", "city")
                .sum("pop").as("cityPop");
        GroupOperation averageStatePop = group("_id.state")
                .avg("cityPop").as("avgCityPop");
        SortOperation sortByAvgPopAsc = sort(Sort.by(Sort.Direction.ASC, "avgCityPop"));
        LimitOperation limitToOnlyFirstDoc = limit(1);
        ProjectionOperation projectToMatchModel = project()
                .andExpression("_id").as("state")
                .andExpression("avgCityPop").as("statePop");

        Aggregation aggregation = newAggregation(
                sumTotalCityPop, averageStatePop, sortByAvgPopAsc,
                limitToOnlyFirstDoc, projectToMatchModel);

        AggregationResults<Movie> result = mongoTemplate
                .aggregate(aggregation, "zips", Movie.class);
        Movie smallestState = result.getUniqueMappedResult();
        return smallestState;
    }


}
