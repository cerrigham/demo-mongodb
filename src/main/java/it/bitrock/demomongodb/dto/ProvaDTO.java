package it.bitrock.demomongodb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvaDTO {

    private String nome;
    private String cognome;
    private int eta;
    private String citta;

}
