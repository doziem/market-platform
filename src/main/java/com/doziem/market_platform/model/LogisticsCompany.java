package com.doziem.market_platform.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "logistic_service")
public class LogisticsCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  logisticsId;

    private String name;
    private String contactPerson;
    private String phoneNumber;
    private String email;
    private String address;
    private String regionCovered;


}
