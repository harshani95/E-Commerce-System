package com.ecom.product_service_api.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Product {
    @Id
    private  String productId;
    private String description;
    private double unitPrice;
    private int qty;

   @OneToMany(mappedBy = "product", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Images> images;

}
