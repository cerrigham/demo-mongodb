package it.bitrock.demomongodb.repository;

import it.bitrock.demomongodb.model.Movie;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class CustomMovieRepositoryImpl implements CustomMovieRepository {

    List<Movie> count = new ArrayList();

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void partialUpdate(ObjectId movieId, String fieldName, Object fieldValue) {
      mongoTemplate.findAndModify(BasicQuery.query(Criteria.where("id").is(movieId)),
                BasicUpdate.update(fieldName, fieldValue), FindAndModifyOptions.none(), Movie.class);
    }

}
