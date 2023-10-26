package com.logic.gate.employee.service;


import com.logic.gate.employee.dto.EmployeeEvent;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeQueryService {
    EmployeeEvent fetchEmployeeByCodeOrEmailOrPhone(String searchKey);
    List<EmployeeEvent> fetchAllEmployeesOrByFirstNameOrLastNameOrOtherNames(String searchKey, int pageNumber, int pageSize);
    List<EmployeeEvent> fetchEmployeesHiredDateRange(LocalDate date1, LocalDate date2, int pageNumber, int pageSize);
}
