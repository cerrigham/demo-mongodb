package it.bitrock.demomongodb.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Document("prova")
public class Sale {

    private ObjectId objectId;
    private LocalDateTime saleDate;
    private List<?> items;
    private String storeLocation;
    private Object customer;
    private Boolean couponUsed;
    private String purchaseMethod;
    
}