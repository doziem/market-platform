package com.doziem.market_platform.model;

import com.doziem.market_platform.enums.RequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String branchRequestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Store branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_warehouse_id")
    private StateWarehouse stateWarehouse;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @NotBlank(message = "Requested by is required")
    private ZonedDateTime requestDate;

    private ZonedDateTime approvedDate;

    private ZonedDateTime fulfilledDate;

    @OneToMany(mappedBy = "branchSupplyRequest", cascade = CascadeType.ALL)
    private List<BranchSupplyItem> items;
}
