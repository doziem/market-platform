package com.doziem.market_platform.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String  categoryId;

        @Column(nullable = false)
        private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
}
