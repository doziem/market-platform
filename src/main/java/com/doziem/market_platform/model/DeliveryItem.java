package com.doziem.market_platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String deliveryItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BaseDelivery delivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @NotBlank(message = "Total quantity is required")
    private int quantity;
}
