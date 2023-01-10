package it.bitrock.demomongodb.dto.sales;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDTO {

    @Schema(hidden = true)
    private ObjectId objectId;


}
