package com.logic.gate.employee.entity;

import com.logic.gate.address.entity.Address;
import com.logic.gate.employee.staticdata.EmployeeStatus;
import com.logic.gate.employee.staticdata.Gender;
import com.logic.gate.employee.staticdata.Title;
import com.logic.gate.nextofkin.entity.NextOfKin;
import com.logic.gate.picture.model.Picture;
import com.logic.gate.userrole.entity.Role;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE employee SET employeeStatus='DELETE' WHERE employeeId=?", check = ResultCheckStyle.COUNT)
@Where(clause = "employeeStatus <>'DELETE'")
public class Employee {
    @Id
    @SequenceGenerator(name = "next_of_kin_generator",
            sequenceName = "next_of_kin_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "next_of_kin_generator")
    private Long id;
    private String employeeCode;

    @Enumerated(EnumType.STRING)
    private Title title;

    @Column(nullable = false)
    @Pattern(regexp = "[A-Za-z\\s]+", message = "First Name should contains alphabets only")
    private String firstName;

    @Column(nullable = false)
    @Pattern(regexp = "[A-Za-z\\s]+", message = "Last Name should contains alphabets only")
    private String lastName;

    @Pattern(regexp = "[A-Za-z\\s]+", message = "Other Names should contains alphabets only")
    private String otherNames;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate dateOfBirth;
    private Integer age;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String mobile;

    @Column(nullable = false)
    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Please Enter a valid Password")
    private String defaultPassword;

    @Transient
    private String confirmDefaultPassword;
    private LocalDate hiredDate;
    private LocalDate retirementDate;
    private String nationalIdentificationNumber;
    private LocalDate nationalIdentificationIssuedDate;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus employeeStatus;
    private String nationality;
    private String otherNationality;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private NextOfKin nextOfKin;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Picture picture;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "employee_roles",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

}
