package com.logic.gate.userrole.repository;

import com.logic.gate.userrole.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>, RoleRepositoryCustom {
}
