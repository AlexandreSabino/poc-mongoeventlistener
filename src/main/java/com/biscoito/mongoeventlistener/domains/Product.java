package com.biscoito.mongoeventlistener.domains;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Product {

    @Id
    private String sku;

    private String name;

    private String description;
}
