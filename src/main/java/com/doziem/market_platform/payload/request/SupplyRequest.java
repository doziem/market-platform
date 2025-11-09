package com.doziem.market_platform.payload.request;

import com.doziem.market_platform.model.Product;
import com.doziem.market_platform.model.StoreBranch;
import com.doziem.market_platform.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SupplyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    private StoreBranch branch;

    @ManyToOne
    private Product product;

    private int requestedQuantity;
    private int approvedQuantity;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    private  ZonedDateTime requestDate;
    private ZonedDateTime approvedDate;
    private ZonedDateTime fulfilledDate;
}
