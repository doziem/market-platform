package com.doziem.market_platform.payload.dto;

import com.doziem.market_platform.model.CentralWarehouse;
import com.doziem.market_platform.model.StoreBranch;
import com.doziem.market_platform.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StateWarehouseDto {

    private String  stateWarehouseId;

    private String name;
    private String address;
    private String city;
    private String state;

    private String branchManagerName;

    private StoreBranch mainBranch;

    private CentralWarehouse centralWarehouse;
}
