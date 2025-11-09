package com.doziem.market_platform.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String deliveryItemIId;


    @ManyToOne
    private BaseDelivery delivery; // using inheritance requires careful mapping; alternately map to concrete classes


    @ManyToOne
    private Product product;


    private int quantity;
}
