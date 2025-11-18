package com.doziem.market_platform.model.staff;

import com.doziem.market_platform.model.StoreBranch;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "staff_id")
    private List<Staff> staff = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "humanResource", cascade = CascadeType.ALL)
    private List<Department> departments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id")
    private StoreBranch branch;

    private String roleTitle;

    private boolean active;
}

