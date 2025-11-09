package com.doziem.market_platform.model;

import com.doziem.market_platform.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateReplenishmentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  requestId;

    @ManyToOne
    private StateWarehouse stateWarehouse;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private ZonedDateTime requestDate;
    private ZonedDateTime approvedDate;
    private ZonedDateTime fulfilledDate;

    @OneToMany(mappedBy = "stateRequest", cascade = CascadeType.ALL)
    private List<StateReplenishmentItem> items;
}
