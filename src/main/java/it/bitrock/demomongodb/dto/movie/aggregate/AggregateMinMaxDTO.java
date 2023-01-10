package it.bitrock.demomongodb.dto.movie.aggregate;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AggregateMinMaxDTO {

   private long minRuntime;
   private long maxRuntime;

}
