package com.logic.gate.employee.service;


import com.logic.gate.employee.dto.EmployeeEvent;
import com.logic.gate.employee.entity.Employee;
import com.logic.gate.employee.exception.EmployeeNotFoundException;
import com.logic.gate.employee.repository.EmployeeRepository;
import com.logic.gate.employee.staticdata.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeQueryServiceImpl implements EmployeeQueryService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeEvent fetchEmployeeByCodeOrEmailOrPhone(String searchKey) {
        Employee employee = employeeRepository.findEmployeeByEmailOrMobileOrEmployeeCode(searchKey, searchKey, searchKey)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee " + searchKey + " not found"));
        EmployeeEvent event = convertEmployeeToEvent(employee);
        return event;
    }

    @Override
    public List<EmployeeEvent> fetchAllEmployeesOrByFirstNameOrLastNameOrOtherNames(String searchKey, int pageNumber,
                                                                                    int pageSize) {
        if (searchKey.equals("")) {
            return employeeRepository.findAll(PageRequest.of(pageNumber, pageSize))
                    .stream().map(this::convertEmployeeToEvent)
                    .collect(Collectors.toList());
        } else return employeeRepository.findEmployeeByFirstNameOrLastNameOrOtherNames(searchKey, searchKey,searchKey,
                PageRequest.of(pageNumber, pageSize)).stream().map(this::convertEmployeeToEvent)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeEvent> fetchEmployeesHiredDateRange(LocalDate date1, LocalDate date2, int pageNumber, int pageSize) {
        return employeeRepository.findByHiredDateBetweenTwoDates(date1, date2, PageRequest.of(pageNumber, pageSize))
                .stream().map(this::convertEmployeeToEvent)
                .collect(Collectors.toList());
    }


    private EmployeeEvent convertEmployeeToEvent(Employee employee) {
        EmployeeEvent event = new EmployeeEvent();
        event.getEmployee().setEmployeeCode(employee.getEmployeeCode());
        event.getEmployee().setFirstName(employee.getFirstName());
        event.getEmployee().setLastName(employee.getLastName());
        event.getEmployee().setDateOfBirth(employee.getDateOfBirth());
        event.getEmployee().setAge(employee.getAge());
        event.getEmployee().setEmployeeStatus(employee.getEmployeeStatus());
        event.getEmployee().setMobile(employee.getMobile());
        event.getEmployee().setEmail(employee.getEmail());
        event.getEmployee().setPicture(employee.getPicture());
        event.getEmployee().setNationality(employee.getNationality());
        event.getEmployee().setOtherNationality(employee.getOtherNationality());
        event.getEmployee().setPicture(employee.getPicture());
        event.getEmployee().setAddress(employee.getAddress());
        event.getEmployee().setGender(employee.getGender());
        event.getEmployee().setHiredDate(employee.getHiredDate());
        event.getEmployee().setRetirementDate(employee.getRetirementDate());
        return event;
    }

    @KafkaListener(topics = "employee-event-topic", groupId = "myGroup")
    public void publishEvent(EmployeeEvent event) {
        Employee employee=event.getEmployee();
        if (event.getEventType().equals(EventType.CREATE_EVENT)) {
            employeeRepository.save(employee);
        }
        if (event.getEventType().equals(EventType.UPDATE_EVENT)) {
            Employee savedEmployee = employeeRepository.findEmployeeByEmailOrMobileOrEmployeeCode(employee.getEmployeeCode(),
                            employee.getEmployeeCode(),employee.getEmployeeCode())
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee " + employee.getEmployeeCode() + " not found"));

            savedEmployee.setEmployeeStatus(employee.getEmployeeStatus());
            savedEmployee.setAddress(employee.getAddress());
            savedEmployee.setLastName(employee.getLastName());
            savedEmployee.setFirstName(employee.getFirstName());
            savedEmployee.setOtherNames(employee.getOtherNames());
            savedEmployee.setMobile(employee.getMobile());
            savedEmployee.setNextOfKin(employee.getNextOfKin());
            savedEmployee.setPicture(employee.getPicture());
            employeeRepository.save(savedEmployee);
        }
    }
}
