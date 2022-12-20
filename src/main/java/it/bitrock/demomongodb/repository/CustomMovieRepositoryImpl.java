package it.bitrock.demomongodb.repository;

import it.bitrock.demomongodb.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;

public class CustomMovieRepositoryImpl implements CustomMovieRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean partialUpdate(String movieId, String fieldName, Object fieldValue) {
        mongoTemplate.findAndModify(BasicQuery.query(Criteria.where("id").is(movieId)),
                BasicUpdate.update(fieldName, fieldValue), FindAndModifyOptions.none(), Movie.class);
        return true;
    }

}
