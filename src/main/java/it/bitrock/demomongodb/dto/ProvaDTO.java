package it.bitrock.demomongodb.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvaDTO {

    @Schema(hidden = true)
    private ObjectId objectId;
    private String nome;
    private String cognome;
    private int eta;
    private String citta;

}
