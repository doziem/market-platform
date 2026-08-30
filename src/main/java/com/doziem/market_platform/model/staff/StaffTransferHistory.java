package com.doziem.market_platform.model.staff;

import com.doziem.market_platform.model.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffTransferHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String staffTransferHistoryId;

    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Staff staff;

    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Store fromBranch;

    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Store toBranch;

    private ZonedDateTime transferDate;

    private String initiatedBy;
}
