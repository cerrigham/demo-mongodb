package it.bitrock.demomongodb.dto.movie.aggregate;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AggregateTestDTO {

   private String rated;
   private Long count;
   private List<String> film;

}
