package com.logic.gate.employee.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddRoleToEmployee {
    private String employeeCodeOrEmailOrMobile;
    private String roleName;
}
