package com.logic.gate.employee.service;

import com.logic.gate.employee.dto.AddRoleToEmployee;
import com.logic.gate.employee.dto.EmployeeEvent;
import com.logic.gate.employee.entity.Employee;
import com.logic.gate.employee.exception.EmployeeNotFoundException;
import com.logic.gate.employee.repository.EmployeeRepository;
import com.logic.gate.employee.staticdata.EmployeeStatus;
import com.logic.gate.employee.staticdata.EventType;
import com.logic.gate.picture.model.Picture;
import com.logic.gate.userrole.entity.Role;
import com.logic.gate.userrole.exception.RoleNotFoundException;
import com.logic.gate.userrole.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.Objects;
import java.util.Random;

@Service
@Transactional
public class EmployeeCommandServiceImpl implements EmployeeCommandService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void createEmployee(EmployeeEvent employeeEvent) {
//        Picture picture = new Picture();
//        try {
//            picture = uploadPicture(file);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
        Employee employee = Employee.builder()
                .employeeCode(employeeEvent.getEmployee().getHiredDate()
                        .toString().substring(0, 8).concat(String.valueOf(new Random()
                                .nextInt(LocalTime.now().getNano()))))
                .firstName(employeeEvent.getEmployee().getFirstName())
                .lastName(employeeEvent.getEmployee().getLastName())
                .otherNames(employeeEvent.getEmployee().getOtherNames())
                .gender(employeeEvent.getEmployee().getGender())
                .dateOfBirth(employeeEvent.getEmployee().getDateOfBirth())
                .age(Period.between(employeeEvent.getEmployee().getDateOfBirth(), LocalDate.now()).getYears())
                .hiredDate(employeeEvent.getEmployee().getHiredDate())
                .email(employeeEvent.getEmployee().getEmail())
                .mobile(employeeEvent.getEmployee().getMobile())
                .defaultPassword(employeeEvent.getEmployee().getDefaultPassword())
                .confirmDefaultPassword(employeeEvent.getEmployee().getConfirmDefaultPassword())
                .nationality(employeeEvent.getEmployee().getNationality())
                .otherNationality(employeeEvent.getEmployee().getOtherNationality())
                .employeeStatus(EmployeeStatus.ACTIVE)
                .nextOfKin(employeeEvent.getEmployee().getNextOfKin())
//                .picture(picture)
                .address(employeeEvent.getEmployee().getAddress())
                .retirementDate(employeeEvent.getEmployee().getDateOfBirth().plusYears(60))
                .build();

//        employee.setPicture(picture);
        if (!validatePhoneNumber(employee)) {
            throw new EmployeeNotFoundException("Mobile phone must be in one of these formats: " +
                    "10 or 11 digit, 0000 000 0000, 000 000 0000, 000-000-0000, 000-000-0000 ext0000");
        }
        if (!employee.getDefaultPassword().equals(employee.getConfirmDefaultPassword())) {
            throw new EmployeeNotFoundException("Passwords do not match");
        }

        employeeRepository.save(employee);
        EmployeeEvent event = new EmployeeEvent(EventType.CREATE_EVENT, employee);
        kafkaTemplate.send("employee-event-topic", event);
    }

    @Override
    public void addRoleToEmployee(AddRoleToEmployee addRoleToEmployee) {
        Employee employee=employeeRepository.findEmployeeByEmailOrMobileOrEmployeeCode(
                addRoleToEmployee.getEmployeeCodeOrEmailOrMobile(), addRoleToEmployee.getEmployeeCodeOrEmailOrMobile(),
                addRoleToEmployee.getEmployeeCodeOrEmailOrMobile()
        ).orElseThrow(()->new EmployeeNotFoundException("Employee "+addRoleToEmployee.getEmployeeCodeOrEmailOrMobile()+" not found"));

        Role role=roleRepository.findByRoleName(addRoleToEmployee.getRoleName())
                .orElseThrow(()->new RoleNotFoundException("Role " + addRoleToEmployee.getRoleName()+" not found"));
        employee.getRoles().add(role);
        employeeRepository.save(employee);
        kafkaTemplate.send("employee-event-topic", addRoleToEmployee);
    }

    @Override
    public void editEmployee(EmployeeEvent employeeEvent, String employeeCode) {
        Employee savedEmployee=employeeRepository.findEmployeeByEmailOrMobileOrEmployeeCode(
                employeeCode,employeeCode,employeeCode)
                .orElseThrow(()->new EmployeeNotFoundException("Employee "+employeeCode+" not found"));
        Employee employee=employeeEvent.getEmployee();
        savedEmployee.setEmployeeStatus(employee.getEmployeeStatus());
        savedEmployee.setAddress(employee.getAddress());
        savedEmployee.setLastName(employee.getLastName());
        savedEmployee.setFirstName(employee.getFirstName());
        savedEmployee.setOtherNames(employee.getOtherNames());
        savedEmployee.setMobile(employee.getMobile());
        savedEmployee.setNextOfKin(employee.getNextOfKin());
        savedEmployee.setPicture(employee.getPicture());
        employeeRepository.save(savedEmployee);
        EmployeeEvent event=new EmployeeEvent(EventType.UPDATE_EVENT,savedEmployee);
        kafkaTemplate.send("employee-event-topic",event);
    }

    @Override
    public void deleteEmployeeByCode(String employeeCode) {
        Employee employee=employeeRepository.findEmployeeByEmailOrMobileOrEmployeeCode(
                employeeCode,employeeCode,employeeCode
        ).orElseThrow(()->new EmployeeNotFoundException("Employee "+employeeCode+" not found"));

        if (Objects.nonNull(employee)){
            employeeRepository.deleteEmployeeByCode(employeeCode);
        }else throw new EmployeeNotFoundException("Employee "+employeeCode+" not found");
    }


    private static boolean validatePhoneNumber(Employee employee) {
        // validate phone numbers of format "1234567890"
        if (employee.getMobile().matches("\\d{10}")) {
            return true;
        }
        // validate phone numbers of format "12345678901"
        else if (employee.getMobile().matches("\\d{11}")) {
            return true;
        }
        // validating phone number with -, . or spaces
        else if (employee.getMobile().matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            return true;
        }
        // validating phone number with extension length from 3 to 5
        else if (employee.getMobile().matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
            return true;
        }
        // validating phone number where area code is in braces ()
        else if (employee.getMobile().matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            return true;
        }    // Validation for India numbers
        else if (employee.getMobile().matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")) {
            return true;
        } else if (employee.getMobile().matches("\\(\\d{5}\\)-\\d{3}-\\d{3}")) {
            return true;
        } else if (employee.getMobile().matches("\\(\\d{4}\\)-\\d{3}-\\d{3}")) {
            return true;
        }    // return false if nothing matches the input
        else {
            return false;
        }
    }

    private Picture uploadPicture(MultipartFile multipartFile) throws IOException {
        Picture picture = new Picture(multipartFile.getOriginalFilename(),
                multipartFile.getContentType(), multipartFile.getBytes());
        return picture;
    }

}
