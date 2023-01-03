package it.bitrock.demomongodb.dto.aggregate;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AggregateMovieRuntimeMinMaxDTO {

   private ObjectId id;
   private String title;
   private List<String> genres;
   private Long runtime;
   private int minRuntime;
   private int maxRuntime;

}
