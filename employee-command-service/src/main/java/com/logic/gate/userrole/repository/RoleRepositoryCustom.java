package com.logic.gate.userrole.repository;

import com.logic.gate.userrole.entity.Role;

import java.util.Optional;

public interface RoleRepositoryCustom {
    Optional<Role> findByRoleName(String userRole);
}
