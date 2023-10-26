package com.logic.gate.employee.controller;

import com.logic.gate.employee.dto.EmployeeEvent;
import com.logic.gate.employee.service.EmployeeQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/employeeQuery")
public record EmployeeQueryController(EmployeeQueryService employeeQueryService) {

    @GetMapping("/findByCodeOrEmailOMobile")
    public ResponseEntity<EmployeeEvent> getEmployeeByCode(@RequestParam("searchKey") String searchKey) {
        return new ResponseEntity<>(employeeQueryService.fetchEmployeeByCodeOrEmailOrPhone(searchKey), HttpStatus.OK);
    }

    @GetMapping("/findAllEmployeeOrByName")
    public ResponseEntity<List<EmployeeEvent>> getAllEmployeeOrByName(@RequestParam("searchKey") String searchKey,
                                                                      @RequestParam("pageNumber") int pageNumber,
                                                                      @RequestParam("pageSize") int pageSize) {
        return new ResponseEntity<>(employeeQueryService.fetchAllEmployeesOrByFirstNameOrLastNameOrOtherNames(searchKey,
                pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/findEmployeesHiredWithinDateRange")
    public ResponseEntity<List<EmployeeEvent>> getEmployeesHiredWithinDateRange(@RequestParam("date1") LocalDate date1,
                                                                                @RequestParam("date2") LocalDate date2,
                                                                                @RequestParam("pageNumber") int pageNumber,
                                                                                @RequestParam("pageSize") int pageSize) {
        return new ResponseEntity<>(employeeQueryService.fetchEmployeesHiredDateRange(date1, date2, pageNumber,
                pageSize), HttpStatus.OK);
    }

}
