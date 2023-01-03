package it.bitrock.demomongodb.repository;

import it.bitrock.demomongodb.dto.aggregate.AggregateMovieRuntimeDTO;
import it.bitrock.demomongodb.dto.aggregate.AggregateMovieRuntimeMinMaxDTO;
import it.bitrock.demomongodb.dto.aggregate.AggregateTestDTO;
import it.bitrock.demomongodb.model.Movie;
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
        List<Movie> movieList = mongoTemplate.find(query, Movie.class).stream().limit(limit).collect(Collectors.toList());
        //TODO TOGLIERE I PARAMETRI NULL
//        movieList.stream().
        return movieList;
    }

    @Override
    public List<?> testAgg(){
        AggregationOperation group = Aggregation.group("rated").count().as("count");
        AggregationOperation project = Aggregation.project("count").and("rated").previousOperation();
        Aggregation aggregation = Aggregation.newAggregation(group, project);
        List<AggregateTestDTO> aggregateRuntimeDTOs =
                mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(Movie.class),
                        AggregateTestDTO.class).getMappedResults();
        return aggregateRuntimeDTOs.stream().collect(Collectors.toList());
    }

    //TODO FUNZIONA
    @Override
    public List<?> aggregationMovieRuntimeGreaterThan(int limit, int runtimeGt){
//        GroupOperation groupByCountriesAndSumPop = group("runtime")
//                .sum("runtime").as("runtime");
        MatchOperation filter = match(new Criteria("runtime").gt(runtimeGt));
        SortOperation sortByDesc = sort(Sort.by(Sort.Direction.DESC, "runtime"));

        Aggregation aggregation = Aggregation.newAggregation(filter, sortByDesc);
        List<AggregateMovieRuntimeDTO> result = mongoTemplate.aggregate(
                aggregation, mongoTemplate.getCollectionName(Movie.class),
                AggregateMovieRuntimeDTO.class).getMappedResults();
        return result.stream().limit(limit).collect(Collectors.toList());
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
    @Override
    public List<?> aggregationRuntimeMinMax(int limit){
        List<?> movie;
//        GroupOperation sumZips = group("runtime").count().as("runtimeCount");
        SortOperation sortByCount = sort(Sort.Direction.ASC, "runtimeCount");
        GroupOperation groupFirstAndLast = group().first("_id").as("minRuntime")
                .first("runtimeCount").as("minRuntime").last("_id").as("maxRuntime")
                .last("runtimeCount").as("maxRuntime");

        Aggregation aggregation = newAggregation(sortByCount, groupFirstAndLast);

        List<AggregateMovieRuntimeMinMaxDTO> result = mongoTemplate
                .aggregate(aggregation, mongoTemplate.getCollectionName(Movie.class),
                        AggregateMovieRuntimeMinMaxDTO.class).getMappedResults();
        return movie = result.stream().limit(limit).collect(Collectors.toList());
    }



}
