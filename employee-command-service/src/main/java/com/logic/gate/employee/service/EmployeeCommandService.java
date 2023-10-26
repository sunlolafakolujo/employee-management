package com.logic.gate.employee.service;

import com.logic.gate.employee.dto.AddRoleToEmployee;
import com.logic.gate.employee.dto.EmployeeEvent;
import com.logic.gate.employee.entity.Employee;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeCommandService {
    void createEmployee(EmployeeEvent employeeEvent);
    void addRoleToEmployee(AddRoleToEmployee addRoleToEmployee);
    void editEmployee(EmployeeEvent employeeEvent, String employeeCode);
    void deleteEmployeeByCode(String employeeCode);
}
