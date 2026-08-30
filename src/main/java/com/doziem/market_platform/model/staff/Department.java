package com.doziem.market_platform.model.staff;

import com.doziem.market_platform.model.StoreBranch;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String departmentId;


    @NotBlank(message = "Department name cannot be empty")
    private String departmentName;

    @ManyToOne
    @JoinColumn(name = "human_resource_id", nullable = false)
    @NotNull(message = "Human Resource is required")
    private HumanResource humanResource;


    @ManyToOne
    @JoinColumn(name = "branch_id")
    @NotNull(message = "Branch is required")
    private StoreBranch branch;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Staff> staffList;

    private String description;

}
