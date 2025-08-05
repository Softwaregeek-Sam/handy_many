package com.sumit.handymany.user.repository;


import com.sumit.handymany.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String roleName);
    // This interface will automatically provide CRUD operations for Role entities.
    // You can add custom query methods if needed.
}
