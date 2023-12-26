package com.bikkadIt.electronic.store.repositories;

import com.bikkadIt.electronic.store.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
