package com.logic.gate.address.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Address {
    @Id
    @SequenceGenerator(name = "address_generator",
            sequenceName = "address_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "address_generator")
    private Long id;

    @Column(nullable = false)
    private String houseNumber;

    @Column(nullable = false)
    private String streetNumber;
    private String landmark;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String country;
}
