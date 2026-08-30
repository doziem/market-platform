package com.doziem.market_platform.model;

import com.doziem.market_platform.enums.RequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_warehouse_id", nullable = false)
    @NotNull(message = "State warehouse is required")
    private StateWarehouse stateWarehouse;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Request status is required")
    private RequestStatus status;

@NotBlank(message = "Requested by is required")
    private String requestedBy;

    private String approvedBy;
    private String approvedByPosition;

    @NotNull(message = "Request date is required")
    private ZonedDateTime requestDate;

    @NotBlank(message = "Needed by date is required")
    private ZonedDateTime approvedDate;

    private ZonedDateTime fulfilledDate;

    @OneToMany(mappedBy = "stateRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StateReplenishmentItem> items = new ArrayList<>();
}
