package com.doziem.market_platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

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

    @NotBlank(message = "Logistics Company name is required")
    private String name;
    @NotBlank(message = "Contact Person is required")
    private String contactPerson;
    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Region Covered is required")
    private String regionCovered;
    @NotBlank(message = "Image URL is required")
    private String logisticLogoUrl;
    @NotBlank(message = "Public ID is required")
    private String publicId;

@OneToMany(mappedBy = "logisticsCompany", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BaseDelivery> deliveries;
}
