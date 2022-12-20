package it.bitrock.demomongodb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMovieDTO {

    private String movieId;
    private String fieldName;
    private String fieldValue;

}
