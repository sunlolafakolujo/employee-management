package com.logic.gate.nextofkin.entity;

import com.logic.gate.address.entity.Address;
import com.logic.gate.nextofkin.staticdata.RelationshipWithNextOfKin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class NextOfKin {
    @Id
    @SequenceGenerator(name = "next_of_kin_generator",
            sequenceName = "next_of_kin_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "next_of_kin_generator")
    private Long id;

    @Column(nullable = false)

    @Pattern(regexp = "[A-Za-z\\s]+", message = "First Name should contains alphabets only")
    private String nFirstName;

    @Pattern(regexp = "[A-Za-z\\s]+", message = "First Name should contains alphabets only")
    @Column(nullable = false)
    private String nLastName;

    @Email
    @Column(nullable = false, unique = true)
    private String nEmail;

    @Column(nullable = false, unique = true)
    private String nMobile;

    @Enumerated(EnumType.STRING)
    private RelationshipWithNextOfKin relationshipWithNextOfKin;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Address address;
}
