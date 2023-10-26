package com.logic.gate.employee.controller;

import com.logic.gate.employee.dto.AddRoleToEmployee;
import com.logic.gate.employee.dto.EmployeeEvent;
import com.logic.gate.employee.entity.Employee;
import com.logic.gate.employee.service.EmployeeCommandService;
import com.logic.gate.picture.model.Picture;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/v1/employeeManagement")
public record EmployeeCommandController(EmployeeCommandService employeeCommandService) {

    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeEvent employeeEvent) {

        employeeCommandService.createEmployee(employeeEvent);
        return ResponseEntity.ok("Employee added successfully");
    }

    @PostMapping("/addRoleToEmployee")
    public ResponseEntity<String> addRoleToEmployee(@RequestBody AddRoleToEmployee addRoleToEmployee) {
        employeeCommandService.addRoleToEmployee(addRoleToEmployee);
        return ResponseEntity.ok("Role added to Employee successfully");
    }

    @PutMapping("/editEmployee")
    public ResponseEntity<String> editEmployee(@RequestBody EmployeeEvent employeeEvent, String employeeCode) {
        employeeCommandService.editEmployee(employeeEvent, employeeCode);
        return ResponseEntity.ok("Employee successfully updated");
    }

    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<String> deleteEmployee(String employeeCode){
        employeeCommandService.deleteEmployeeByCode(employeeCode);
        return ResponseEntity.ok("Employee "+employeeCode+" successfully deleted");
    }

    private Picture uploadPicture(MultipartFile multipartFile) throws IOException {
        Picture picture = new Picture(multipartFile.getOriginalFilename(),
                multipartFile.getContentType(), multipartFile.getBytes());
        return picture;
    }
}
