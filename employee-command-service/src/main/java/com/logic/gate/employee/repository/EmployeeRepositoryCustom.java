package com.logic.gate.employee.repository;

import com.logic.gate.employee.entity.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepositoryCustom {
    @Query("From Employee e Where e.email=?2 Or e.mobile=?3 Or e.employeeCode=?4")
    Optional<Employee> findEmployeeByEmailOrMobileOrEmployeeCode(String key1, String key2, String key3);

    @Modifying
    @Query("Delete From Employee e Where e.employeeCode=?1")
    void deleteEmployeeByCode(String searchKey);
}
