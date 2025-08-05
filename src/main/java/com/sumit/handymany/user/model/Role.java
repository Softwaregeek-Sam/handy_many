package com.sumit.handymany.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")

public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /* Assuming roles are stored as strings, e.g., "CLIENT", "WORKER", etc.
    // You can also use an enum if you prefer
    // but for simplicity, we are using a string here.
     If you want to use an enum, you can define it separately and use it here.
      */

    @Column(nullable = false, unique = true)
    private String name;

}
