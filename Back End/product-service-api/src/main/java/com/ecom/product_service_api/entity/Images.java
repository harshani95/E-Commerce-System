package com.ecom.product_service_api.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Images {
    @Id
    private String id;

    @Embedded
    private FileResource fileResource;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
