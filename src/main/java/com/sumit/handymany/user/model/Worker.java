package com.sumit.handymany.user.model;

import com.sumit.handymany.user.enums.AvailabilityStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;


    private String profession;
    private int experienceYears;
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus = AvailabilityStatus.AVAILABLE;

    private BigDecimal hourlyRate;
      @ElementCollection
    private List<String> skills;

      private Double currentLatitude;
      private Double currentLongitude;




      private String locality;
}
