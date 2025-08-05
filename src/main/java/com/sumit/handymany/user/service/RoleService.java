package com.sumit.handymany.user.service;

import com.sumit.handymany.user.model.Role;
import com.sumit.handymany.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getOrCreateRole(String roleName) {
        // In a real application, you would typically check if the role exists in the database
        // and create it if it doesn't. Here, we are just creating a new Role instance for simplicity.
      return roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    return roleRepository.save(newRole);
                });

    }

    public boolean existsByName(String roleName) {
        return roleRepository.findByName(roleName).isPresent();
    }
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}
