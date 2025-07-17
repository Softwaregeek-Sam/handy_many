package com.sumit.handymany.user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String street;
    private String locality;

    private String city;
    private String state;
    private String pincode;
    private Double latitude;
    private Double longitude;




    @OneToOne
    @JoinColumn(name="user_id", nullable = false, unique = true)
    private User user;

}
