package com.logic.gate.employee.dto;

import com.logic.gate.employee.entity.Employee;
import com.logic.gate.employee.staticdata.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEvent {
    private EventType eventType;
    private Employee employee;
}
