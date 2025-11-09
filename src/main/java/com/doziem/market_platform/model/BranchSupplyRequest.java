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
public class BranchSupplyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  branchRequestId;

    @ManyToOne
    private StoreBranch branch;

    @ManyToOne
    private StateWarehouse stateWarehouse;

    @Enumerated(EnumType.STRING)
    private RequestStatus status; // PENDING, APPROVED, REJECTED, FULFILLED

    private ZonedDateTime requestDate;
    private ZonedDateTime approvedDate;
    private ZonedDateTime fulfilledDate;

    @OneToMany(mappedBy = "branchSupplyRequest", cascade = CascadeType.ALL)
    private List<BranchSupplyItem> items;
}
