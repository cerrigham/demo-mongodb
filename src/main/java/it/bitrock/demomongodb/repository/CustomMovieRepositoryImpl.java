package it.bitrock.demomongodb.repository;

import it.bitrock.demomongodb.model.Movie;
import org.bson.Document;
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

public class CustomMovieRepositoryImpl implements CustomMovieRepository {

    List<Movie> count = new ArrayList();

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void partialUpdate(ObjectId movieId, String fieldName, Object fieldValue) {
        mongoTemplate.findAndModify(BasicQuery.query(Criteria.where("id").is(movieId)),
                BasicUpdate.update(fieldName, fieldValue), FindAndModifyOptions.none(), Movie.class);
    }

    @Override
    public List<Movie> getSomeField(int limit, String... field){
        Query query = new Query();
            query.fields().include(field).exclude("id");
        return mongoTemplate.find(query, Movie.class).stream().limit(limit).collect(Collectors.toList());
    }

    //TODO ADATTARE
    public AggregationResults<Movie> aggregationOne(){
        GroupOperation groupByContriesAndSumPop = group("countries")
                .sum("pop").as("statePop");
        MatchOperation filterStates = match(new Criteria("statePop").gt(10000000));
        SortOperation sortByPopDesc = sort(Sort.by(Sort.Direction.DESC, "statePop"));

        Aggregation aggregation = newAggregation(
                groupByContriesAndSumPop, filterStates, sortByPopDesc);
        AggregationResults<Movie> result = mongoTemplate.aggregate(
                aggregation, "zips", Movie.class);
        return result;
    }

    //TODO ADATTARE
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

    //TODO ADATTARE
    public Document aggregationThree(){
        GroupOperation sumZips = group("state").count().as("zipCount");
        SortOperation sortByCount = sort(Sort.Direction.ASC, "zipCount");
        GroupOperation groupFirstAndLast = group().first("_id").as("minZipState")
                .first("zipCount").as("minZipCount").last("_id").as("maxZipState")
                .last("zipCount").as("maxZipCount");

        Aggregation aggregation = newAggregation(sumZips, sortByCount, groupFirstAndLast);

        AggregationResults<Document> result = mongoTemplate
                .aggregate(aggregation, "zips", Document.class);
        Document document= result.getUniqueMappedResult();
        return document;
    }



}
