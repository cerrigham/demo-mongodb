package it.bitrock.demomongodb.dto.aggregate;

import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AggregateMovieRuntimeDTO {

   private ObjectId id;
   private String title;
   private List<String> genres;
   private Long runtime;
   private List<String> languages;
   private List<String> countries;
   private Object year;
   private String type;

}
