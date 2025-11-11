package com.doziem.market_platform.model.staff;

import com.doziem.market_platform.model.StoreBranch;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HumanResource {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  humanResourceId;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @OneToMany(mappedBy = "humanResource", cascade = CascadeType.ALL)
    private List<Department> departments;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id")
    private StoreBranch branch;

    private String roleTitle ;

    private boolean active = true;
}

