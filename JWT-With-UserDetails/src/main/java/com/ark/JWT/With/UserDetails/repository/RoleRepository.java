package com.ark.JWT.With.UserDetails.repository;

import com.ark.JWT.With.UserDetails.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(String roleName);
}
