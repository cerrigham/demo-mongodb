package it.bitrock.demomongodb.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document("prova")
public class Prova {

    private ObjectId objectId;
    private String nome;
    private String cognome;
    private int eta;
    private String citta;
    
}