package it.bitrock.demomongodb.dto.movie.aggregate;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AggregateMovieRuntimeMinMaxDTO {

   private long minRuntime;
   private long maxRuntime;

}
